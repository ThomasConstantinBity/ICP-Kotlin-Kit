package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.domain.factory.TokenServiceFactory
import com.bity.icp_kotlin_kit.domain.generated_file.TokensService
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.model.error.TokenActorException
import com.bity.icp_kotlin_kit.domain.service.TokensCachedService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class TokensCachedServiceImpl(
    private val tokensService: TokensService,
    private val actorFactory: TokenServiceFactory
): TokensCachedService {

    private var cachedTokens: List<ICPToken>? = null

    override suspend fun getAllTokens(): List<ICPToken> = coroutineScope {
        cachedTokens?.let { return@coroutineScope it }
        val tokensDeferred = async {
            tokensService.get_all()
                .map { ICPToken(it) }
        }
        val icpTokenDeferred = async {
            getICPToken()
        }
        val tokens = tokensDeferred.await() + listOf(icpTokenDeferred.await())
        cachedTokens = tokens
        return@coroutineScope tokens
    }

    private suspend fun getICPToken(): ICPToken {
        val standard = ICPTokenStandard.ICP
        val canister = ICPSystemCanisters.Ledger.icpPrincipal
        val actor = actorFactory.createActor(
            standard = standard,
            canister = canister
        ) ?: throw TokenActorException.NullActorException(
            standard = standard,
            canister = canister
        )
        val metadata = actor.metadata()
        return ICPToken(
            standard = standard,
            canister = canister,
            description = "Internet Computer Protocol",
            metadata = metadata
        )
    }
}