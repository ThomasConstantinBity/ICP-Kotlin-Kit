package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection

internal interface NFTCachedService {
    suspend fun getAllNFTsCollections(): List<ICPNftCollection>
}