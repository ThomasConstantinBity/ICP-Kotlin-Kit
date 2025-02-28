package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import java.math.BigInteger

internal interface NFTService {

    suspend fun getNFTCollectionIds(
        prev: BigInteger? = null,
        take: BigInteger? = null
    ): List<BigInteger>
    suspend fun fetchCollectionNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem>
    suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails>

}