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
                .trim()
        )
    }

    companion object {


        @JvmStatic
        private fun candidService() = listOf(

            Arguments.of(
                """
                    service : {
                        icrc7_collection_metadata : () -> (vec record { text; Value } ) query;
                        icrc7_symbol : () -> (text) query;
                        icrc7_name : () -> (text) query;
                        icrc7_description : () -> (opt text) query;
                        icrc7_logo : () -> (opt text) query;
                        icrc7_total_supply : () -> (nat) query;
                        icrc7_supply_cap : () -> (opt nat) query;
                        icrc7_max_query_batch_size : () -> (opt nat) query;
                        icrc7_max_update_batch_size : () -> (opt nat) query;
                        icrc7_default_take_value : () -> (opt nat) query;
                        icrc7_max_take_value : () -> (opt nat) query;
                        icrc7_max_memo_size : () -> (opt nat) query;
                        icrc7_atomic_batch_transfers : () -> (opt bool) query;
                        icrc7_tx_window : () -> (opt nat) query;
                        icrc7_permitted_drift : () -> (opt nat) query;
                        icrc7_token_metadata : (token_ids : vec nat) -> (vec record { nat; opt record { text; Value } }) query;
                        icrc7_owner_of : (token_ids : vec nat)
                        -> (vec opt Account) query;
                        icrc7_balance_of : (vec Account) -> (vec nat) query;
                        icrc7_tokens : (prev : opt nat, take : opt nat)
                        -> (vec nat) query;
                        icrc7_tokens_of : (account : Account, prev : opt nat, take : opt nat)
                        -> (vec nat) query;
                        icrc7_transfer : (vec TransferArg) -> (vec opt TransferResult);
                    }
                """.trimIndent(),
                """
                    class Service(
                        private val canister: ICPPrincipal
                    ) {
                        suspend fun icrc7_symbol(): String {
                            val icpQuery = ICPQuery(
                                methodName = "icrc7_symbol",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf()
                            ).getOrThrow()
                            return CandidDecoder.decodeNotNull(result.first())
                        }
                        
                        suspend fun icrc7_name(): String {
                            val icpQuery = ICPQuery(
                                methodName = "icrc7_name",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf()
                            ).getOrThrow()
                            return CandidDecoder.decodeNotNull(result.first())
                        }
                        
                        suspend fun icrc7_description(): String? {
                            val icpQuery = ICPQuery(
                                methodName = "icrc7_description",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf()
                            ).getOrThrow()
                            return CandidDecoder.decode(result.first())
                        }
                        
                        suspend fun icrc7_logo(): String? {
                            val icpQuery = ICPQuery(
                                methodName = "icrc7_logo",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf()
                            ).getOrThrow()
                            return CandidDecoder.decode(result.first())
                        }
                        
                        suspend fun icrc7_total_supply(): BigInteger {
                            val icpQuery = ICPQuery(
                                methodName = "icrc7_total_supply",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf()
                            ).getOrThrow()
                            return CandidDecoder.decodeNotNull(result.first())
                        }
                        
                        suspend fun icrc7_supply_cap(): BigInteger? {
                            val icpQuery = ICPQuery(
                                methodName = "icrc7_supply_cap",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf()
                            ).getOrThrow()
                            return CandidDecoder.decode(result.first())
                        }
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    service : {
                        // DRS Methods
                        "name"   : () -> (text) query;
                        "get"    : (token_id: principal) -> (opt token) query;
                        "add"    : (trusted_source: opt principal, token: add_token_input) -> (operation_response);
                        "remove" : (trusted_source: opt principal, token_id: principal) -> (operation_response);
                    
                        // Canister methods
                        "get_all"  : () -> (vec token) query;
                        "add_admin" : (admin: principal) -> (operation_response);
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
                            
                        suspend fun remove(
                            trusted_source: ICPPrincipalApiModel?,
                            token_id: ICPPrincipalApiModel,
                            sender: ICPSigningPrincipal,
                            pollingValues: PollingValues = PollingValues()
                        ): operation_response {
                            val icpQuery = ICPQuery(
                                    methodName = "remove",
                                    canister = canister
                                )
                                val result = icpQuery.callAndPoll(
                                    values = listOf(trusted_source, token_id),
                                    sender = sender,
                                    pollingValues = pollingValues
                                ).getOrThrow()
                                return CandidDecoder.decodeNotNull(result.first())
                            }
                            
                        suspend fun get_all(): Array<token> {
                            val icpQuery = ICPQuery(
                                methodName = "get_all",
                                canister = canister
                            )
                            val result = icpQuery.invoke(
                                values = listOf()
                            ).getOrThrow()
                            return CandidDecoder.decodeNotNull(result.first())
                        }
                        
                        suspend fun add_admin(
                            admin: ICPPrincipalApiModel,
                            sender: ICPSigningPrincipal,
                            pollingValues: PollingValues = PollingValues()
                        ): operation_response {
                            val icpQuery = ICPQuery(
                                    methodName = "add_admin",
                                    canister = canister
                                )
                                val result = icpQuery.callAndPoll(
                                    values = listOf(admin),
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