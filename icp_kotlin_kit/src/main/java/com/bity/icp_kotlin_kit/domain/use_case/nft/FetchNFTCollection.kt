package com.bity.icp_kotlin_kit.domain.use_case.nft

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedRepository

class FetchNFTCollection internal constructor(
    private val nftCachedRepository: NFTCachedRepository
) {

    suspend operator fun invoke(collectionPrincipal: ICPPrincipal): ICPNftCollection? =
        nftCachedRepository.fetchNFTCollection(collectionPrincipal)

}