package com.bity.icp_kotlin_kit.domain.use_case.nft

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedRepository

class FetchNFTCollectionTokens internal constructor(
    private val nftCachedRepository: NFTCachedRepository
) {

    suspend operator fun invoke(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem> =
        nftCachedRepository.fetchCollectionTokens(collectionPrincipal)

}