package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.factory.NFTServiceFactory
import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.service.NFTCachedService
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class NFTRepositoryImpl(
    private val nftActorFactory: NFTServiceFactory,
    private val nftCachedService: NFTCachedService
): NFTRepository {

    override suspend fun getAllNFTsCollections(): List<ICPNftCollection> =
        nftCachedService.getAllNFTsCollections()

    override suspend fun getNFTHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails> = coroutineScope {
        return@coroutineScope nftCachedService.getAllNFTsCollections()
            .map {
                async {
                    try {
                        getHoldingForNFTCollection(
                            icpPrincipal = icpPrincipal,
                            collection = it
                        )
                    } catch (_: RemoteClientError) {
                        emptyList<ICPNFTDetails>()
                    }
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