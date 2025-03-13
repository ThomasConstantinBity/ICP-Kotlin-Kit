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
    val spam: Boolean,
    val logo: String?,
) {
    fun decimal(amount: BigInteger): BigDecimal {
        val divisor = BigDecimal.TEN.pow(decimals)
        return amount.toBigDecimal().divide(divisor)
    }
}