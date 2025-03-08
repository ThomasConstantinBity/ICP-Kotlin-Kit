package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.datasource.api.model.toDomainModel
import com.bity.icp_kotlin_kit.domain.exception.ICPKitException
import com.bity.icp_kotlin_kit.domain.factory.NFTRepositoryFactory
import com.bity.icp_kotlin_kit.domain.generated_file.DABNFT
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedRepository
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

internal class NFTCachedRepositoryImpl(
    private val dabCanister: DABNFT.DABNFTService,
    private val nftRepositoryFactory: NFTRepositoryFactory,
): NFTCachedRepository {

    private var cachedCollections: List<ICPNftCollection> = emptyList()

    override suspend fun fetchAllNFTsCollections(): List<ICPNftCollection> {
        if(cachedCollections.isEmpty()) {
            val collections = this@NFTCachedRepositoryImpl.dabCanister.get_all()
                .mapNotNull { it.toDomainModel() }
            cachedCollections = collections
        }
        return cachedCollections
    }

    override suspend fun fetchNFTCollection(collectionPrincipal: ICPPrincipal): ICPNftCollection? =
        fetchAllNFTsCollections()
            .firstOrNull { it.canister == collectionPrincipal }

    override suspend fun fetchCollectionTokens(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem> {
        val nftRepository = getNFTRepositoryForCollection(collectionPrincipal)
        return nftRepository.fetchNFTs(collectionPrincipal)
    }

    override suspend fun fetchNFTCollectionTokenOwner(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ): ICPPrincipal? {
        val nftRepository = getNFTRepositoryForCollection(collectionPrincipal)
        return nftRepository.fetchOwner(
            collectionPrincipal = collectionPrincipal,
            nftId = nftId
        )
    }

    override suspend fun fetchUserTokenHoldings(icpPrincipal: ICPPrincipal): List<ICPNFTDetails> = coroutineScope {
        return@coroutineScope fetchAllNFTsCollections()
            .map {
                async {
                    try {
                        getHoldingForNFTCollection(
                            icpPrincipal = icpPrincipal,
                            collection = it
                        )
                    } catch (t: Throwable) {
                        ICPKitLogger.logError("Error getting NFT holdings for collection ${it.name} - ${it.canister.string}", t)
                        emptyList<ICPNFTDetails>()
                    }
                }
            }
            .awaitAll()
            .flatten()
    }

    private suspend fun getNFTRepositoryForCollection(collectionPrincipal: ICPPrincipal) : NFTRepository {
        val collection = fetchNFTCollection(collectionPrincipal)
            ?: throw ICPKitException.NFTCollectionNotFound(collectionPrincipal)
        return nftRepositoryFactory.createNFTRepository(collection)
            ?: throw ICPKitException.NFTStandardNotSupported(collection.standard)
    }

    private suspend fun getHoldingForNFTCollection(
        icpPrincipal: ICPPrincipal,
        collection: ICPNftCollection
    ): List<ICPNFTDetails> {
        val nftService = nftRepositoryFactory.createNFTRepository(collection)
            ?: throw ICPKitException.NFTStandardNotSupported(collection.standard)
        return nftService.fetchUserHoldings(icpPrincipal)
    }

}

private fun DABNFT.nft_canister.toDomainModel(): ICPNftCollection? {
    val nftStandard = standard ?: return null
    return ICPNftCollection(
        standard = nftStandard,
        name = name,
        description = description,
        iconURL = thumbnail,
        canister = principal_id.toDomainModel()
    )
}

private val DABNFT.nft_canister.standard: ICPNftStandard?
    get() {
        val standardValue = details.firstOrNull { it.string == "standard" }?.detail_value
            ?: return null
        if(standardValue !is DABNFT.detail_value.Text) return null
        return when (standardValue.string.uppercase()) {
            "EXT" -> ICPNftStandard.EXT
            "ICRC7" -> ICPNftStandard.ICRC7
            "NFTORIGYN" -> ICPNftStandard.ORIGYN_NFT
            "ICPUNKS" -> ICPNftStandard.ICPUNKS
            "DEPARTURELABS" -> ICPNftStandard.DEPARTURESLABS
            "C3" -> ICPNftStandard.C3
            "DIP721" -> ICPNftStandard.DIP721
            "DIP721V2" -> ICPNftStandard.DIP721V2
            "ERC721" -> ICPNftStandard.ERC721
            else -> null
        }
    }