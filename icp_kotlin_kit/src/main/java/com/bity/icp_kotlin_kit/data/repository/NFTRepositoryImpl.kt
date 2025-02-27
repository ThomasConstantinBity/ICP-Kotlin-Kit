package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.factory.NFTServiceFactory
import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.service.NFTCachedService
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
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
            .filter { it.standard == ICPNftStandard.ICRC7 }
            .map {
                async {
                    try {
                        getHoldingForNFTCollection(
                            icpPrincipal = icpPrincipal,
                            collection = it
                        )
                    } catch (err: RemoteClientError) {
                        ICPKitLogger.logError(throwable = err)
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
        val actor = nftActorFactory.createNFTService(collection)
            ?: return emptyList()
        return actor.getUserHoldings(icpPrincipal)
    }
}