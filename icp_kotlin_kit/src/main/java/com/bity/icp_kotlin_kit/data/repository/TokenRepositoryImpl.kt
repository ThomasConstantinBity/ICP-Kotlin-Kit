package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.data.factory.ICPTokenActorFactory
import com.bity.icp_kotlin_kit.domain.generated_file.TokensService
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.model.error.TokenActorException
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

class TokenRepositoryImpl(
    private val tokensService: TokensService
): TokenRepository {

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
        val actor = ICPTokenActorFactory.createActor(
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

    override suspend fun getTokenBalance(
        standard: ICPTokenStandard,
        canister: ICPPrincipal,
        principal: ICPPrincipal
    ): BigInteger? {
        val actor = ICPTokenActorFactory.createActor(standard, canister)
            ?: return null
        return try {
            actor.getBalance(principal)
        } catch (_: RemoteClientError) { null }
    }

    override suspend fun fee(token: ICPToken): BigInteger {
        val actor = ICPTokenActorFactory.createActor(
            standard = token.standard,
            canister = token.canister
        ) ?: throw TokenActorException.NullActorException(
            standard = token.standard,
            canister = token.canister
        )
        return actor.fee()
    }

    override suspend fun send(transferArgs: ICPTokenTransferArgs): ICPTokenTransfer {
        val actor = ICPTokenActorFactory.createActor(
            standard = transferArgs.token.standard,
            canister = transferArgs.token.canister
        ) ?: throw TokenActorException.NullActorException(
            standard = transferArgs.token.standard,
            canister = transferArgs.token.canister
        )
        return actor.transfer(transferArgs)
    }
}