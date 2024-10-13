package com.bity.icp_kotlin_kit.candid.model

import com.bity.icp_kotlin_kit.cryptography.ICPCryptography

class CandidPrincipal(
    val bytes: ByteArray,
    private val string: String
) {
    constructor(string: String): this(
        bytes = ICPCryptography.decodeCanonicalText(string),
        string = string
    )

    constructor(bytes: ByteArray): this(
        bytes = bytes,
        string = ICPCryptography.encodeCanonicalText(bytes)
    )
}