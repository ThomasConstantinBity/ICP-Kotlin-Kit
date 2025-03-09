package com.bity.icp_kotlin_kit.domain.use_case.transaction

import com.bity.icp_kotlin_kit.domain.exception.TransactionServiceException
import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransaction
import com.bity.icp_kotlin_kit.domain.repository.ICPTransactionRepository
import com.bity.icp_kotlin_kit.domain.repository.TokenCachedRepository

class FetchTokenTransactions internal constructor(
    private val tokenRepository: TokenCachedRepository,
    private val transactionRepository: ICPTransactionRepository
) {

    suspend operator fun invoke(
        account: ICPAccount,
        token: ICPToken,
    ): List<ICPTokenTransaction> = this(
        account = account,
        tokenCanister = token.canister
    )

    suspend operator fun invoke(
        account: ICPAccount,
        tokenCanister: ICPPrincipal,
    ): List<ICPTokenTransaction> {
        val token = tokenRepository.fetchAllTokens()
            .firstOrNull { it.canister.string == tokenCanister.string }
            ?: throw TransactionServiceException.TokenNotFound(tokenCanister)
        return transactionRepository.fetchTokenTransactions(account, token)
    }

}