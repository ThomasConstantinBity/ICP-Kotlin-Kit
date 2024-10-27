package com.bity.icp_kotlin_kit.domain.usecase.ledger

import com.bity.icp_kotlin_kit.di.ledgerCanisterRepository
import com.bity.icp_kotlin_kit.domain.model.error.ICPLedgerCanisterError
import com.bity.icp_kotlin_kit.domain.repository.LedgerCanisterRepository
import com.bity.icp_kotlin_kit.domain.request.TransferICPRequest
import com.bity.icp_kotlin_kit.util.cryptography.ICPAccountCryptography
import java.math.BigInteger

class TransferICPUseCase internal constructor(
    private val repository: LedgerCanisterRepository
) {
    constructor(): this(ledgerCanisterRepository)

    suspend operator fun invoke(
        request: TransferICPRequest
    ): BigInteger {
        require(ICPAccountCryptography.validateAccountId(request.receivingAddress)) {
            throw ICPLedgerCanisterError.InvalidReceivingAddress()
        }
        return repository.transferICP(request)
    }
}