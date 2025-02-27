package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

internal interface NFTRepository {
    suspend fun getNFTCollection(canisterPrincipal: ICPPrincipal): ICPNftCollection?
    suspend fun getAllNFTsCollections(): List<ICPNftCollection>
    suspend fun getNFTHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails>
}