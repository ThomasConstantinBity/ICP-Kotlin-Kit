package com.bity.icp_kotlin_kit.domain.model

interface ICPSigningPrincipal {
    val principal: ICPPrincipal
    val rawPublicKey: ByteArray
    suspend fun sign(
        message: ByteArray,
        domain: String
    ): ByteArray
}