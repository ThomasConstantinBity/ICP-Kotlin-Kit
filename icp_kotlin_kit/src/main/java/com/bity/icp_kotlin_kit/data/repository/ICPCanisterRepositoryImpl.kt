package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.candid.deserializer.CandidDeserializer
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPRequestApiModel
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPStateTreePathApiModel
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPStateTreePathComponentApiModel
import com.bity.icp_kotlin_kit.data.datasource.api.request.ICPRequest
import com.bity.icp_kotlin_kit.data.datasource.api.response.model.enum.RejectCodeApiModel
import com.bity.icp_kotlin_kit.data.datasource.api.response.model.enum.StatusCodeApiModel
import com.bity.icp_kotlin_kit.data.datasource.api.service.ICPRetrofitService
import com.bity.icp_kotlin_kit.data.model.error.PollingError
import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.model.ICPMethod
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.error.RustBindingsError
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.repository.ICPCanisterRepository
import com.bity.icp_kotlin_kit.util.ext_function.toInt
import kotlinx.coroutines.delay
import java.util.Date

internal class ICPCanisterRepositoryImpl(
    private val icpRetrofitService: ICPRetrofitService,
): ICPCanisterRepository {

    override suspend fun query(
        method: ICPMethod
    ): Result<CandidValue> {
        val request = ICPRequest.init(
            requestType = ICPRequestApiModel.Query(
                icpMethod = method.toDataModel()
            ),
            canister = method.canister.toDataModel()
        )
        icpRetrofitService.query(
            urlPath = request.urlPath,
            body = request.envelope
        ).apply {
            require(isSuccessful) {
                return Result.failure(
                    RemoteClientError.HttpError(
                        errorCode = code(),
                        errorMessage = errorBody()?.string()
                    )
                )
            }
            val arg = body()?.reply?.arg
                ?: body()?.let {
                    return Result.failure(
                        RemoteClientError.CanisterError(
                            rejectCode = it.rejectCode?.name,
                            rejectMessage = it.rejectMessage,
                            errorCode = it.errorCode,
                            errorBody = errorBody()?.string(),
                        )
                    )
                }
                ?: return Result.failure(RemoteClientError.MissingBody())
            val candidValue = CandidDeserializer.decode(arg).firstOrNull()
                ?: return Result.failure(RemoteClientError.ParsingError(arg))
            return Result.success(candidValue)
        }
    }

    override suspend fun call(
        method: ICPMethod,
        sender: ICPSigningPrincipal?
    ): Result<ByteArray> {
        val request = ICPRequest.init(
            requestType = ICPRequestApiModel.Call(method.toDataModel()),
            canister = method.canister.toDataModel(),
            sender = sender
        )
        icpRetrofitService.call(
            urlPath = request.urlPath,
            body = request.envelope
        ).apply {
            if(!isSuccessful)
                return Result.failure(
                    RemoteClientError.HttpError(
                        errorCode = code(),
                        errorMessage = errorBody()?.string()
                    )
                )
        }
        return Result.success(request.requestId)
    }

    override suspend fun pollRequestStatus(
        requestId: ByteArray,
        canister: ICPPrincipal,
        sender: ICPSigningPrincipal?,
        durationSeconds: Long,
        waitDurationSeconds: Long
    ): Result<CandidValue> {
        val endTime = Date(System.currentTimeMillis() + durationSeconds * 1000).time

        val paths: List<ICPStateTreePathApiModel> = listOf(
            listOf(ICPStateTreePathComponentApiModel.StringApiModel("time")),
            listOf(
                ICPStateTreePathComponentApiModel.StringApiModel("request_status"),
                ICPStateTreePathComponentApiModel.DataApiModel(requestId),
                ICPStateTreePathComponentApiModel.StringApiModel("status")
            ),
            listOf(
                ICPStateTreePathComponentApiModel.StringApiModel("request_status"),
                ICPStateTreePathComponentApiModel.DataApiModel(requestId),
                ICPStateTreePathComponentApiModel.StringApiModel("reply")
            ),
            listOf(
                ICPStateTreePathComponentApiModel.StringApiModel("request_status"),
                ICPStateTreePathComponentApiModel.DataApiModel(requestId),
                ICPStateTreePathComponentApiModel.StringApiModel("reject_code")),
            listOf(
                ICPStateTreePathComponentApiModel.StringApiModel("request_status"),
                ICPStateTreePathComponentApiModel.DataApiModel(requestId),
                ICPStateTreePathComponentApiModel.StringApiModel("reject_message")
            )
        ).map { ICPStateTreePathApiModel(it) }

        while (endTime > System.currentTimeMillis()) {
            val status = readState(
                paths = paths,
                canister = canister,
                sender = sender
            ).getOrElse { return Result.failure(it) }

            val statusValue = stringValueForPath(status, "status")
            if(statusValue != null) {
                val statusCode = StatusCodeApiModel.valueOf(statusValue.replaceFirstChar { it.uppercase() })
                when(statusCode){
                    StatusCodeApiModel.Done ->
                        return Result.failure(PollingError.RequestIsDone())
                    StatusCodeApiModel.Received,
                    StatusCodeApiModel.Processing -> { }
                    StatusCodeApiModel.Replied -> {
                        val replyData = rawValueForPath(status, "reply")
                            ?: return Result.failure(PollingError.ParsingError("Unable to read replyData"))
                        val result = CandidDeserializer.decode(replyData).firstOrNull()
                            ?: return Result.failure(PollingError.ParsingError("Unable to deserialize replyData"))
                        return Result.success(result)
                    }
                    StatusCodeApiModel.Rejected -> {
                        val rejectCodeValue = rawValueForPath(status, "reject_code")
                            ?: return Result.failure(PollingError.ParsingError("Unable to read rejectCode"))
                        val rejectCode = RejectCodeApiModel.valueFromErrorCode(rejectCodeValue.toInt())
                            ?: return Result.failure(PollingError.ParsingError("Unable to parse rejectCode"))
                        val rejectMessage = stringValueForPath(status,"reject_message")
                        return Result.failure(PollingError.RequestRejected(rejectCode, rejectMessage))
                    }
                }
            }
            delay(waitDurationSeconds)
        }
        return Result.failure(PollingError.Timeout())
    }

    private suspend fun readState(
        paths: List<ICPStateTreePathApiModel>,
        canister: ICPPrincipal,
        sender: ICPSigningPrincipal? = null
    ): Result<Map<ICPStateTreePathApiModel, ByteArray?>> {
        val request = ICPRequest.init(
            requestType = ICPRequestApiModel.ReadState(paths),
            canister = canister.toDataModel(),
            sender = sender
        )
        try {
            icpRetrofitService.readState(
                urlPath = request.urlPath,
                body = request.envelope
            ).apply {
                if(!isSuccessful) {
                    return Result.failure(
                        RemoteClientError.HttpError(
                            errorCode = code(),
                            errorMessage = errorBody()?.string()
                        )
                    )
                }
                val body = body() ?: return Result.failure(RemoteClientError.MissingBody())
                val pathResponses = paths.mapNotNull { path ->
                    body.tree.getValue(path)?.let { value -> path to value }
                }.toMap()
                return Result.success(pathResponses)
            }
        } catch (err: RustBindingsError) {
            return Result.failure(err)
        }
    }

    private fun stringValueForPath(
        status: Map<ICPStateTreePathApiModel, ByteArray?>,
        suffix: String
    ): String? {
        val data = rawValueForPath(status, suffix) ?: return null
        return String(data, Charsets.UTF_8)
    }

    private fun rawValueForPath(
        status: Map<ICPStateTreePathApiModel, ByteArray?>,
        suffix: String
    ): ByteArray? {
        return status.filter {
            it.key.components.lastOrNull()?.stringValue == suffix
        }
            .toList()
            .firstOrNull()?.second
    }
}