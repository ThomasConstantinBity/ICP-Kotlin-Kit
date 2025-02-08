package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeRecord
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CandidRecordParserTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl()

    @ParameterizedTest(name = "{index}")
    @MethodSource("candidRecord")
    fun variantDefinition(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = candidTypeParserService
            .parseCandidType(typeDefinition) as? CandidTypeRecord
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
        private fun candidRecord() = listOf(
            Arguments.of(
                """
                    type add_token_input = record {
                        name        : text;
                        description : text;
                        thumbnail   : text;
                        frontend    : opt text;
                        principal_id : principal;
                        details     : vec record { text; detail_value }
                    };
                """.trimIndent(),
                """
                    class add_token_input(
                        val name: String,
                        val description: String,
                        val thumbnail: String,
                        val frontend: String?,
                        val principal_id: ICPPrincipalApiModel,
                        val details: Array<Details>
                    ) {
                        class Details(
                            val textValue: String,
                            val detail_value: detail_value
                        )
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type token = record {
                        name        : text;
                        description : text;
                        thumbnail   : text;
                        frontend    : opt text;
                        principal_id : principal;
                        submitter: principal;
                        last_updated_by: principal;
                        last_updated_at: nat64;
                        details     : vec record { text; detail_value }
                    };
                """.trimIndent(),
                """
                    class token(
                        val name: String,
                        val description: String,
                        val thumbnail: String,
                        val frontend: String?,
                        val principal_id: ICPPrincipalApiModel,
                        val submitter: ICPPrincipalApiModel,
                        val last_updated_by: ICPPrincipalApiModel,
                        val last_updated_at: ULong,
                        val details: Array<Details>
                    ) {
                        class Details(
                            val textValue: String,
                            val detail_value: detail_value
                        )
                    }
                """.trimIndent()
            ),

            Arguments.of(
                "type Account = record { owner : principal; subaccount : opt Subaccount };",
                """
                    class Account(
                        val owner: ICPPrincipalApiModel,
                        val subaccount: Subaccount?
                    )
                """.trimIndent()
            )
        )

    }

}