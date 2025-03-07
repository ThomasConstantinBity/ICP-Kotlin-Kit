package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.exception.TokenRepositoryException
import com.bity.icp_kotlin_kit.domain.factory.TokenRepositoryFactory
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.ICPTokenBalance
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import com.bity.icp_kotlin_kit.domain.repository.TokensCachedRepository
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

internal class TokenRepositoryImpl (
    private val tokensCachedRepository: TokensCachedRepository,
    private val tokenRepositoryFactory: TokenRepositoryFactory
): TokenRepository {

    override suspend fun fetchAllTokens(): List<ICPToken> =
        tokensCachedRepository.fetchAllTokens()

    override suspend fun fetchTokensBalance(
        principal: ICPPrincipal
    ): List<ICPTokenBalance> = coroutineScope {
        val tokens = fetchAllTokens()
        tokens.map { token ->
            token to async {
                fetchTokenBalance(
                    standard = token.standard,
                    canister = token.canister,
                    principal = principal
                )
            }
        }.mapNotNull {
            val value = it.second.await() ?: return@mapNotNull null
            it.first to value
        }.filter {
            it.second != BigInteger.ZERO
        }.map {
            ICPTokenBalance(
                token = it.first,
                balance = it.second
            )
        }
    }

    private suspend fun fetchTokenBalance(
        standard: ICPTokenStandard,
        canister: ICPPrincipal,
        principal: ICPPrincipal
    ): BigInteger? {
        val actor = try {
            tokenRepositoryFactory.createRepository(standard, canister)
        } catch (ex: TokenRepositoryException.NoTokenServiceFound) {
            ICPKitLogger.logError(throwable = ex)
            return null
        }
        return try {
            actor.fetchBalance(principal)
        } catch (ex: RemoteClientError) {
            ICPKitLogger.logError(throwable = ex)
            null
        }
    }

    override suspend fun fee(token: ICPToken): BigInteger {
        val actor = tokenRepositoryFactory.createRepository(
            standard = token.standard,
            canister = token.canister
        )
        return actor.fee()
    }

    override suspend fun send(transferArgs: ICPTokenTransferArgs): ICPTokenTransfer {
        val actor = tokenRepositoryFactory.createRepository(
            standard = transferArgs.token.standard,
            canister = transferArgs.token.canister
        )
        return actor.transfer(transferArgs)
    }
}