package com.bity.icp_kotlin_kit.cryptography
import java.nio.ByteBuffer
import java.util.zip.CRC32

object CRC32 {

    /**
     * CRC32 is a four byte check sequence, calculated as defined by ISO 3309, ITU-T V.42,
     * and elsewhere, and stored as big-endian, i.e., the most significant byte comes first and
     * then the less significant bytes come in descending order of significance (MSB B2 B1 LSB).
     */
    const val CRC_32_LENGTH = 4

    operator fun invoke(data: ByteArray): ByteArray {
        val crc32 = CRC32()
        crc32.update(data)
        val checksum = crc32.value

        val buffer = ByteBuffer.allocate(Long.SIZE_BYTES)
        buffer.putLong(checksum)

        return buffer.array().takeLast(CRC_32_LENGTH).toByteArray()
    }
}