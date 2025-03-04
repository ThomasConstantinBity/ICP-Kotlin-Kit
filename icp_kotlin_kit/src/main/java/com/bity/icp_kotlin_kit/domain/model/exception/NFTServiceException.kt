package com.bity.icp_kotlin_kit.domain.model.exception

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import java.math.BigInteger

sealed class NFTServiceException(message: String? = null) : ICPKitException(message) {

    class StandardNotSupported(
        standard: ICPNftStandard
    ) : NFTServiceException("Standard not supported: $standard")

    class NFTNotFound(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger
    ) : NFTServiceException("NFT with id $nftId not found in collection ${collectionPrincipal.string}")

}