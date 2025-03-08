package com.bity.icp_kotlin_kit.domain.exception

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard

sealed class ICPKitException(message: String? = null) : Exception(message) {

    class NFTCollectionNotFound(
        collectionPrincipal: ICPPrincipal
    ): ICPKitException("NFT Collection not found: $collectionPrincipal")

    class NFTStandardNotSupported(
        standard: ICPNftStandard
    ) : ICPKitException("Standard not supported: $standard")

}