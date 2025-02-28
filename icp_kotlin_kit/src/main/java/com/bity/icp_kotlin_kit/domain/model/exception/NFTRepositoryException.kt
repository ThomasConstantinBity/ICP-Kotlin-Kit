package com.bity.icp_kotlin_kit.domain.model.exception

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

sealed class NFTRepositoryException(message: String? = null) : ICPKitException(message) {

    class CollectionNotFound(
        collectionPrincipal: ICPPrincipal
    ): NFTRepositoryException("Collection not found: $collectionPrincipal")

}