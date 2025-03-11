package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.data.generated_file.DIP20
import java.math.BigInteger

internal data class ICPTokenMetadata(
    val name: String,
    val symbol: String,
    val decimals: Int,
    val totalSupply: BigInteger,
    val logoUrl: String?,
    val fee: BigInteger
)

internal fun DIP20.Metadata.toDomainModel(): ICPTokenMetadata =
    ICPTokenMetadata(
        name = name,
        symbol = symbol,
        decimals = decimals.toByte().toInt(),
        totalSupply = totalSupply,
        logoUrl = logo,
        fee = fee
    )