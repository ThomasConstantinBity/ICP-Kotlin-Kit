package com.bity.icp_kotlin_kit.domain.usecase.token

import com.bity.icp_kotlin_kit.data.model.error.DABTokenException
import com.bity.icp_kotlin_kit.di.tokenRepository
import com.bity.icp_kotlin_kit.di.transactionProviderFactory
import com.bity.icp_kotlin_kit.domain.factory.TransactionProviderFactory
import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransaction
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository

class GetTokenTransactionsUseCase internal constructor(
    private val repository: TokenRepository,
    private val transactionFactory: TransactionProviderFactory
) {

    constructor(): this(
        repository = tokenRepository,
        transactionFactory = transactionProviderFactory
    )

    suspend operator fun invoke(
        account: ICPAccount,
        token: ICPToken
    ): List<ICPTokenTransaction> = this(
        account = account,
        tokenCanister = token.canister
    )

    suspend operator fun invoke(
        account: ICPAccount,
        tokenCanister: ICPPrincipal
    ): List<ICPTokenTransaction> {
        val token = repository.getAllTokens()
            .firstOrNull { it.canister.string == tokenCanister.string }
            ?: throw DABTokenException.TokenNotFound(tokenCanister)
        val transactionProvider = transactionFactory.getTransactionProvider(token)
            ?: return emptyList()
        return transactionProvider.getAllTransactions(account)
    }
}