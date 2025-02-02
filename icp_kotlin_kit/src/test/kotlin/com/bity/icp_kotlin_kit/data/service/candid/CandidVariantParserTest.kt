package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeVariant
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CandidVariantParserTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl()

    @ParameterizedTest(name = "{index}")
    @MethodSource("candidVariant")
    fun variantDefinition(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = candidTypeParserService
            .parseCandidType(typeDefinition) as? CandidTypeVariant
        assertNotNull(candidTypeDefinition)
        assertFalse(candidTypeDefinition.isTypeAlias)
        val kotlinDefinition = candidTypeDefinition.getClassDefinition()
        assertEquals(
            expected = expectedGeneratedClass
                .replace("""\s+|\t+""".toRegex(), " ")
                .trim(),
            actual = kotlinDefinition
                .replace("""\s+|\t+""".toRegex(), " ")
                .trim(),
            message = """
                Expected:
                $expectedGeneratedClass
                
                Actual:
                $kotlinDefinition
            """.trimIndent()
        )

    }

    companion object {

        @JvmStatic
        private fun candidVariant() = listOf(
            Arguments.of(
                """
                    type detail_value = variant {
                        True;
                        False;
                        I64       : int64;
                        U64       : nat64;
                        Vec       : vec detail_value;
                        Slice     : vec nat8;
                        Text      : text;
                        Float     : float64;
                        Principal : principal;
                    };
                """.trimIndent(),
                """
                    sealed class detail_value {
                        object True: detail_value()
                        object False: detail_value()
                        class I64(val int64Value: Long): detail_value()
                        class U64(val nat64Value: ULong): detail_value()
                        class Vec(val arrayValue: Array<detail_value>): detail_value()
                        class Slice(val arrayValue: Array<UByte>): detail_value()
                        class Text(val textValue: String): detail_value()
                        class Float(val candidFloatValue: Double): detail_value()
                        class Principal(val icpPrincipalApiModel: ICPPrincipalApiModel): detail_value()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type operation_error = variant {
                        NotAuthorized;
                        NonExistentItem;
                        BadParameters;
                        Unknown : text;
                    };
                """.trimIndent(),
                """
                    sealed class operation_error {
                        object NotAuthorized: operation_error()
                        object NonExistentItem: operation_error()
                        object BadParameters: operation_error()
                        class Unknown(val textValue: String): operation_error()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type operation_response = variant {
                        Ok  : opt text;
                        Err : operation_error;
                    };
                """.trimIndent(),
                """
                    sealed class operation_response {
                        class Ok(val textValue: String?): operation_response()
                        class Err(val operation_error: operation_error): operation_response()
                    }
                """.trimIndent()
            )
        )

    }

}