package com.bity.icp_kotlin_kit.domain.model.nft.metadata

data class ICPNFTEXTMetadata(
    val nftImageUrl: String,
    override val thumbnailUrl: String
) : ICPNFTMetadata