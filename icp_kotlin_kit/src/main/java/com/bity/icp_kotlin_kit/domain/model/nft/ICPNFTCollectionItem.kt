package com.bity.icp_kotlin_kit.domain.model.nft

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import java.math.BigInteger

data class ICPNFTCollectionItem(
    val id: BigInteger,
    val nftId: String,
    val nftImageUrl: String,
    val thumbnail: String
)