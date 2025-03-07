package com.bity.icp_kotlin_kit.data.service.transaction

import com.bity.icp_kotlin_kit.domain.exception.TransactionServiceException
import com.bity.icp_kotlin_kit.domain.factory.TransactionProviderFactory
import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransaction
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import com.bity.icp_kotlin_kit.domain.service.TransactionService

class TransactionServiceImpl internal constructor(
    private val tokenRepository: TokenRepository,
    private val transactionRepositoryFactory: TransactionProviderFactory
) : TransactionService {

    override suspend fun fetchTokenTransactions(
        account: ICPAccount,
        token: ICPToken,
    ): List<ICPTokenTransaction> = fetchTokenTransactions(
        account = account,
        tokenCanister = token.canister
    )

    override suspend fun fetchTokenTransactions(
        account: ICPAccount,
        tokenCanister: ICPPrincipal,
    ): List<ICPTokenTransaction> {
        val token = tokenRepository.fetchAllTokens()
            .firstOrNull { it.canister.string == tokenCanister.string }
            ?: throw TransactionServiceException.TokenNotFound(tokenCanister)
        val transactionProvider = transactionRepositoryFactory.getTransactionProvider(token)
            ?: return emptyList()
        return transactionProvider.fetchAllTransactions(account)
    }

    override suspend fun explorerUrl(
        tokenCanister: ICPPrincipal,
        transactionIndex: String,
    ): String? {
        val token = tokenRepository.fetchAllTokens()
            .firstOrNull { it.canister.string == tokenCanister.string }
            ?: throw TransactionServiceException.TokenNotFound(tokenCanister)
        val explorerUrlProvider = transactionRepositoryFactory.getExplorerURLProvider(token)
            ?: return null
        return explorerUrlProvider.getExplorerURL(transactionIndex)
    }
}