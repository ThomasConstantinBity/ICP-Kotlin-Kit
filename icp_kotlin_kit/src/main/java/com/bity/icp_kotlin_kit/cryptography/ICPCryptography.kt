package com.bity.icp_kotlin_kit.cryptography

import com.bity.icp_kotlin_kit.domain.model.error.ICPCryptographyError
import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlockTransaction
import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlockTransactionOperation
import com.bity.icp_kotlin_kit.util.cbor.UnsignedNumberCBORSerializer
import com.bity.icp_kotlin_kit.util.ext_function.grouped
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.cbor.CBORFactory
import com.google.common.io.BaseEncoding
import java.math.BigInteger

object ICPCryptography {

    private const val CANONICAL_TEXT_SEPARATOR = "-"
    private val base32 = BaseEncoding.base32()

    /**
     * The canonical textual representation of a blob b isGrouped(Base32(CRC32(b) · b)) where:
     * - CRC32 is a four byte check sequence, calculated as defined by ISO 3309, ITU-T V.42,
     *   and elsewhere, and stored as big-endian, i.e., the most significant byte comes first
     *   and then the less significant bytes come in descending order of significance (MSB B2 B1 LSB).
     * - Base32 is the Base32 encoding as defined in RFC 4648, with no padding character added.
     * - The middle dot denotes concatenation.
     * - Grouped takes an ASCII string and inserts the separator - (dash) every 5 characters.
     *   The last group may contain less than 5 characters. A separator never appears at the beginning or end.
     **/
    internal fun encodeCanonicalText(data: ByteArray): String {
        val checksum = CRC32(data)
        val dataWithChecksum = checksum + data
        val base32Encoded = base32.encode(dataWithChecksum)
            .lowercase()
            .filter { it != '=' }
        return base32Encoded.grouped(CANONICAL_TEXT_SEPARATOR, 5)
    }

    internal fun decodeCanonicalText(text: String): ByteArray {
        val degrouped = text.replace(CANONICAL_TEXT_SEPARATOR, "")
        val base32Encoded = if(degrouped.length % 2 != 0) {
            "$degrouped="
        } else {
            degrouped
        }
        val decoded = base32.decode(base32Encoded.uppercase())
        val checksum = decoded.take(CRC32.CRC_32_LENGTH).toByteArray()
        val data = decoded.copyOfRange(CRC32.CRC_32_LENGTH, decoded.size)
        val expectedChecksum = CRC32(data)
        require(expectedChecksum.contentEquals(checksum)) {
            throw ICPCryptographyError.ICPCRC32Error.InvalidChecksum()
        }
        return decoded.copyOfRange(CRC32.CRC_32_LENGTH, decoded.size)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun transactionHash(icpBlockTransaction: ICPBlockTransaction): ByteArray {
        val serialized = icpBlockTransaction.cborHexString.hexToByteArray()
        return SHA256.sha256(serialized)
    }

    fun transactionHash(
        operation: ICPBlockTransactionOperation,
        memo: BigInteger,
        createdNanos: ULong
    ): ByteArray = transactionHash(
        ICPBlockTransaction(
            memo = memo.toLong().toULong(),
            createdNanos = createdNanos,
            operation = operation
        )
    )
}

/**
 * NOTE:
 * We can't use objectMapper to serialize both `ICPBlockTransaction.cborHexString`
 * and `ICPBlockTransactionOperation.cbor` because it does not support
 * definite-length Maps (see: [kotlinx.serialization issue #1955](https://github.com/Kotlin/kotlinx.serialization/issues/1955)).
 * Check [CBOR Online Decoder](https://cbor.nemo157.com) using expected values from tests
 * for a quick understanding of the hardcoded string value.
 *
 * Jackson object mapper (https://github.com/FasterXML/jackson-dataformats-binary/issues/3) seems to
 * have a possible workaround (https://github.com/FasterXML/jackson-dataformats-binary/issues/3#issuecomment-372026871)
 * but it can't serialise ULong (ULong is a class which requires a Jackson serializer/deserializer)
 */
private val objectMapper = ObjectMapper(CBORFactory())

private val ICPBlockTransaction.cborHexString: String
    get() = "a300a1${operation.cbor}" +
            "01${UnsignedNumberCBORSerializer.serialize(memo)}" +
            "02a100${UnsignedNumberCBORSerializer.serialize(createdNanos)}"

@OptIn(ExperimentalStdlibApi::class)
private val ICPBlockTransactionOperation.cbor
    get() = when(this) {
        is ICPBlockTransactionOperation.Burn ->
            "00a200${objectMapper.writeValueAsBytes(this.from.toHexString()).toHexString()}" +
                    "01${UnsignedNumberCBORSerializer.serialize(this.amount.toLong().toULong())}"
        is ICPBlockTransactionOperation.Mint ->
            "01a200${objectMapper.writeValueAsBytes(this.to.toHexString()).toHexString()}" +
                    "01${UnsignedNumberCBORSerializer.serialize(this.amount.toLong().toULong())}"
        is ICPBlockTransactionOperation.Transfer ->
            "02a4" +
                    "00${objectMapper.writeValueAsBytes(this.from.toHexString()).toHexString()}"+
                    "01${objectMapper.writeValueAsBytes(this.to.toHexString()).toHexString()}" +
                    "02a100${UnsignedNumberCBORSerializer.serialize(this.amount.toLong().toULong())}" +
                    "03a100${UnsignedNumberCBORSerializer.serialize(this.fee!!.toLong().toULong())}"

        // TODO: Can not find any docs for this
        is ICPBlockTransactionOperation.Approve -> null
    }