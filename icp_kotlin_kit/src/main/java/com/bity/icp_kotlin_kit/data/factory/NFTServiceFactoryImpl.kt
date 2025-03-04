package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.service.nft.EXTNFTService
import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.data.service.nft.ICRC7NFTService
import com.bity.icp_kotlin_kit.data.service.nft.OrigynNFTService
import com.bity.icp_kotlin_kit.data.service.nft.custom.chain_fusion_toonis.ChainFusionToonisNFTService
import com.bity.icp_kotlin_kit.domain.factory.NFTServiceFactory
import com.bity.icp_kotlin_kit.domain.generated_file.ChainFusionToonis
import com.bity.icp_kotlin_kit.domain.generated_file.EXTService
import com.bity.icp_kotlin_kit.domain.generated_file.OrigynNFT
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.service.NFTService
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import com.bity.icp_kotlin_kit.util.nft_service.NFTServiceUtil

internal class NFTServiceFactoryImpl: NFTServiceFactory {

    private val customNFTService = hashMapOf<String, NFTService>()

    init {
        // ChainFusionToonis
        ICPPrincipal("nsbts-5iaaa-aaaah-aeblq-cai").let { collectionPrincipal ->
            val nftService = ChainFusionToonisNFTService(
                canister = collectionPrincipal,
                service = ChainFusionToonis.CFTService(collectionPrincipal)
            )
            setNFTService(collectionPrincipal, nftService)
        }
    }

    override fun setNFTService(
        collectionPrincipal: ICPPrincipal,
        nftService: NFTService,
    ) {
        customNFTService[collectionPrincipal.string] = nftService
    }

    override fun createNFTService(collection: ICPNftCollection): NFTService? {
        if(customNFTService.containsKey(collection.canister.string)) {
            ICPKitLogger.logInfo("Found custom NFT service for ${collection.canister.string}")
            return customNFTService[collection.canister.string]
        }
        return when(collection.standard) {

            ICPNftStandard.EXT ->
                EXTNFTService(
                    canister = collection.canister,
                    service = EXTService(
                        canister = collection.canister
                    )
                )

            ICPNftStandard.ICRC7 ->
                ICRC7NFTService(
                    canister = collection.canister,
                    service = DBANFTService(
                        canister = collection.canister,
                    ),
                )

            ICPNftStandard.ORIGYN_NFT ->
                OrigynNFTService(
                    canister = OrigynNFT.Nft_Canister(
                        canister = collection.canister
                    )
                )

            else -> null
        }
    }

}