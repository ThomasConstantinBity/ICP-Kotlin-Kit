package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.provider.ICPTokenActor

internal interface TokenActorFactory {
    fun createActor(
        standard: ICPTokenStandard,
        canister: ICPPrincipal
    ): ICPTokenActor?
}