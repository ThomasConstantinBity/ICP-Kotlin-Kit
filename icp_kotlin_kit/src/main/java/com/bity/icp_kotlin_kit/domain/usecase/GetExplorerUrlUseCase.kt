package com.bity.icp_kotlin_kit.domain.usecase

import com.bity.icp_kotlin_kit.data.factory.TransactionProviderFactoryImpl
import com.bity.icp_kotlin_kit.data.model.error.DABTokenException
import com.bity.icp_kotlin_kit.di.nnsSNSWService
import com.bity.icp_kotlin_kit.di.tokenRepository
import com.bity.icp_kotlin_kit.di.transactionProviderFactory
import com.bity.icp_kotlin_kit.domain.factory.TransactionProviderFactory
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository

class GetExplorerUrlUseCase internal constructor(
    private val repository: TokenRepository,
    private val transactionFactory: TransactionProviderFactory
) {

    constructor(): this(
        repository = tokenRepository,
        transactionFactory = transactionProviderFactory
    )

    suspend operator fun invoke(
        tokenCanister: ICPPrincipal,
        transactionIndex: String
    ): String? {
        val token = repository.getAllTokens()
            .firstOrNull { it.canister.string == tokenCanister.string }
            ?: throw DABTokenException.TokenNotFound(tokenCanister)
        val explorerUrlProvider = transactionFactory.getExplorerURLProvider(token)
            ?: return null
        return explorerUrlProvider.getExplorerURL(transactionIndex)
    }
}