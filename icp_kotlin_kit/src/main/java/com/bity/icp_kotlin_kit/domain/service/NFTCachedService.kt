package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

internal interface NFTCachedService {
    suspend fun getAllNFTsCollections(): List<ICPNftCollection>
    suspend fun getNFTCollection(collectionPrincipal: ICPPrincipal): ICPNftCollection?
}