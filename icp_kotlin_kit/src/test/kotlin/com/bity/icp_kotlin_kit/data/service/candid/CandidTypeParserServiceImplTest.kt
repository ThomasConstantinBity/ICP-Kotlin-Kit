package com.bity.icp_kotlin_kit.data.service.candid

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class CandidTypeParserServiceImplTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl()

    @ParameterizedTest(name = "{index}")
    @MethodSource("origynNFT")
    fun origynNFTType(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        TODO()
       /* val candidTypeDefinition = candidTypeParserService.parseCandidType(typeDefinition)
        val kotlinDefinition = candidTypeDefinition.getKotlinDefinition()
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
        )*/
    }

    companion object {

        @JvmStatic
        fun origynNFT() = listOf(

            Arguments.of(
                "type AskConfigShared = opt AskFeatureArray;",
                "typealias AskConfigShared = AskFeatureArray?"
            ),

            Arguments.of(
                "type AskFeatureArray = vec AskFeature;",
                "typealias AskFeatureArray = Array<AskFeature>"
            ),

            Arguments.of(
                "type BearerResult = variant { ok : Account; err : OrigynError };",
                """
                    sealed class BearerResult {
                        data class ok(val account: Account): BearerResult()
                        data class err(val origynError: OrigynError): BearerResult()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                "type Caller = opt principal;",
                "typealias Caller = ICPPrincipalApiModel?"

            ),

            Arguments.of(
                """
                    type canister_status = record {
                        status : variant { stopped; stopping; running };
                        memory_size : nat;
                        cycles : nat;
                        settings : definite_canister_settings;
                        module_hash : opt vec nat8;
                    };
                """.trimIndent(),
                """
                    data class canister_status(
                        val status: Status,
                        val memory_size: BigInteger,
                        val cycles: BigInteger,
                        val settings: definite_canister_settings,
                        val module_hash: Array<UByte>?
                    ) {
                        sealed class Status {
                            object stopped: Status()
                            object stopping: Status()
                            object running: Status()
                        }
                    }
                """.trimIndent()
            ),

            Arguments.of(
                "type AskSubscribeResponse = bool;",
                "typealias AskSubscribeResponse = Boolean"
            ),

            Arguments.of(
                """
                    type ApprovalResult = vec record {
                        token_id : nat;
                        approval_result : variant { Ok : nat; Err : ApprovalError };
                    };
                """.trimIndent(),
                """
                    typealias ApprovalResult = Array<ApprovalResultValue>
                    data class ApprovalResultValue(
                        val token_id: BigInteger,
                        val approval_result: Approval_result
                    ) {
                        sealed class Approval_result {
                            data class Ok(val natValue: BigInteger): Approval_result()
                            data class Err(val approvalError: ApprovalError): Approval_result()
                        }
                    }
                """.trimIndent()
            ),

            Arguments.of(
                "type BidConfigShared = opt vec BidFeature;",
                "typealias BidConfigShared = Array<BidFeature>?"
            )
        )

    }

}