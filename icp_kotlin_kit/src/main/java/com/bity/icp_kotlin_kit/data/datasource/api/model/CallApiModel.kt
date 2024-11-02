package com.bity.icp_kotlin_kit.data.datasource.api.model

import com.bity.icp_kotlin_kit.data.datasource.api.enum.ContentRequestType
import com.fasterxml.jackson.annotation.JsonProperty

// Need to use sneak case because of order independent hash
internal class CallApiModel(
    @JsonProperty("request_type") override val request_type: ContentRequestType,
    @JsonProperty("sender") override val sender: ByteArray,
    @JsonProperty("nonce") override val nonce: ByteArray,
    @JsonProperty("ingress_expiry") override val ingress_expiry: Long,
    @JsonProperty("method_name") val method_name: String,
    @JsonProperty("canister_id") val canister_id: ByteArray,
    @JsonProperty("arg") val arg: ByteArray
): ContentApiModel()