package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CandidServiceParserTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl()

    @ParameterizedTest(name = "{index}")
    @MethodSource("candidService")
    fun serviceDefinition(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = candidTypeParserService
            .parseCandidType(typeDefinition) as? CandidTypeService
        assertNotNull(candidTypeDefinition)
        assertFalse(candidTypeDefinition.isTypeAlias)
        val kotlinDefinition = candidTypeDefinition.getServiceDefinition()
        assertEquals(
            expected = expectedGeneratedClass
                .replace("""\s+|\t+""".toRegex(), " ")
                .replace("""\n""".toRegex(), " ")
                .trim(),
            actual = kotlinDefinition
                .replace("""\s+|\t+""".toRegex(), " ")
                .replace("""\n""".toRegex(), " ")
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
        private fun candidService() = listOf(
            Arguments.of(
                """
                    service : {
                        "name"   : () -> (text) query;
                        "get"    : (token_id: principal) -> (opt token) query;
                        "add"    : (trusted_source: opt principal, token: add_token_input) -> (operation_response);
                    }
                """.trimIndent(),
                """
                    class Service(
                        private val canister: ICPPrincipal
                    ) {
                        suspend fun name(): String {
                            val icpQuery = ICPQuery(
                                methodName = "name",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf()
                            ).getOrThrow()
                            return CandidDecoder.decodeNotNull(result.first())
                        }
                        
                        suspend fun get(
                            token_id: ICPPrincipalApiModel
                        ): token? {
                            val icpQuery = ICPQuery(
                                methodName = "get",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf(token_id)
                            ).getOrThrow()
                            return CandidDecoder.decode(result.first())
                        }
                            
                        suspend fun add(
                            trusted_source: ICPPrincipalApiModel?,
                            token: add_token_input,
                            sender: ICPSigningPrincipal,
                            pollingValues: PollingValues = PollingValues()
                        ): operation_response {
                            val icpQuery = ICPQuery(
                                    methodName = "add",
                                    canister = canister
                                )
                                val result = icpQuery.callAndPoll(
                                    values = listOf(trusted_source, token),
                                    sender = sender,
                                    pollingValues = pollingValues
                                ).getOrThrow()
                                return CandidDecoder.decodeNotNull(result.first())
                            }
                            
                    }
                """.trimIndent()
            )
        )
    }

}