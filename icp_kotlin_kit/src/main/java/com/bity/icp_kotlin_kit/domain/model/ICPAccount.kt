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

    fun getTextualRepresentation(): String {
        return if(subAccountId.contentEquals(DEFAULT_SUB_ACCOUNT_ID)) {
            principal.string
        } else {
            val checksum = checksum()
            val hexSubAccount = subAccountId.toHexString().trimStart { it == '0' }
            "${principal.string}-$checksum.$hexSubAccount"
        }
    }

    private fun checksum(): String {
        val checksum = CRC32(principal.bytes + subAccountId)
        return BaseEncoding.base32().encode(checksum)
    }

    companion object {

        private const val SUB_ACCOUNT_ID_LENGTH = 32
        val DEFAULT_SUB_ACCOUNT_ID = ByteArray(SUB_ACCOUNT_ID_LENGTH) { 0 }

        fun mainAccount(principal: ICPPrincipal): ICPAccount =
            ICPAccount(
                principal = principal,
                subAccountId = DEFAULT_SUB_ACCOUNT_ID
            )
        
    }
}