package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.generated_file.*
import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.exception.ICPKitException
import com.bity.icp_kotlin_kit.domain.factory.TokenRepositoryFactory
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.ICPTokenBalance
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.repository.TokenCachedRepository
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

internal class TokenCachedRepositoryImpl(
    private val canister: ICRC1Oracle.ICRC1OracleCanister,
    private val tokenRepositoryFactory: TokenRepositoryFactory
): TokenCachedRepository {

    private var cachedTokens: List<ICPToken>? = null

    override suspend fun fetchAllTokens(): List<ICPToken> {
        cachedTokens?.let { return it }

        val canisterCount = canister.count_icrc1_canisters()
        val pages = canisterCount / PAGE_SIZE + 1UL

        val canisters =  coroutineScope {
            (0UL..pages).map { index ->
                val startAt = index * PAGE_SIZE
                async {
                    try {
                        canister.get_icrc1_paginated(
                            startAt = startAt,
                            pageSize = PAGE_SIZE
                        ).toList()
                    } catch (t: Throwable) {
                        ICPKitLogger.logError(throwable = t)
                        emptyList()
                    }
                }
            }.awaitAll()
                .flatten()
        }
        return canisters.map { it.toDomainModel() }
    }

    override suspend fun fetchAccountTokensBalance(
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
        val repository = try {
            tokenRepositoryFactory.createRepository(standard, canister)
        } catch (ex: ICPKitException) {
            ICPKitLogger.logError(throwable = ex)
            return null
        }
        return try {
            repository.fetchBalance(principal)
        } catch (ex: RemoteClientError) {
            ICPKitLogger.logError(throwable = ex)
            null
        }
    }

    override suspend fun fee(token: ICPToken): BigInteger {
        val repository = tokenRepositoryFactory.createRepository(
            standard = token.standard,
            canister = token.canister
        )
        return repository.fee()
    }

    override suspend fun send(transferArgs: ICPTokenTransferArgs): ICPTokenTransfer {
        val repository = tokenRepositoryFactory.createRepository(
            standard = transferArgs.token.standard,
            canister = transferArgs.token.canister
        )
        return repository.transfer(transferArgs)
    }

    companion object {
        private const val PAGE_SIZE = 10UL
    }

}

private fun ICRC1Oracle.ICRC1.toDomainModel(): ICPToken =
    ICPToken(
        standard = category.toDomainModel(),
        canister = ICPPrincipal(ledger),
        name = name,
        decimals = decimals.toInt(),
        symbol = symbol,
        spam = category.isSpam(),
        logo = logo,
    )

private fun ICRC1Oracle.Category.toDomainModel(): ICPTokenStandard =
    when(this) {
        ICRC1Oracle.Category.Native -> ICPTokenStandard.ICP
        else -> ICPTokenStandard.ICRC1
    }

private fun ICRC1Oracle.Category.isSpam(): Boolean =
    when(this) {
        ICRC1Oracle.Category.Spam -> true
        else -> false
    }