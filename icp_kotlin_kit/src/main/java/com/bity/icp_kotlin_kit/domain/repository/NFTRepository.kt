package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import java.math.BigInteger

interface NFTRepository {
    suspend fun getAllNFTsCollections(): List<ICPNftCollection>
    suspend fun getNFTCollection(collectionPrincipal: ICPPrincipal): ICPNftCollection?
    suspend fun fetchCollectionNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem>
    suspend fun getNFTHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails>
}