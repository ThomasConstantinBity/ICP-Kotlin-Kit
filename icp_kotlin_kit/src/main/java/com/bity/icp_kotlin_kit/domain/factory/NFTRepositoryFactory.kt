package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository

internal interface NFTRepositoryFactory {
    fun setNFTRepository(
        collectionPrincipal: ICPPrincipal,
        nftRepository: NFTRepository
    )
    fun createNFTRepository(collection: ICPNftCollection): NFTRepository?
}