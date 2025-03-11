package com.bity.icp_kotlin_kit.domain.model.nft

import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTMetadata
import java.math.BigInteger

data class ICPNFTCollectionItem(
    val id: BigInteger,
    val nftId: String,
    val metadata: ICPNFTMetadata?
)