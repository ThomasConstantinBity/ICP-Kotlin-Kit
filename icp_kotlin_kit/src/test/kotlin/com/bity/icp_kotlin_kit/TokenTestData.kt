package com.bity.icp_kotlin_kit

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import java.math.BigInteger
import java.util.UUID.randomUUID

interface TokenTestData {

    fun aToken(iCPTokenStandard: ICPTokenStandard, canister: ICPPrincipal): ICPToken {
        return ICPToken(
            standard = iCPTokenStandard,
            canister = canister,
            name = randomUUID().toString().substring(0, 8),
            decimals = 8,
            symbol = "",
            description = "",
            totalSupply = BigInteger.ONE,
            verified = true,
            logoUrl = null,
            websiteUrl = null
        )
    }
}