package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.datasource.api.model.toDomainModel
import com.bity.icp_kotlin_kit.data.model.error.DABTokenException
import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.exception.ICPKitException
import com.bity.icp_kotlin_kit.domain.factory.TokenRepositoryFactory
import com.bity.icp_kotlin_kit.domain.generated_file.*
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.ICPTokenBalance
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.repository.TokenCachedRepository
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

internal class TokenCachedRepositoryImpl(
    private val canister: TokensService,
    private val tokenRepositoryFactory: TokenRepositoryFactory
): TokenCachedRepository {

    private var cachedTokens: List<ICPToken>? = null

    override suspend fun fetchAllTokens(): List<ICPToken> = coroutineScope {
        cachedTokens?.let { return@coroutineScope it }
        val tokensDeferred = async {
            this@TokenCachedRepositoryImpl.canister.get_all()
                .map { it.toICPToken() }
        }
        val icpTokenDeferred = async {
            getICPToken()
        }
        val tokens = tokensDeferred.await() + listOf(icpTokenDeferred.await())
        cachedTokens = tokens
        return@coroutineScope tokens
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

    private suspend fun getICPToken(): ICPToken {
        val standard = ICPTokenStandard.ICP
        val canister = ICPSystemCanisters.Ledger.icpPrincipal
        val repository = tokenRepositoryFactory.createRepository(
            standard = standard,
            canister = canister
        )
        val metadata = repository.fetchMetadata()
        return ICPToken(
            standard = standard,
            canister = canister,
            description = "Internet Computer Protocol",
            metadata = metadata
        )
    }
}

private fun token.toICPToken(): ICPToken =
    ICPToken(
        standard = standard,
        canister = principal_id.toDomainModel(),
        name = name,
        decimals = decimals,
        symbol = symbol,
        description = description,
        totalSupply = BigInteger(totalSupply.toString()),
        verified = verified,
        logoUrl = thumbnail,
        websiteUrl = frontend
    )

private fun token.textValue(key: String): String =
    (details.find { it.string == key }?.detail_value as? detail_value.Text)
        ?.string
        ?: throw DABTokenException.InvalidType(key)

private fun token.uLongValue(key: String): ULong =
    (details.find { it.string == key }?.detail_value as? detail_value.U64)
        ?.uLong
        ?: throw DABTokenException.InvalidType("decimals")

private val token.standard: ICPTokenStandard
    get() {
        val stringValue = textValue("standard")
        return ICPTokenStandard.valueFromString(stringValue)
    }

private val token.symbol: String
    get() = textValue("symbol")

private val token.decimals: Int
    get() = uLongValue("decimals").toInt()

private val token.totalSupply: ULong
    get() = uLongValue("total_supply")

private val token.verified: Boolean
    get() {
        val detailValue = details.find { it.string == "verified" }?.detail_value
        return when(detailValue) {
            detail_value.True -> true
            detail_value.False -> false
            else -> throw DABTokenException.InvalidType("verified")
        }
    }