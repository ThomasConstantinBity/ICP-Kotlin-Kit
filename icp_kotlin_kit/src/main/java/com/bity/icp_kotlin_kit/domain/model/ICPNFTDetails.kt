package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard

data class ICPNFTDetails(
    val name: String?,
    val standard: ICPNftStandard,
    val canister: ICPPrincipal
)