package com.bity.icp_kotlin_kit.domain.usecase.token

import com.bity.icp_kotlin_kit.domain.model.error.ICPLedgerCanisterError
import com.bity.icp_kotlin_kit.domain.repository.LedgerCanisterRepository
import com.bity.icp_kotlin_kit.domain.request.TransferICPRequest
import com.bity.icp_kotlin_kit.provideLedgerCanisterRepository
import com.bity.icp_kotlin_kit.util.cryptography.ICPAccountCryptography
import java.math.BigInteger

class TransferICPUseCase private constructor(
    private val repository: LedgerCanisterRepository
) {
    constructor(): this(provideLedgerCanisterRepository())

    suspend operator fun invoke(
        request: TransferICPRequest
    ): BigInteger {
        require(ICPAccountCryptography.validateAccountId(request.receivingAddress)) {
            throw ICPLedgerCanisterError.InvalidReceivingAddress()
        }
        return repository.transferICP(request)
    }
}