package com.bity.icp_kotlin_kit.candid.deserializer

import com.bity.icp_kotlin_kit.data.model.candid.deserializer.CandidDeserializer
import com.bity.icp_kotlin_kit.data.model.candid.serializer.CandidSerializer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CandidDeserializerTest {

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `CandidSerializer magic bytes`() {
        assertEquals("4449444c", CandidSerializer.magicBytes.toHexString())
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `real world example`() {
        // this example has a type table with forward references
        // e.g. type 0 references type 1 which is defined later in the type table
        val data = (
                "4449444c076b02bc8a0178c5fed201016b05b79eb35d02a1c3ebfd0703c7c6b5f60a05cce" +
                        "5b6900f7feb9cdbd50f066c01a7a5f3cc0e786c01bf9bb7f00d046c01e0a9b302786c018bbdf2" +
                        "9b01786c019cbab69c0204010001040000000000000000"
                ).hexToByteArray()
        val result = CandidDeserializer.decode(data)
        assertNotNull(result)
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `function reference real world`() {
        val candidData = (
                "4449444c196c0597928bda010186dda8bf0a03a4c7a8ce0c7891fb95920d7883f4f4c40f" +
                        "106e026d7b6d046c03dec389dc0405d6a9bbae0a0bc39c99ad0f016c04ba89e5c2047893a6c6900901" +
                        "a78882820a0682f3f3910c0b6e076b05adfaedfb0108ef80e5df020cc2f5d599030dcbd6fda00b0ed5" +
                        "fce8ea0e0f6c05c6fcb60209eaca8a9e0402b98792ea077cdea7f7da0d0acb96dcb40e026c01e0a9b3" +
                        "02786e0b6c01d6f68e8001786c02eaca8a9e0402d8a38ca80d096c02fbca0102d8a38ca80d096c04fb" +
                        "ca0102c6fcb60209eaca8a9e0402d8a38ca80d096c05fbca0102c6fcb60209eaca8a9e0402d8a38ca8" +
                        "0d09cb96dcb40e026d116c03c5b39af80712e2e8ada00878e6a99ef809786a0113011401016c02e2e8" +
                        "ada00878e6a99ef809786b02bc8a0115c5fed201166c0186dda8bf0a036b02b0ad8cd40417b0ad8fcd" +
                        "0c186c02c1a482a6057880d5dbdd05786c0290c6c1960571c498b1b50d780100000100000000000000" +
                        "00000103207750c79b3d0ef35a5ffc3fc1b350d84575ae5f76d763271bb815fe6c6c2350c610270000" +
                        "0000000020cafd0a2c27f41a851837b00f019b93e741f76e4147fe74435fb7efb836826a1c10270000" +
                        "0000000000844208c55e5f17f9def690c55e5f170120fb34cfdac368b0d0853f8783dfb1776659c6ff" +
                        "3fdee5e9394db050523a5dd6efaa655d0000000000b9625d000000000000"
                )
            .hexToByteArray()
        val result = CandidDeserializer.decode(candidData)
        assertNotNull(result)
    }
}