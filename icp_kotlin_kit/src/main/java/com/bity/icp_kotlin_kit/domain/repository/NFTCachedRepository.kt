package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import java.math.BigInteger

internal interface NFTCachedRepository {
    suspend fun fetchAllNFTsCollections(): List<ICPNftCollection>
    suspend fun fetchNFTCollection(collectionPrincipal: ICPPrincipal): ICPNftCollection?
    suspend fun fetchCollectionTokens(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem>
    suspend fun fetchNFTCollectionTokenOwner(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger
    ): ICPPrincipal?
    suspend fun fetchUserTokenHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails>
}