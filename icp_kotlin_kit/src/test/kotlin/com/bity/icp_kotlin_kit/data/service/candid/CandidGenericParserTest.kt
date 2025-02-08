package com.bity.icp_kotlin_kit.data.service.candid

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CandidGenericParserTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl()

    @ParameterizedTest(name = "{index}")
    @MethodSource("typealiases")
    fun typealiases(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = candidTypeParserService.parseCandidType(typeDefinition)
        assertTrue(candidTypeDefinition.isTypeAlias)
        val kotlinDefinition = candidTypeDefinition.getTypealiasDefinition()
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
        private fun typealiases() = listOf(
            Arguments.of(
                """
                    type ApprovalResult = vec record {
                        token_id : nat;
                        approval_result : variant { Ok : nat; Err : ApprovalError };
                    };
                """.trimIndent(),
                """
                    typealias ApprovalResult = kotlin.Array<ApprovalResultClass>
                    class ApprovalResultClass(
                        val token_id: BigInteger,
                        val approval_result: approval_result
                    )
                    sealed class approval_result {
                        class Ok(val ok: BigInteger): approval_result()
                        class Err(val err: ApprovalError): approval_result()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                "type AskConfigShared = opt AskFeatureArray;",
                "typealias AskConfigShared = AskFeatureArray?"
            ),
            Arguments.of(
                "type AskFeatureArray = vec AskFeature;",
                "typealias AskFeatureArray = kotlin.Array<AskFeature>"
            ),
            Arguments.of(
                "type Caller = opt principal;",
                "typealias Caller = ICPPrincipalApiModel?"
            ),
            Arguments.of(
                "type AskSubscribeResponse = bool;",
                "typealias AskSubscribeResponse = Boolean"
            ),
            Arguments.of(
                "type BidConfigShared = opt vec BidFeature;",
                "typealias BidConfigShared = kotlin.Array<BidFeature>?"
            ),
            Arguments.of(
                "type Subaccount = blob;",
                "typealias Subaccount = ByteArray"
            ),
        )

    }

}