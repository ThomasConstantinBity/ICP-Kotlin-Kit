package com.bity.icp_kotlin_kit.domain.model.nft

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTMetadata

data class ICPNFTDetails(
    val name: String?,
    val standard: ICPNftStandard,
    val canister: ICPPrincipal,
    val metadata: ICPNFTMetadata?
)