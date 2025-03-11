package com.bity.icp_kotlin_kit.domain.use_case.nft

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedRepository
import java.math.BigInteger

class FetchNFTCollectionTokenOwner internal constructor(
    private val nftCachedRepository: NFTCachedRepository
) {

    suspend operator fun invoke(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger
    ): ICPPrincipal? = nftCachedRepository.fetchNFTCollectionTokenOwner(
        collectionPrincipal = collectionPrincipal,
        nftId = nftId
    )

}