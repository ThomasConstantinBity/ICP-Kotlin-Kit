package com.bity.icp_kotlin_kit.domain.usecase.token

import com.bity.icp_kotlin_kit.data.model.error.DABTokenException
import com.bity.icp_kotlin_kit.data.factory.ICPTransactionProviderFactory
import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransaction
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import com.bity.icp_kotlin_kit.provideTokenRepository

class GetTokenTransactionsUseCase private constructor(
    private val repository: TokenRepository
){
    constructor(): this(provideTokenRepository())

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
        val transactionProvider = ICPTransactionProviderFactory().getTransactionProvider(token)
            ?: return emptyList()
        return transactionProvider.getAllTransactions(account)
    }
}