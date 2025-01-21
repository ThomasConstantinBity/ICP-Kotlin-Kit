package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.service.NFTService

class OrigynNFTService: NFTService {
    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> {
        TODO("Not yet implemented")
    }
}