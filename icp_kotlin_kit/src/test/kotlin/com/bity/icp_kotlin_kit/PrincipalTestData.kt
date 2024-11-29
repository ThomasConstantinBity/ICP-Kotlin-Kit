package com.bity.icp_kotlin_kit

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.google.common.io.BaseEncoding
import kotlin.random.Random

interface PrincipalTestData {

    fun aPrincipal(): ICPPrincipal {
        return ICPPrincipal(generateRandomBase32String())
    }

    fun aCanister(): ICPPrincipal {
        return ICPPrincipal(generateRandomBase32String())
    }

    private fun generateRandomBase32String(): String {
        val randomData = ByteArray(Random.nextInt(1, 64 + 1))
        Random.nextBytes(randomData)

        val checksum = CRC32(randomData)

        val dataWithChecksum = checksum + randomData

        val base32Encoded = BaseEncoding.base32().encode(dataWithChecksum)
            .lowercase()
            .filter { it != '=' }
        return base32Encoded.grouped("-", 5)
    }

    private fun String.grouped(separator: String, groupSize: Int): String {
        return this.chunked(groupSize).joinToString(separator)
    }

    private fun CRC32(data: ByteArray): ByteArray {
        val crc32 = java.util.zip.CRC32()
        crc32.update(data)
        val checksumValue = crc32.value
        return byteArrayOf(
            (checksumValue shr 24).toByte(),
            (checksumValue shr 16).toByte(),
            (checksumValue shr 8).toByte(),
            (checksumValue).toByte()
        )
    }
}