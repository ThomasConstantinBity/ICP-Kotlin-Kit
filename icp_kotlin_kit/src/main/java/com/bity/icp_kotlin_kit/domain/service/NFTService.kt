package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import java.math.BigInteger

interface NFTService {
    suspend fun fetchIds(
        prev: BigInteger? = null,
        take: BigInteger? = null
    ): List<BigInteger>
    suspend fun fetchNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem>
    suspend fun fetchNFT(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger
    ) : ICPNFTCollectionItem
    suspend fun fetchOwner(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger
    ) : ICPPrincipal?
    suspend fun fetchUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails>
}