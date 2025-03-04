package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import java.math.BigInteger

interface NFTRepository {
    suspend fun fetchAllCollections(): List<ICPNftCollection>
    suspend fun fetchCollection(collectionPrincipal: ICPPrincipal): ICPNftCollection?
    suspend fun fetchNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem>
    suspend fun fetchNFTOwner(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger
    ) : ICPPrincipal?
    suspend fun fetchNFTHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails>
}