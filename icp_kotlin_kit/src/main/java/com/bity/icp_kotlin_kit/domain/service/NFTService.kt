package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

internal interface NFTService {
    suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails>
}