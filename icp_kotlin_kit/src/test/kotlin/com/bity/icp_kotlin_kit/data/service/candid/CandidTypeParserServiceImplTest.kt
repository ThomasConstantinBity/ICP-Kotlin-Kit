package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeVariant
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CandidTypeParserServiceImplTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl()

    @ParameterizedTest(name = "{index}")
    @MethodSource("typealiases")
    fun typealiases(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = candidTypeParserService.parseCandidType(typeDefinition)
        assertTrue(candidTypeDefinition.isKotlinTypealiasDefinition())
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

    @ParameterizedTest(name = "{index}")
    @MethodSource("candidVariant")
    fun variantDefinition(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = candidTypeParserService
            .parseCandidType(typeDefinition) as? CandidTypeVariant
        assertNotNull(candidTypeDefinition)
        assertFalse(candidTypeDefinition.isKotlinTypealiasDefinition())
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
        )

    }

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
        private fun typealiases() = listOf(
            Arguments.of(
                "type AskConfigShared = opt AskFeatureArray;",
                "typealias AskConfigShared = AskFeatureArray?"
            ),
            Arguments.of(
                "type AskFeatureArray = vec AskFeature;",
                "typealias AskFeatureArray = Array<AskFeature>"
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
                "typealias BidConfigShared = Array<BidFeature>?"
            )
        )

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
            )
        )

        @JvmStatic
        fun origynNFT() = listOf(

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


        )

    }

}