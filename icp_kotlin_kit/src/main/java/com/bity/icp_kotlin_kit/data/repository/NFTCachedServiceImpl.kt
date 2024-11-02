package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.domain.generated_file.DABNFT
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedService

internal class NFTCachedServiceImpl(
    private val nftService: DABNFT.DABNFTService
): NFTCachedService {

    private var cachedCollections: List<DABNFT.nft_canister> = emptyList()

    override suspend fun getAllNFTsCollections(): List<DABNFT.nft_canister> {
        if(cachedCollections.isEmpty()) {
            val collections = nftService.get_all()
            cachedCollections = collections.toList()
        }
        return cachedCollections
    }
}