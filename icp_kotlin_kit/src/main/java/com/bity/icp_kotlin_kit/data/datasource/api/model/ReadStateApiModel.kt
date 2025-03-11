package com.bity.icp_kotlin_kit.data.datasource.api.model

import com.bity.icp_kotlin_kit.data.datasource.api.enum.ContentRequestType
import com.fasterxml.jackson.annotation.JsonProperty

internal class ReadStateApiModel(
    @JsonProperty("request_type") override val request_type: ContentRequestType,
    @JsonProperty("sender") override val sender: ByteArray,
    @JsonProperty("nonce") override val nonce: ByteArray,
    @JsonProperty("ingress_expiry") override val ingress_expiry: Long,
    @JsonProperty("paths") val paths: List<List<ByteArray>>
): ContentApiModel()