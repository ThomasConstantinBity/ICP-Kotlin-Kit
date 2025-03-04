package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import java.math.BigInteger

interface NFTService {
    suspend fun fetchNFTCollectionIds(
        prev: BigInteger? = null,
        take: BigInteger? = null
    ): List<BigInteger>
    suspend fun fetchCollectionNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem>
    suspend fun fetchCollectionNFT(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger
    ) : ICPNFTCollectionItem
    suspend fun fetchUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails>
}