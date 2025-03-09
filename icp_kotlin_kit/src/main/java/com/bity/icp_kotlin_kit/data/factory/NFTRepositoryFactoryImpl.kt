package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.service.nft.EXTNFTRepository
import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.data.service.nft.ICRC7NFTRepository
import com.bity.icp_kotlin_kit.data.service.nft.OrigynNFTRepository
import com.bity.icp_kotlin_kit.data.service.nft.custom.chain_fusion_toonis.ChainFusionToonisNFTRepository
import com.bity.icp_kotlin_kit.di.nftCollectionIdService
import com.bity.icp_kotlin_kit.domain.factory.NFTRepositoryFactory
import com.bity.icp_kotlin_kit.domain.generated_file.ChainFusionToonis
import com.bity.icp_kotlin_kit.domain.generated_file.EXTService
import com.bity.icp_kotlin_kit.domain.generated_file.OrigynNFT
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger

internal class NFTRepositoryFactoryImpl: NFTRepositoryFactory {

    private val customNFTRepository = hashMapOf<String, NFTRepository>()

    init {
        // ChainFusionToonis
        ICPPrincipal("nsbts-5iaaa-aaaah-aeblq-cai").let { collectionPrincipal ->
            val nftService = ChainFusionToonisNFTRepository(
                canister = collectionPrincipal,
                service = ChainFusionToonis.CFTService(collectionPrincipal),
            )
            setNFTRepository(collectionPrincipal, nftService)
        }
    }

    override fun setNFTRepository(
        collectionPrincipal: ICPPrincipal,
        nftRepository: NFTRepository,
    ) {
        ICPKitLogger.logInfo("setting custom nft service for ${collectionPrincipal.string}")
        customNFTRepository[collectionPrincipal.string] = nftRepository
    }

    override fun createNFTRepository(collection: ICPNftCollection): NFTRepository? {
        if(customNFTRepository.containsKey(collection.canister.string)) {
            ICPKitLogger.logInfo("Found custom NFT service for ${collection.canister.string}")
            return customNFTRepository[collection.canister.string]
        }
        return when(collection.standard) {

            ICPNftStandard.EXT ->
                EXTNFTRepository(
                    canister = collection.canister,
                    service = EXTService(
                        canister = collection.canister
                    ),
                    idService = nftCollectionIdService
                )

            ICPNftStandard.ICRC7 ->
                ICRC7NFTRepository(
                    canister = collection.canister,
                    service = DBANFTService(
                        canister = collection.canister,
                    ),
                )

            ICPNftStandard.ORIGYN_NFT ->
                OrigynNFTRepository(
                    canister = OrigynNFT.Nft_Canister(
                        canister = collection.canister
                    )
                )

            else -> null
        }
    }

}