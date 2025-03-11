package com.bity.icp_kotlin_kit.domain.model

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