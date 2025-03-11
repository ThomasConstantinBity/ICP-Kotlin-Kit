package com.bity.icp_kotlin_kit.util.nft_service

import com.bity.icp_kotlin_kit.di.nftRepositoryFactory
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository

object NFTServiceUtil {

    fun setNFTService(
        collectionPrincipal: ICPPrincipal,
        nftRepository: NFTRepository
    ) {
        nftRepositoryFactory.setNFTRepository(
            collectionPrincipal = collectionPrincipal,
            nftRepository = nftRepository
        )
    }

}