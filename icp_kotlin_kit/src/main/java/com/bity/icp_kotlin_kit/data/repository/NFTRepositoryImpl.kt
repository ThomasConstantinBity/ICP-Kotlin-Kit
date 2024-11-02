package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.domain.factory.NFTActorFactory
import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedService
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class NFTRepositoryImpl(
    private val nftActorFactory: NFTActorFactory,
    private val nftCachedService: NFTCachedService
): NFTRepository {

    override suspend fun getAllNFTsCollections(): List<ICPNftCollection> =
        nftCachedService.getAllNFTsCollections()

    override suspend fun getNFTHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails> = coroutineScope {
        return@coroutineScope nftCachedService.getAllNFTsCollections()
            .map {
                async {
                    getHoldingForNFTCollection(
                        icpPrincipal = icpPrincipal,
                        collection = it
                    )
                }
            }
            .awaitAll()
            .flatten()
    }

    private suspend fun getHoldingForNFTCollection(
        icpPrincipal: ICPPrincipal,
        collection: ICPNftCollection
    ): List<ICPNFTDetails> {
        val actor = nftActorFactory.createActor(collection)
            ?: return emptyList()
        return actor.getUserHoldings(icpPrincipal)
    }
}