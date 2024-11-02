package com.bity.icp_kotlin_kit.data.datasource.api.model

import com.bity.icp_kotlin_kit.data.datasource.api.enum.ContentRequestType
import com.bity.icp_kotlin_kit.util.OrderIndependentHash
import com.fasterxml.jackson.annotation.JsonProperty

// Need to use sneak case because of order independent hash
internal abstract class ContentApiModel {

    abstract val request_type: ContentRequestType
    abstract val sender: ByteArray
    abstract val nonce: ByteArray
    abstract val ingress_expiry: Long

    fun calculateRequestId(): ByteArray = OrderIndependentHash(this)
}