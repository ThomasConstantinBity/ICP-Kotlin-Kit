package com.bity.icp_kotlin_kit.data.service.nft.custom.chain_fusion_toonis

import com.fasterxml.jackson.annotation.JsonProperty

data class ChainFusionToonisNonFungibleMetadata(
    @JsonProperty("url") val url: String,
    @JsonProperty("thumb") val thumb: String,
    @JsonProperty("mimeType") val mimeType: String
)