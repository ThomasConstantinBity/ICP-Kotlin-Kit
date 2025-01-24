package com.bity.icp_kotlin_kit.candid

import com.bity.icp_kotlin_kit.data.model.candid.deserializer.CandidDeserializer
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidRecord
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVariant
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVector
import com.bity.icp_kotlin_kit.data.model.candid.serializer.CandidSerializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigInteger

internal class CandidConversionTest {

    @ParameterizedTest
    @MethodSource("singleValues")
    fun `Single value conversion`(
        candidValue: CandidValue,
        expectedSerialized: ByteArray
    ) {
        val expected = CandidSerializer.magicBytes + expectedSerialized
        val encoded = CandidSerializer.encode(candidValue)
        assertTrue(
            expected.contentEquals(encoded)
        )
        val decoded = CandidDeserializer.decode(encoded)
        assertEquals(
            candidValue,
            decoded.first()
        )
    }

    @ParameterizedTest
    @MethodSource("multipleValues")
    fun `Multiple values conversion`(
        candidValues: List<CandidValue>,
        expectedSerialized: ByteArray
    ) {
        val encoded = CandidSerializer.encode(candidValues)
        val expected = CandidSerializer.magicBytes + expectedSerialized
        assertTrue(
            expected.contentEquals(encoded)
        )
        val decoded = CandidDeserializer.decode(encoded)
        assertTrue(candidValues == decoded)
    }

