package com.bity.icp_kotlin_kit.cryptography

import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlockTransaction
import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlockTransactionOperation
import com.bity.icp_kotlin_kit.util.ext_function.fromHex
import com.bity.icp_kotlin_kit.util.ext_function.toHexString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigInteger

class ICPCryptographyTest {

    @ParameterizedTest(name = "Encoding {1}")
    @MethodSource("encodeCanonicalTextTestValues")
    fun encodeCanonicalText(
        data: ByteArray,
        expectedResult: String
    ) {
        val result = ICPCryptography.encodeCanonicalText(data)
        assertEquals(expectedResult, result)
    }

    @ParameterizedTest(name = "Decoding {0}")
    @MethodSource("decodeCanonicalTextTestValues")
    fun decodeCanonicalText(
        stringValue: String,
        expectedResult: ByteArray
    ) {
        val decoded = ICPCryptography.decodeCanonicalText(stringValue)
        assertTrue(expectedResult.contentEquals(decoded))
    }

    @ParameterizedTest
    @MethodSource("icpBlockTransactions")
    fun transactionHash(
        icpBlockTransaction: ICPBlockTransaction,
        expectedResult: String
    ) {
        assertEquals(
            expectedResult,
            ICPCryptography.transactionHash(icpBlockTransaction).toHexString()
        )
    }

    companion object {

        @JvmStatic
        @OptIn(ExperimentalStdlibApi::class)
        // Generated using https://internetcomputer.org/docs/current/references/id-encoding-spec#test-vectors
        private fun encodeCanonicalTextTestValues() = listOf(
            Arguments.of("000102030405060708".hexToByteArray(), "xtqug-aqaae-bagba-faydq-q"),
            Arguments.of("00".hexToByteArray(), "2ibo7-dia"),
            Arguments.of("".hexToByteArray(), "aaaaa-aa"),
            Arguments.of(
                "0102030405060708091011121314151617181920212223242526272829".hexToByteArray(),
                "iineg-fibai-bqibi-ga4ea-searc-ijrif-iwc4m-bsibb-eirsi-jjge4-ucs"
            )
        )

        @JvmStatic
        @OptIn(ExperimentalStdlibApi::class)
        // Generated using https://internetcomputer.org/docs/current/references/id-encoding-spec#test-vectors
        private fun decodeCanonicalTextTestValues() = listOf(
            Arguments.of("ryjl3-tyaaa-aaaaa-aaaba-cai", "00000000000000020101".hexToByteArray()),
            Arguments.of("xtqug-aqaae-bagba-faydq-q", "000102030405060708".hexToByteArray()),
            Arguments.of("2ibo7-dia", "00".hexToByteArray()),
            Arguments.of("2IBO7-DIA", "00".hexToByteArray()),
            Arguments.of("2Ibo7-diA", "00".hexToByteArray()),
            Arguments.of("w3gef-eqbai", "0102".hexToByteArray()),
        )

        @OptIn(ExperimentalStdlibApi::class)
        @JvmStatic
        private fun icpBlockTransactions() = listOf(
            Arguments.of(
                ICPBlockTransaction(
                    memo = 1699434456UL,
                    createdNanos = 1699434456596306831UL,
                    operation = ICPBlockTransactionOperation.Transfer(
                        from = "e9344613c9615a83cf15748d9265c77b76244166678d4a0fd5df13186c64b9c4".hexToByteArray(),
                        to = "b87f601d508c2fe1967d1eaceb4e81205e77e29dc46eb96df2028881ed4c9f8c".hexToByteArray(),
                        amount = BigInteger.valueOf(1_418_990_000),
                        fee = BigInteger.valueOf(10_000),
                        spender = null
                    )
                ),
                "e609323577ef35afca168a5b45504549889f4086dba5ef4fae8d9dfb2e9666e0"
            ),
            Arguments.of(
                ICPBlockTransaction(
                    memo = 1234UL,
                    createdNanos = 1728037586000000000UL,
                    operation = ICPBlockTransactionOperation.Transfer(
                        from = "cafd0a2c27f41a851837b00f019b93e741f76e4147fe74435fb7efb836826a1c".hexToByteArray(),
                        to = "cafd0a2c27f41a851837b00f019b93e741f76e4147fe74435fb7efb836826a1c".hexToByteArray(),
                        amount = BigInteger.valueOf(1_000_000),
                        fee = BigInteger.valueOf(10_000),
                        spender = null
                    )
                ),
                "e7fb27fc14d6439b3aceee0b88125d7f4ab902a583edfb2586ff8f32e9866f4b"
            )
        )
    }
}