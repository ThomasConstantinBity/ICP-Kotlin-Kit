package com.bity.icp_kotlin_kit.util.nft_service

import com.bity.icp_kotlin_kit.di.nftActorFactory
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository

object NFTServiceUtil {

    fun setNFTService(
        collectionPrincipal: ICPPrincipal,
        nftServiceToBeRepo: NFTRepository
    ) {
        nftActorFactory.setNFTRepository(
            collectionPrincipal = collectionPrincipal,
            nftServiceToBeRepo = nftServiceToBeRepo
        )
    }

}