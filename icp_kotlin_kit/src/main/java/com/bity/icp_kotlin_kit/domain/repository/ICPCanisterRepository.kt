package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.domain.model.ICPMethod
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal

internal interface ICPCanisterRepository {

    suspend fun query(
        method: ICPMethod,
    ): Result<CandidValue>

    /**
     * @return requestId of the request
     * Use [pollRequestStatus] to to get the current status of the request
     */
    suspend fun call(
        method: ICPMethod,
        sender: ICPSigningPrincipal? = null,
    ): Result<ByteArray>

    suspend fun pollRequestStatus(
        requestId: ByteArray,
        canister: ICPPrincipal,
        sender: ICPSigningPrincipal? = null,
        durationSeconds: Long,
        waitDurationSeconds: Long
    ): Result<CandidValue>
}