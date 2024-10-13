package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.cryptography.CRC32
import com.bity.icp_kotlin_kit.util.cryptography.ICPAccountCryptography
import com.google.common.io.BaseEncoding

@OptIn(ExperimentalStdlibApi::class)
class ICPAccount(
    val principal: ICPPrincipal,
    val subAccountId: ByteArray
) {
    val accountId = ICPAccountCryptography.generateAccountId(principal, subAccountId)
    val address = accountId.toHexString()

    fun textualRepresentation(): String {
        if(subAccountId.contentEquals(DEFAULT_SUB_ACCOUNT_ID))
            return principal.string
        val checksum = checksum(principal, subAccountId)
        val hexSubAccount = subAccountId.toHexString().trimStart { it == '0' }
        return "${principal.string}-$checksum.$hexSubAccount"
    }

    companion object {

        private const val SUB_ACCOUNT_ID_LENGTH = 32
        val DEFAULT_SUB_ACCOUNT_ID = ByteArray(SUB_ACCOUNT_ID_LENGTH) { 0 }

        fun mainAccount(principal: ICPPrincipal): ICPAccount =
            ICPAccount(
                principal = principal,
                subAccountId = DEFAULT_SUB_ACCOUNT_ID
            )

        private fun checksum(principal: ICPPrincipal, subAccountId: ByteArray): String {
            val checksum = CRC32(principal.bytes + subAccountId)
            val base32Checksum = BaseEncoding.base32().encode(checksum)
            return base32Checksum
        }
    }
}