package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection

internal interface NFTCachedService {
    suspend fun getAllNFTsCollections(): List<ICPNftCollection>
}