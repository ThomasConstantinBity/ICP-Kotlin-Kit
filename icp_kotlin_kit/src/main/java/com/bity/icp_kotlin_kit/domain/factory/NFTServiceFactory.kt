package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.service.NFTService

internal interface NFTServiceFactory {
    fun setNFTService(
        collectionPrincipal: ICPPrincipal,
        nftService: NFTService
    )
    fun createNFTService(collection: ICPNftCollection): NFTService?
}