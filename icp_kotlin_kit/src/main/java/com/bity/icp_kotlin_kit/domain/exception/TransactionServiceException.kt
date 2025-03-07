package com.bity.icp_kotlin_kit.domain.exception

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

sealed class TransactionServiceException(message: String? = null): ICPKitException(message) {
    class TokenNotFound(
        tokenCanister: ICPPrincipal
    ): TransactionServiceException("Token ${tokenCanister.string} not found")
}