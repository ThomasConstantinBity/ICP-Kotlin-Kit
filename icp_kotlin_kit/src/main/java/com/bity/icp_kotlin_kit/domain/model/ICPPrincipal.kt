package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.cryptography.DER
import com.bity.icp_kotlin_kit.cryptography.ICPCryptography
import com.bity.icp_kotlin_kit.cryptography.SHA224
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel

// Source:  from https://internetcomputer.org/docs/current/references/ic-interface-spec/#principal
class ICPPrincipal internal constructor(
    val bytes: ByteArray,
    val string: String
) {

    constructor(bytes: ByteArray): this(
        string = ICPCryptography.encodeCanonicalText(bytes),
        bytes = bytes
    )

    constructor(string: String): this(
        string = string,
        bytes = ICPCryptography.decodeCanonicalText(string)
    )

    companion object {

        /** Principal with Self-Authenticating ID
         * These have the form H(ec_public_key) · 0x02 (29 bytes).
         * ec_public_key in raw uncompressed form (65 bytes) 0x04·X·Y
         **/
        fun selfAuthenticatingPrincipal(uncompressedPublicKey: ByteArray): ICPPrincipal {
            val serialized = DER.serialise(uncompressedPublicKey)
            val hash = SHA224(serialized)
            val bytes = hash + 0x02.toByte()
            return ICPPrincipal(bytes)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ICPPrincipal

        return string == other.string
    }

    override fun hashCode(): Int {
        return string.hashCode()
    }
}

fun ICPPrincipal.toDataModel(): ICPPrincipalApiModel =
    ICPPrincipalApiModel(
        bytes = bytes,
        string = string
    )