package com.bity.icp_kotlin_kit.candid

import com.bity.icp_kotlin_kit.data.model.candid.CandidEncoder
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KClass

internal class CandidEncoderTest {

    @ParameterizedTest(name = "[{index}] - encoding {0}")
    @MethodSource("unsignedValue")
    fun `encode unsigned value`(
        value: Any,
        expectedResult: CandidValue
    ) {
        assertEquals(
            expectedResult,
            CandidEncoder(value)
        )
    }

    @ParameterizedTest(name = "[{index}] - encoding {0}")
    @MethodSource("signedValue")
    fun `encode signed value`(
        value: Any,
        expectedResult: CandidValue
    ) {
        assertEquals(
            expectedResult,
            CandidEncoder(value)
        )
    }

    @ParameterizedTest(name = "[{index}] - encoding {0}")
    @MethodSource("floatValue")
    fun `encode float value`(
        value: Float,
        expectedResult: CandidValue
    ) {
        assertEquals(
            expectedResult,
            CandidEncoder(value)
        )
    }

    @ParameterizedTest(name = "[{index}] - encoding {0}")
    @MethodSource("doubleValue")
    fun `encode double value`(
        value: Double,
        expectedResult: CandidValue
    ) {
        assertEquals(
            expectedResult,
            CandidEncoder(value)
        )
    }

    @ParameterizedTest(name = "[{index}] - encoding {0}")
    @MethodSource("booleanValue")
    fun `encode boolean value`(
        value: Boolean,
        expectedResult: CandidValue
    ) {
        assertEquals(
            expectedResult,
            CandidEncoder(value)
        )
    }

    @ParameterizedTest(name = "[{index}] - encoding {0}")
    @MethodSource("stringValue")
    fun `encode string value`(
        value: String,
        expectedResult: CandidValue
    ) {
        assertEquals(
            expectedResult,
            CandidEncoder(value)
        )
    }

    @ParameterizedTest(name = "[{index}] - encoding {0}")
    @MethodSource("byteArrayValue")
    fun `encode byteArray value`(
        value: ByteArray,
        expectedResult: CandidValue
    ) {
        assertEquals(
            expectedResult,
            CandidEncoder(value)
        )
    }

    @Disabled
    @ParameterizedTest(name = "[{index}] - encoding {0}")
    @MethodSource("nullValue")
    fun `encode null value`(
        expectedClass: KClass<*>,
        expectedResult: CandidValue,
        expectedClassNullable: Boolean
    ) {
        assertEquals(
            expectedResult,
            CandidEncoder(
                arg = null,
                expectedClass = expectedClass,
                expectedClassNullable = expectedClassNullable
            )
        )
    }

    companion object {

        @JvmStatic
        private fun unsignedValue() = listOf(

            Arguments.of(
                0.toUByte(),
                CandidValue.Natural8(0U)
            ),

            Arguments.of(
                0.toUShort(),
                CandidValue.Natural16(0U)
            ),

            Arguments.of(
                1.toUShort(),
                CandidValue.Natural16(1U)
            ),

            Arguments.of(
                2.toUInt(),
                CandidValue.Natural32(2U)
            ),

            Arguments.of(
                123.toULong(),
                CandidValue.Natural64(123U)
            )
        )

        @JvmStatic
        private fun signedValue() = listOf(
            Arguments.of(
                (-1).toByte(),
                CandidValue.Integer8(-1)
            ),

            Arguments.of(
                (-5).toShort(),
                CandidValue.Integer16(-5)
            ),

            Arguments.of(
                -100,
                CandidValue.Integer32(-100)
            ),

            Arguments.of(
                (-34567).toLong(),
                CandidValue.Integer64(-34567)
            ),
        )

        @JvmStatic
        private fun floatValue() = listOf(
            Arguments.of(
                1.5.toFloat(),
                CandidValue.Float32(1.5.toFloat())
            )
        )

        @JvmStatic
        private fun doubleValue() = listOf(
            Arguments.of(
                1.5554,
                CandidValue.Float64(1.5554)
            )
        )

        @JvmStatic
        private fun booleanValue() = listOf(
            Arguments.of(
                true,
                CandidValue.Bool(true)
            ),
            Arguments.of(
                false,
                CandidValue.Bool(false)
            )
        )

        @JvmStatic
        private fun stringValue() = listOf(
            Arguments.of(
                "some simple text",
                CandidValue.Text("some simple text")
            )
        )

        @JvmStatic
        private fun byteArrayValue() = listOf(
            Arguments.of(
                byteArrayOf(),
                CandidValue.Blob(byteArrayOf())
            ),
            Arguments.of(
                byteArrayOf(0x00, 0xAA.toByte(), 0xCD.toByte()),
                CandidValue.Blob(byteArrayOf(0x00, 0xAA.toByte(), 0xCD.toByte()))
            )
        )

        @JvmStatic
        private fun nullValue() = listOf(
            Arguments.of(
                Boolean::class.java,
                CandidValue.Option(
                    containedType = CandidType.Bool
                ),
                false
            ),
            /* Arguments.of(
                String::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.TEXT)
                ),
                false
            ),
            Arguments.of(
                Long::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.INTEGER64)
                ),
                true
            ),
            Arguments.of(
                UByte::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.NATURAL8)
                ),
                true
            ),
            Arguments.of(
                UShort::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.NATURAL16)
                ),
                true
            ),
            Arguments.of(
                UInt::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.NATURAL32)
                ),
                true
            ),
            Arguments.of(
                ULong::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.NATURAL64)
                ),
                true
            ),
            Arguments.of(
                Byte::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.INTEGER8)
                ),
                true
            ),
            Arguments.of(
                Short::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.INTEGER16)
                ),
                true
            ),
            Arguments.of(
                Int::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.INTEGER32)
                ),
                true
            ),
            Arguments.of(
                Long::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.INTEGER64)
                ),
                true
            ),
            Arguments.of(
                Float::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.FLOAT32)
                ),
                true
            ),
            Arguments.of(
                Double::class.java,
                CandidValue.Option(
                    containedType = CandidType.Primitive(CandidPrimitiveType.FLOAT64)
                ),
                true
            ) */
        )

        /**
         *     (Double(1.5), .float64(1.5)),
         *
         *     (BigUInt(5), .natural(5)),
         *     (BigInt(-5), .integer(-5)),
         *
         *     (Optional(8), .option(.integer64(8))),
         *     (BigUInt?.none, .option(.natural)),
         *     (BigInt?.none, .option(.integer)),
         *     (Optional(Optional(8)), .option(.option(.integer64(8)))),
         *     (Optional(Int?.none), .option(CandidValue.option(.integer64))),
         */
    }
}