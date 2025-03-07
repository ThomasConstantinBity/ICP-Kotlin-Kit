package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.factory.NFTRepositoryFactory
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.exception.NFTRepositoryException
import com.bity.icp_kotlin_kit.domain.exception.NFTServiceException
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedRepository
import com.bity.icp_kotlin_kit.domain.service.NFTService
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

internal class NFTServiceImpl(
    private val nftRepositoryFactory: NFTRepositoryFactory,
    private val nftCachedRepository: NFTCachedRepository
): NFTService {

    override suspend fun fetchAllCollections(): List<ICPNftCollection> =
        nftCachedRepository.fetchAllNFTsCollections()

    override suspend fun fetchCollection(collectionPrincipal: ICPPrincipal): ICPNftCollection? =
        nftCachedRepository.fetchNFTCollection(collectionPrincipal)

    override suspend fun fetchNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem> {
        val nftService = getNFTServiceForCollection(collectionPrincipal)
        return nftService.fetchNFTs(collectionPrincipal)
    }

    override suspend fun fetchNFTOwner(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ): ICPPrincipal? {
        val nftService = getNFTServiceForCollection(collectionPrincipal)
        return nftService.fetchOwner(
            collectionPrincipal = collectionPrincipal,
            nftId = nftId
        )
    }

    override suspend fun fetchNFTHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails> = coroutineScope {
        return@coroutineScope nftCachedRepository.fetchAllNFTsCollections()
            .filter { it.standard == ICPNftStandard.ICRC7 }
            .map {
                async {
                    try {
                        getHoldingForNFTCollection(
                            icpPrincipal = icpPrincipal,
                            collection = it
                        )
                    } catch (err: RemoteClientError) {
                        ICPKitLogger.logError(
                            "Error getting NFT holdings for collection ${it.name} - ${it.canister.string}",
                            err
                        )
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
        val nftService = nftRepositoryFactory.createNFTService(collection)
            ?: return emptyList()
        return nftService.fetchUserHoldings(icpPrincipal)
    }

    private suspend fun getNFTServiceForCollection(collectionPrincipal: ICPPrincipal) : NFTRepository {
        val collection = nftCachedRepository.fetchNFTCollection(collectionPrincipal)
            ?: throw NFTRepositoryException.CollectionNotFound(collectionPrincipal)
        return nftRepositoryFactory.createNFTService(collection)
            ?: throw NFTServiceException.StandardNotSupported(collection.standard)
    }
}