package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.repository.ICPTokenRepository

internal interface TokenRepositoryFactory {
    fun createRepository(
        standard: ICPTokenStandard,
        canister: ICPPrincipal
    ): ICPTokenRepository
}