    companion object {
        @JvmStatic
        fun singleValues() = listOf(
            Arguments.of(CandidValue.Null, byteArrayOf(0x00, 0x01, 0x7F)),
            Arguments.of(CandidValue.Bool(false), byteArrayOf(0x00, 0x01, 0x7E, 0x00)),
            Arguments.of(CandidValue.Bool(true), byteArrayOf(0x00, 0x01, 0x7E, 0x01)),
            Arguments.of(CandidValue.Natural(BigInteger.ZERO), byteArrayOf(0x00, 0x01, 0x7D, 0x0)),
            Arguments.of(CandidValue.Natural(BigInteger.ONE), byteArrayOf(0x00, 0x01, 0x7D, 0x01)),
            Arguments.of(
                CandidValue.Natural(BigInteger.valueOf(300)), byteArrayOf(
                    0x00, 0x01, 0x7D, 0xAC.toByte(), 0x02
                )
            ),
            Arguments.of(
                CandidValue.Integer(BigInteger.valueOf(-129)), byteArrayOf(
                    0x00, 0x01, 0x7C, 0xFF.toByte(), 0x7E
                )
            ),
            Arguments.of(CandidValue.Natural8(5.toUByte()), byteArrayOf(0x00, 0x01, 0x7B, 0x05)),
            Arguments.of(CandidValue.Natural16(5.toUShort()), byteArrayOf(0x00, 0x01, 0x7A, 0x05, 0x00)),
            Arguments.of(
                CandidValue.Natural32(5.toUInt()), byteArrayOf(0x00, 0x01, 0x79, 0x05, 0x00, 0x00, 0x00)
            ),
            Arguments.of(
                CandidValue.Natural64(5.toULong()), byteArrayOf(
                    0x00, 0x01, 0x78, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
                )
            ),
            Arguments.of(CandidValue.Integer8(-5), byteArrayOf(0x00, 0x01, 0x77, 0xfb.toByte())),
            Arguments.of(
                CandidValue.Integer16(-5), byteArrayOf(0x00, 0x01, 0x76, 0xfB.toByte(), 0xff.toByte())
            ),
            Arguments.of(
                CandidValue.Integer32(-5), byteArrayOf(
                    0x00, 0x01, 0x75, 0xfB.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte()
                )
            ),
            Arguments.of(
                CandidValue.Integer64(-5), byteArrayOf(
                    0x00,
                    0x01,
                    0x74,
                    0xfB.toByte(),
                    0xff.toByte(),
                    0xff.toByte(),
                    0xff.toByte(),
                    0xff.toByte(),
                    0xff.toByte(),
                    0xff.toByte(),
                    0xff.toByte()
                )
            ),
            Arguments.of(
                CandidValue.Float32(-0.5F), byteArrayOf(
                    0x00, 0x01, 0x73, 0x00, 0x00, 0x00, 0xbf.toByte()
                )
            ),
            Arguments.of(
                CandidValue.Float32(-0.768F), byteArrayOf(
                    0x00, 0x01, 0x73, 0xa6.toByte(), 0x9b.toByte(), 0x44, 0xbf.toByte()
                )
            ),
            Arguments.of(
                CandidValue.Float64(-0.768), byteArrayOf(
                    0x00,
                    0x01,
                    0x72,
                    0xFA.toByte(),
                    0x7E,
                    0x6A,
                    0xBC.toByte(),
                    0x74,
                    0x93.toByte(),
                    0xE8.toByte(),
                    0xBF.toByte()
                )
            ),
            Arguments.of(CandidValue.Reserved, byteArrayOf(0x00, 0x01, 0x70)),
            Arguments.of(CandidValue.Empty, byteArrayOf(0x00, 0x01, 0x6F)),
            Arguments.of(CandidValue.Text("a"), byteArrayOf(0x00, 0x01, 0x71, 0x01, 0x61)),
            Arguments.of(
                CandidValue.Text("%±§"), byteArrayOf(
                    0x00,
                    0x01,
                    0x71,
                    0x05,
                    0x25,
                    0xc2.toByte(),
                    0xb1.toByte(),
                    0xc2.toByte(),
                    0xa7.toByte()
                )
            ),
            // 1 type in table, option, bool, 1 candidValue, value of type 0, null value
            Arguments.of(
                CandidValue.Option(containedType = CandidType.Bool), byteArrayOf(
                    0x01, 0x6E, 0x7E, 0x01, 0x00, 0x00
                )
            ),
            // 1 type in table, option, bool, 1 candidValue, value of type 0, non-null value, true
            Arguments.of(
                CandidValue.Option(CandidValue.Bool(true)), byteArrayOf(
                    0x01, 0x6E, 0x7E, 0x01, 0x00, 0x01, 0x01
                )
            ),
            // 1 type in table, vector, bool, 1 candidValue, value of type 0, 0 elements
            Arguments.of(
                CandidValue.Vector(containedType = CandidType.Bool), byteArrayOf(
                    0x01, 0x6D, 0x7E, 0x01, 0x00, 0x00
                )
            ),
            // 1 type in table, vector, bool, 1 candidValue, value of type 0, 2 elements, true, false
            Arguments.of(
                CandidValue.Vector(
                    CandidVector(
                        listOf(
                            CandidValue.Bool(true), CandidValue.Bool(false)
                        )
                    )
                ), byteArrayOf(0x01, 0x6D, 0x7E, 0x01, 0x00, 0x02, 0x01, 0x00)
            ),
            // 1 type in table, vector, nat8, 1 candidValue, value of type 0, 0 elements
            Arguments.of(
                CandidValue.Blob(byteArrayOf()), byteArrayOf(0x01, 0x6D, 0x7B, 0x01, 0x00, 0x00)
            ),
            // 1 type in table, vector, nat8, 1 candidValue, value of type 0, 2 elements, 127, 128
            Arguments.of(
                CandidValue.Blob(
                    byteArrayOf(127.toByte(), 128.toByte())
                ), byteArrayOf(0x01, 0x6D, 0x7B, 0x01, 0x00, 0x02, 0x7F, 0x80.toByte())
            ),
            // 1 type in table, record, 0 rows, 1 candidValue, value of type 0,
            Arguments.of(
                CandidValue.Record(CandidRecord.init(mapOf())),
                byteArrayOf(0x01, 0x6C, 0x00, 0x01, 0x00)
            ),
            // 1 type in table, record, 1 row, leb(hash("a")), .empty, 1 candidValue, value of type 0,
            Arguments.of(
                CandidValue.Record(
                    CandidRecord.init(
                        mapOf("a" to CandidValue.Empty)
                    )
                ), byteArrayOf(0x01, 0x6C, 0x01, 97, 0x6F, 0x01, 0x00)
            ),
            // 1 type in table, record, 2 rows, leb(hash("a")), .natural, leb(hash("b")), .natural8, 1 candidValue, value of type 0, 0x01, 0x02
            Arguments.of(
                CandidValue.Record(
                    CandidRecord.init(
                        hashMapOf(
                            "a" to CandidValue.Natural(BigInteger.valueOf(1)),
                            "b" to CandidValue.Natural8(2U)
                        )
                    )
                ), byteArrayOf(0x01, 0x6C, 0x02, 97, 0x7D, 98, 0x7B, 0x01, 0x00, 0x01, 0x02)
            ),
            // 2 types in table, (0)vector, bool, (1)option, referencing type 0, 1 candidValue, value of type 1, option present, 2 values, true, false
            Arguments.of(
                CandidValue.Option(
                    CandidValue.Vector(
                        CandidVector(
                            listOf(
                                CandidValue.Bool(true), CandidValue.Bool(false)
                            )
                        )
                    )
                ), byteArrayOf(0x02, 0x6D, 0x7E, 0x6E, 0x00, 0x01, 0x01, 0x01, 0x02, 0x01, 0x00)
            ),
            // 3 types in table, (0)vector, nat8, (1) vector, ref 0, (2)option, ref 1, 1 candidValue, value of type 2, option present, 2 values, length 0, length 2, leb(127), leb(128)
            Arguments.of(
                CandidValue.Option(
                    CandidValue.Vector(
                        CandidVector(
                            listOf(
                                CandidValue.Blob(byteArrayOf()),
                                CandidValue.Blob(byteArrayOf(127.toByte(), 128.toByte()))
                            )
                        )
                    )
                ), byteArrayOf(
                    0x03,
                    0x6D,
                    0x7B,
                    0x6D,
                    0x00,
                    0x6E,
                    0x01,
                    0x01,
                    0x02,
                    0x01,
                    0x02,
                    0x00,
                    0x02,
                    0x7F,
                    0x80.toByte()
                )
            ),
            // 4 types in table, (0)vector, nat8, (1) vector, ref 0, (2) record, 2 keys, leb(hash("a")), ref 0, leb(hash("b")), ref 1, (3)option, ref 2, 1 candidValue, value of type 3, option present, length 1, 0x44, length 1, length 2, 0x45, 0x47
            Arguments.of(
                CandidValue.Option(
                    CandidValue.Record(
                        CandidRecord.init(
                            hashMapOf(
                                "a" to CandidValue.Blob(byteArrayOf(0x44)), "b" to CandidValue.Vector(
                                    CandidVector(
                                        listOf(CandidValue.Blob(byteArrayOf(0x45, 0x47)))
                                    )
                                )
                            )
                        )
                    )
                ), byteArrayOf(
                    4,
                    0x6D,
                    0x7B,
                    0x6D,
                    0,
                    0x6C,
                    2,
                    97,
                    0,
                    98,
                    1,
                    0x6E,
                    2,
                    0x01,
                    3,
                    1,
                    1,
                    0x44,
                    1,
                    2,
                    0x45,
                    0x47
                )
            ),
            // 2 types in table, (0) vector, nat8, (1) variant, 3 keys, leb(hash("a")), bool, leb(hash("b")), nat8, leb(hash("c")), type 0, 1 candidValue, type 1, row 1, 15
            Arguments.of(
                CandidValue.Variant(
                    CandidVariant(
                        hashMapOf(
                            "a" to CandidType.Bool,
                            "b" to CandidType.Natural8,
                            "c" to CandidType.Vector(CandidType.Natural8)
                        ),
                        Pair("b", CandidValue.Natural8(15U))
                    )
                ), byteArrayOf(2, 0x6D, 0x7B, 0x6B, 3, 97, 0x7E, 98, 0x7B, 99, 0, 1, 1, 1, 0x0f)
            )
        )

        @JvmStatic
        fun multipleValues() = listOf(
            Arguments.of(
                emptyList<CandidValue>(),
                byteArrayOf(0x00, 0x00)
            ),
            Arguments.of(
                listOf(
                    CandidValue.Natural8(0U),
                    CandidValue.Natural8(1U),
                    CandidValue.Natural8(2U)
                ), byteArrayOf(0x00, 0x03, 0x7B, 0x7B, 0x7B, 0, 1, 2)
            ),
            Arguments.of(
                listOf(
                    CandidValue.Natural8(0U),
                    CandidValue.Natural16(258.toUShort()),
                    CandidValue.Natural8(2U)
                ), byteArrayOf(0x00, 0x03, 0x7B, 0x7A, 0x7B, 0, 2, 1, 2)
            ),
            // 4 types in table, (0)vector, nat8, (1) vector, ref 0, (2) record, 2 keys, leb(hash("a")), ref 0, leb(hash("b")), ref 1, (3)option, ref 2, 2 candidValues, value of type 3, value of type 2, option present, length 1, 0x44, length 1, length 2, 0x45, 0x47,  length 1, 0x43, length 1, length 2, 0x40, 0x41
            Arguments.of(
                listOf(
                    // First Item
                    CandidValue.Option(
                        CandidValue.Record(
                            CandidRecord.init(
                                hashMapOf(
                                    "a" to CandidValue.Blob(byteArrayOf(0x44)),
                                    "b" to CandidValue.Vector(
                                        CandidVector(
                                            listOf(
                                                CandidValue.Blob(byteArrayOf(0x45, 0x47))
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    ),
                    // Second Item
                    CandidValue.Record(
                        CandidRecord.init(
                            hashMapOf(
                                "a" to CandidValue.Blob(byteArrayOf(0x43)),
                                "b" to CandidValue.Vector(
                                    CandidVector(
                                        listOf(
                                            CandidValue.Blob(byteArrayOf(0x40, 0x41))
                                        )
                                    )
                                )
                            )
                        )
                    )
                ), byteArrayOf(4, 0x6D, 0x7B, 0x6D, 0, 0x6C, 2, 97, 0, 98, 1, 0x6E, 2, 0x02, 3, 2, 1, 1, 0x44, 1, 2, 0x45, 0x47, 1, 0x43, 1, 2, 0x40, 0x41)
            )
        )
    }
}