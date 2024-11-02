package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.data.factory.TokenActorFactoryImpl
import com.bity.icp_kotlin_kit.domain.factory.TokenActorFactory
import com.bity.icp_kotlin_kit.domain.generated_file.TokensService
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.model.error.TokenActorException
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import com.bity.icp_kotlin_kit.domain.repository.TokensCachedService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

internal class TokenRepositoryImpl (
    private val tokensCachedService: TokensCachedService,
    private val actorFactory: TokenActorFactory
): TokenRepository {

    override suspend fun getAllTokens(): List<ICPToken> =
        tokensCachedService.getAllTokens()

    override suspend fun getTokenBalance(
        standard: ICPTokenStandard,
        canister: ICPPrincipal,
        principal: ICPPrincipal
    ): BigInteger? {
        val actor = actorFactory.createActor(standard, canister)
            ?: return null
        return try {
            actor.getBalance(principal)
        } catch (_: RemoteClientError) { null }
    }

    override suspend fun fee(token: ICPToken): BigInteger {
        val actor = actorFactory.createActor(
            standard = token.standard,
            canister = token.canister
        ) ?: throw TokenActorException.NullActorException(
            standard = token.standard,
            canister = token.canister
        )
        return actor.fee()
    }

    override suspend fun send(transferArgs: ICPTokenTransferArgs): ICPTokenTransfer {
        val actor = actorFactory.createActor(
            standard = transferArgs.token.standard,
            canister = transferArgs.token.canister
        ) ?: throw TokenActorException.NullActorException(
            standard = transferArgs.token.standard,
            canister = transferArgs.token.canister
        )
        return actor.transfer(transferArgs)
    }
}