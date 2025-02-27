package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.datasource.api.model.toDomainModel
import com.bity.icp_kotlin_kit.data.model.error.DABTokenException
import com.bity.icp_kotlin_kit.domain.factory.TokenServiceFactory
import com.bity.icp_kotlin_kit.domain.generated_file.TokensService
import com.bity.icp_kotlin_kit.domain.generated_file.detail_value
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.model.error.TokenActorException
import com.bity.icp_kotlin_kit.domain.service.TokensCachedService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger
import com.bity.icp_kotlin_kit.domain.generated_file.token

internal class TokensCachedServiceImpl(
    private val tokensService: TokensService,
    private val actorFactory: TokenServiceFactory
): TokensCachedService {

    private var cachedTokens: List<ICPToken>? = null

    override suspend fun getAllTokens(): List<ICPToken> = coroutineScope {
        cachedTokens?.let { return@coroutineScope it }
        val tokensDeferred = async {
            tokensService.get_all()
                .map { it.toICPToken() }
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