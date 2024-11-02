package com.bity.icp_kotlin_kit.domain.provider

import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

internal interface NFTActor {
    suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails>
}