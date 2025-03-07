package com.bity.icp_kotlin_kit.domain.exception

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard

sealed class TokenRepositoryException(message: String? = null) : ICPKitException(message) {
    class NoTokenServiceFound(
        standard: ICPTokenStandard,
        canister: ICPPrincipal
    ) : TokenRepositoryException("Unable to create token service for token with standard: $standard and canister: $canister")
}