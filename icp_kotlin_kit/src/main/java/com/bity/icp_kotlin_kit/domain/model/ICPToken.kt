package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.data.datasource.api.model.toDomainModel
import com.bity.icp_kotlin_kit.data.model.error.DABTokenException
import com.bity.icp_kotlin_kit.domain.generated_file.detail_value
import com.bity.icp_kotlin_kit.domain.generated_file.token
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import java.math.BigDecimal
import java.math.BigInteger

data class ICPToken(
    val standard: ICPTokenStandard,
    val canister: ICPPrincipal,
    val name: String,
    val decimals: Int,
    val symbol: String,
    val description: String,
    val totalSupply: BigInteger,
    val verified: Boolean,
    val logoUrl: String?,
    val websiteUrl: String?
) {

    internal constructor(token: token): this(
        standard = token.standard,
        canister = token.principal_id.toDomainModel(),
        name = token.name,
        decimals = token.decimals,
        symbol = token.symbol,
        description = token.description,
        totalSupply = BigInteger("${token.totalSupply}"),
        verified = token.verified,
        logoUrl = token.thumbnail,
        websiteUrl = token.frontend
    )

    internal constructor(
        standard: ICPTokenStandard,
        canister: ICPPrincipal,
        description: String,
        metadata: ICPTokenMetadata,
        verified: Boolean = true,
        websiteUrl: String? = null
    ): this(
        standard = standard,
        canister = canister,
        name = metadata.name,
        decimals = metadata.decimals,
        symbol = metadata.symbol,
        description = description,
        totalSupply = metadata.totalSupply,
        verified = verified,
        logoUrl = metadata.logoUrl,
        websiteUrl = websiteUrl
    )

    fun decimal(amount: BigInteger): BigDecimal {
        val divisor = BigDecimal.TEN.pow(decimals)
        return amount.toBigDecimal().divide(divisor)
    }
}

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