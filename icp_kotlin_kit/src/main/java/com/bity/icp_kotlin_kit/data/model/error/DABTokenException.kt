package com.bity.icp_kotlin_kit.data.model.error

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

sealed class DABTokenException(errorMessage: String? = null): Exception(errorMessage) {
    class InvalidType(type: String):
        DABTokenException("Missing field: $type")
    class WrongTokenStandard(tokenStandard: String):
        DABTokenException("Token standard not supported: $tokenStandard")
    class TokenNotFound(tokenPrincipal: ICPPrincipal):
            DABTokenException("Token ${tokenPrincipal.string} not found")
}