package com.bity.icp_kotlin_kit.domain.model.exception

import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard

sealed class NFTServiceException(message: String? = null) : ICPKitException(message) {

    class StandardNotSupported(
        standard: ICPNftStandard
    ) : NFTServiceException("Standard not supported: $standard")

}