package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.generated_file.DABNFT

internal interface NFTCachedService {
    suspend fun getAllNFTsCollections(): List<DABNFT.nft_canister>
}