package com.bity.icp_kotlin_kit.data.service.candid

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CandidGenericParserTest {


    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("typealiases")
    fun typealiases(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = CandidTypeParserService.parseCandidType(typeDefinition)
        assertTrue(candidTypeDefinition.isTypeAlias)
        val kotlinDefinition = candidTypeDefinition.getTypealiasDefinition()
        assertEquals(
            expected = expectedGeneratedClass
                .replace("""\s+|\t+""".toRegex(), " ")
                .trim(),
            actual = kotlinDefinition
                .replace("""\s+|\t+""".toRegex(), " ")
                .trim()
        )
    }

    companion object {

        @JvmStatic
        private fun typealiases() = listOf(

            Arguments.of(
                "type Time__2 = int;",
                "typealias Time__2 = BigInteger"
            ),

            Arguments.of(
                "type TokenIndex__1 = nat32;",
                "typealias TokenIndex__1 = UInt"
            ),

            Arguments.of(
                "type EXTTokenIdentifier = text;",
                "typealias EXTTokenIdentifier = String"
            ),

            Arguments.of(
                "type EXTBalance = nat;",
                "typealias EXTBalance = BigInteger"
            ),

            Arguments.of(
                "type EXTAccountIdentifier = text;",
                "typealias EXTAccountIdentifier = String"
            ),

            Arguments.of(
                "type UpdateCallsAggregatedData = vec nat64;",
                "typealias UpdateCallsAggregatedData = kotlin.Array<ULong>"
            ),

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