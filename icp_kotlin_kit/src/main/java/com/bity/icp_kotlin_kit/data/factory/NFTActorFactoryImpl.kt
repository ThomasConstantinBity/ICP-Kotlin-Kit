package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.remote.nft_actor.EXTNFTActor
import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.data.remote.nft_actor.ICRC7NFTActor
import com.bity.icp_kotlin_kit.domain.factory.NFTActorFactory
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.provider.NFTActor

internal class NFTActorFactoryImpl: NFTActorFactory {
    override fun createActor(collection: ICPNftCollection): NFTActor? {
        return when(collection.standard) {
            ICPNftStandard.EXT -> null // EXTNFTActor()
            ICPNftStandard.ICRC7 ->
                ICRC7NFTActor(
                    service = DBANFTService(
                        canister = collection.canister
                    )
                )
            ICPNftStandard.ORIGYN_NFT -> null
            else -> null
        }
    }
}