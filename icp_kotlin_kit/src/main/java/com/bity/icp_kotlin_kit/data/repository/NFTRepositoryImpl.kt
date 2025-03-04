package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.factory.NFTServiceFactory
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.model.exception.NFTRepositoryException
import com.bity.icp_kotlin_kit.domain.model.exception.NFTServiceException
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.service.NFTCachedService
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import com.bity.icp_kotlin_kit.domain.service.NFTService
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

internal class NFTRepositoryImpl(
    private val nftServiceFactory: NFTServiceFactory,
    private val nftCachedService: NFTCachedService
): NFTRepository {

    override suspend fun fetchAllNFTsCollections(): List<ICPNftCollection> =
        nftCachedService.getAllNFTsCollections()

    override suspend fun fetchNFTCollection(collectionPrincipal: ICPPrincipal): ICPNftCollection? =
        nftCachedService.getNFTCollection(collectionPrincipal)

    override suspend fun fetchCollectionNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem> {
        val nftService = getNFTServiceForCollection(collectionPrincipal)
        return nftService.fetchCollectionNFTs(collectionPrincipal)
    }

    override suspend fun fetchCollectionNFT(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ): ICPNFTCollectionItem {
        val nftService = getNFTServiceForCollection(collectionPrincipal)
        return nftService.fetchCollectionNFT(
            collectionPrincipal = collectionPrincipal,
            nftId = nftId
        )
    }

    override suspend fun fetchNFTHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails> = coroutineScope {
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
        val nftService = nftServiceFactory.createNFTService(collection)
            ?: return emptyList()
        return nftService.fetchUserHoldings(icpPrincipal)
    }

    private suspend fun getNFTServiceForCollection(collectionPrincipal: ICPPrincipal) : NFTService {
        val collection = nftCachedService.getNFTCollection(collectionPrincipal)
            ?: throw NFTRepositoryException.CollectionNotFound(collectionPrincipal)
        return nftServiceFactory.createNFTService(collection)
            ?: throw NFTServiceException.StandardNotSupported(collection.standard)
    }
}