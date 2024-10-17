package com.bity.icp_kotlin_kit.domain.usecase

import com.bity.icp_kotlin_kit.data.factory.ICPTransactionProviderFactory
import com.bity.icp_kotlin_kit.data.model.DABTokenException
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import com.bity.icp_kotlin_kit.provideTokenRepository

class GetExplorerUrlUseCase private constructor(
    private val repository: TokenRepository
) {

    constructor(): this(provideTokenRepository())

    suspend operator fun invoke(
        tokenCanister: ICPPrincipal,
        transactionIndex: String
    ): String? {
        val token = repository.getAllTokens()
            .firstOrNull { it.canister.string == tokenCanister.string }
            ?: throw DABTokenException.TokenNotFound(tokenCanister)
        val explorerUrlProvider = ICPTransactionProviderFactory().getExplorerURLProvider(token)
            ?: return null
        return explorerUrlProvider.getExplorerURL(transactionIndex)
    }
}