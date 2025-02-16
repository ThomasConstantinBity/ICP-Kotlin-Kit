package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.candid.CandidEncoder
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.di.icpCanisterRepository
import com.bity.icp_kotlin_kit.domain.model.ICPMethod
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
import com.bity.icp_kotlin_kit.domain.repository.ICPCanisterRepository
import com.bity.icp_kotlin_kit.domain.request.PollingValues

open class ICPQuery(
    private val methodName: String,
    private val canister: ICPPrincipal,
) {
    private val canisterRepository: ICPCanisterRepository = icpCanisterRepository

    // TODO, this will be removed
    suspend operator fun invoke(
        values: List<com.bity.icp_kotlin_kit.data.model.ValueToEncode>?,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues,
        certification: ICPRequestCertification
    ): Result<List<CandidValue>> =
        when(certification) {
            ICPRequestCertification.Uncertified -> query(values)
            ICPRequestCertification.Certified -> callAndPoll(
                values = values,
                sender = sender,
                pollingValues = pollingValues
            )
        }

    // TODO, values could be not null
    suspend fun query(
        values: List<com.bity.icp_kotlin_kit.data.model.ValueToEncode>?,
    ): Result<List<CandidValue>> {
        val icpMethod = ICPMethod(
            canister = canister,
            methodName = methodName,
            args = values?.map { CandidEncoder(it) }
        )
        return canisterRepository.query(icpMethod)
    }

    suspend fun callAndPoll(
        values: List<com.bity.icp_kotlin_kit.data.model.ValueToEncode>?,
        sender: ICPSigningPrincipal?,
        pollingValues: PollingValues
    ): Result<List<CandidValue>> {
        val icpMethod = ICPMethod(
            canister = canister,
            methodName = methodName,
            args = values?.map { CandidEncoder(it) }
        )
        val requestId = canisterRepository.call(
            method = icpMethod,
            sender = sender
        ).getOrElse { return Result.failure(it) }
        return canisterRepository.pollRequestStatus(
            requestId = requestId,
            canister = canister,
            sender = sender,
            durationSeconds = pollingValues.durationSeconds,
            waitDurationSeconds = pollingValues.waitDurationSeconds
        )
    }
}