package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

internal interface NFTCachedRepository {
    suspend fun fetchAllNFTsCollections(): List<ICPNftCollection>
    suspend fun fetchNFTCollection(collectionPrincipal: ICPPrincipal): ICPNftCollection?
}