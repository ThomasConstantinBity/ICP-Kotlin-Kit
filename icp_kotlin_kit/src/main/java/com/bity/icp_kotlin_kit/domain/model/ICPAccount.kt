package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.cryptography.CRC32
import com.bity.icp_kotlin_kit.domain.exception.ICPAccountException
import com.bity.icp_kotlin_kit.util.cryptography.ICPAccountCryptography
import com.google.common.io.BaseEncoding
import kotlin.jvm.Throws

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

    private fun checksum(): String =
        checksum(
            principal = principal,
            subAccountId = subAccountId
        )

    companion object {

        private const val SUB_ACCOUNT_ID_LENGTH = 32
        val DEFAULT_SUB_ACCOUNT_ID = ByteArray(SUB_ACCOUNT_ID_LENGTH) { 0 }

        fun mainAccount(principal: ICPPrincipal): ICPAccount =
            ICPAccount(
                principal = principal,
                subAccountId = DEFAULT_SUB_ACCOUNT_ID
            )

        /**
         * Creates an [ICPAccount] from its textual representation.
         *
         * This method can handle two types of textual representations:
         *
         * 1.  **Principal and Sub-account ID (with Checksum):**
         *     `aaaaa-aa-checksum.subAccountId`
         *     -   `aaaaa-aa`: The textual representation of the principal, excluding the checksum.
         *     -   `checksum`: The checksum, derived from the principal and sub-account ID.
         *     -   `subAccountId`: The sub-account ID, represented as a hexadecimal string.
         *
         * 2.  **Principal Only:**
         *     `aaaaa-aa-bbbb`
         *     - `aaaaa-aa-bbbb`: The textual representation of the principal.
         *     - In this case the subaccount id is the default one (all zeros)
         *
         * @param textualRepresentation The textual representation of the IC account.
         * @return An [ICPAccount] object representing the parsed account.
         *
         * @throws ICPAccountException.InvalidTextualRepresentation if the textual representation is invalid.
         *         This can occur if:
         *         - The representation is not in the correct format (e.g., missing parts, incorrect separators).
         *         - The sub-account ID is not a valid hexadecimal string.
         *         - The sub-account ID has more than [SUB_ACCOUNT_ID_LENGTH] bytes when converted from hex.
         *         - The principal string is invalid.
         * @throws ICPAccountException.InvalidChecksum if the provided checksum does not match the calculated checksum.
         */
        fun fromTextualRepresentation(textualRepresentation: String): ICPAccount {
            return if(textualRepresentation.contains(".")) {
                val dotSplit = textualRepresentation.split(".")
                if(dotSplit.size != 2) throw ICPAccountException.InvalidTextualRepresentation()
                var subAccountString = dotSplit.last()
                if(subAccountString.length % 2 == 1)
                    subAccountString = "0$subAccountString"
                val subAccountId = try {
                    subAccountString.hexToByteArray()
                } catch (_: Throwable) {
                    throw ICPAccountException.InvalidTextualRepresentation()
                }
                if(subAccountId.size > SUB_ACCOUNT_ID_LENGTH) throw ICPAccountException.InvalidTextualRepresentation()
                subAccountId.copyOf(SUB_ACCOUNT_ID_LENGTH)
                val dashSplit = dotSplit.first().split("-")
                if(dashSplit.size < 3) throw ICPAccountException.InvalidTextualRepresentation()
                val checksumString = dashSplit.last()
                val principalString = dashSplit.dropLast(1).joinToString("-")
                val principal = ICPPrincipal(principalString)
                val expectedChecksum = checksum(principal, subAccountId)
                if (checksumString != expectedChecksum)
                    throw ICPAccountException.InvalidChecksum()
                ICPAccount(
                    principal = principal,
                    subAccountId = subAccountId
                )
            } else try {
                val principal = ICPPrincipal(textualRepresentation)
                ICPAccount(
                    principal = principal,
                    subAccountId = DEFAULT_SUB_ACCOUNT_ID
                )
            } catch (_: Throwable) {
                throw ICPAccountException.InvalidTextualRepresentation()
            }
        }

        private fun checksum(
            principal: ICPPrincipal,
            subAccountId: ByteArray
        ): String {
            val checksum = CRC32(principal.bytes + subAccountId)
            return BaseEncoding.base32().encode(checksum)
        }
    }
}