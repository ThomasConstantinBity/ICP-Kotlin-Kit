package com.bity.icp_kotlin_kit.cryptography

import java.security.MessageDigest

object SHA256 {

    private const val SHA_256_ALGORITHM_NAME = "SHA-256"

    private val messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM_NAME)

    fun sha256(data: ByteArray): ByteArray =
        messageDigest.digest(data)

    fun doubleSha256(data: ByteArray): ByteArray =
        sha256(sha256(data))
}