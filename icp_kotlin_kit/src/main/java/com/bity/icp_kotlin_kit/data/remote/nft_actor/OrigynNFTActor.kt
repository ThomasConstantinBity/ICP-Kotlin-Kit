package com.bity.icp_kotlin_kit.data.remote.nft_actor

import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.provider.NFTActor

class OrigynNFTActor: NFTActor {
    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> {
        TODO("Not yet implemented")
    }
}