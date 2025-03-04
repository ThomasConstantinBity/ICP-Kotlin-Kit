package com.bity.icp_kotlin_kit.util.nft_service

import com.bity.icp_kotlin_kit.di.nftActorFactory
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.service.NFTService

object NFTServiceUtil {

    fun setNFTService(
        collectionPrincipal: ICPPrincipal,
        nftService: NFTService
    ) {
        nftActorFactory.setNFTService(
            collectionPrincipal = collectionPrincipal,
            nftService = nftService
        )
    }

}