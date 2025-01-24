package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard

data class ICPNftCollection(
    val standard: ICPNftStandard,
    val name: String,
    val description: String,
    val iconURL: String,
    val canister: ICPPrincipal
)