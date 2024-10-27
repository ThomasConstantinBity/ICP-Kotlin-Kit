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

    suspend operator fun invoke(
        args: List<Any?>?,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues,
        certification: ICPRequestCertification
    ): Result<CandidValue> =
        when(certification) {
            ICPRequestCertification.Uncertified -> query(args)
            ICPRequestCertification.Certified -> callAndPoll(
                args = args,
                sender = sender,
                pollingValues = pollingValues
            )
        }

    private suspend fun query(
        args: List<Any?>?,
    ): Result<CandidValue> {
        val icpMethod = ICPMethod(
            canister = canister,
            methodName = methodName,
            args = args?.map { CandidEncoder(it) }
        )
        return canisterRepository.query(icpMethod)
    }

    suspend fun callAndPoll(
        args: List<Any?>?,
        sender: ICPSigningPrincipal?,
        pollingValues: PollingValues
    ): Result<CandidValue> {
        val icpMethod = ICPMethod(
            canister = canister,
            methodName = methodName,
            args = args?.map { CandidEncoder(it) }
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