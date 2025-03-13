package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeRecord
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CandidRecordParserTest {


    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("candidRecord")
    fun variantDefinition(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = CandidTypeParserService
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
                .trim()
        )
    }

    companion object {

        @JvmStatic
        private fun candidRecord() = listOf(

            Arguments.of(
                """
                    type WithdrawResponse = record {
                      token_id : text;
                      txn_type : variant {
                        escrow_deposit : record {
                          token : TokenSpec;
                          token_id : text;
                          trx_id : TransactionID;
                          seller : Account__1;
                          extensible : CandyShared;
                          buyer : Account__1;
                          amount : nat;
                        };
                        fee_deposit : record {
                          token : TokenSpec;
                          extensible : CandyShared;
                          account : Account__1;
                          amount : nat;
                        };
                        canister_network_updated : record {
                          network : principal;
                          extensible : CandyShared;
                        };
                        escrow_withdraw : record {
                          fee : nat;
                          token : TokenSpec;
                          token_id : text;
                          trx_id : TransactionID;
                          seller : Account__1;
                          extensible : CandyShared;
                          buyer : Account__1;
                          amount : nat;
                        };
                        canister_managers_updated : record {
                          managers : vec principal;
                          extensible : CandyShared;
                        };
                        auction_bid : record {
                          token : TokenSpec;
                          extensible : CandyShared;
                          buyer : Account__1;
                          amount : nat;
                          sale_id : text;
                        };
                        burn : record { from : opt Account__1; extensible : CandyShared };
                        data : record {
                          hash : opt vec nat8;
                          extensible : CandyShared;
                          data_dapp : opt text;
                          data_path : opt text;
                        };
                        sale_ended : record {
                          token : TokenSpec;
                          seller : Account__1;
                          extensible : CandyShared;
                          buyer : Account__1;
                          amount : nat;
                          sale_id : opt text;
                        };
                        royalty_paid : record {
                          tag : text;
                          token : TokenSpec;
                          seller : Account__1;
                          extensible : CandyShared;
                          buyer : Account__1;
                          amount : nat;
                          receiver : Account__1;
                          sale_id : opt text;
                        };
                        extensible : CandyShared;
                        fee_deposit_withdraw : record {
                          fee : nat;
                          token : TokenSpec;
                          trx_id : TransactionID;
                          extensible : CandyShared;
                          account : Account__1;
                          amount : nat;
                        };
                        owner_transfer : record {
                          to : Account__1;
                          from : Account__1;
                          extensible : CandyShared;
                        };
                        sale_opened : record {
                          pricing : PricingConfigShared;
                          extensible : CandyShared;
                          sale_id : text;
                        };
                        canister_owner_updated : record {
                          owner : principal;
                          extensible : CandyShared;
                        };
                        sale_withdraw : record {
                          fee : nat;
                          token : TokenSpec;
                          token_id : text;
                          trx_id : TransactionID;
                          seller : Account__1;
                          extensible : CandyShared;
                          buyer : Account__1;
                          amount : nat;
                        };
                        deposit_withdraw : record {
                          fee : nat;
                          token : TokenSpec;
                          trx_id : TransactionID;
                          extensible : CandyShared;
                          buyer : Account__1;
                          amount : nat;
                        };
                      };
                      timestamp : int;
                      index : nat;
                    };
                """.trimIndent(),
                """
                    class WithdrawResponse(
                        val token_id: String,
                        val txn_type: TxnType,
                        val timestamp: BigInteger,
                        val index: BigInteger
                    ) {
                        sealed class TxnType {
                        
                            class escrow_deposit(
                                val token: TokenSpec,
                                val token_id: String,
                                val trx_id: TransactionID,
                                val seller: Account__1,
                                val extensible: CandyShared,
                                val buyer: Account__1,
                                val amount: BigInteger
                            ): TxnType()
                            
                            class fee_deposit(
                                val token: TokenSpec,
                                val extensible: CandyShared,
                                val account: Account__1,
                                val amount: BigInteger
                            ): TxnType()
                            
                            class canister_network_updated(
                                val network: ICPPrincipalApiModel,
                                val extensible: CandyShared
                            ): TxnType()
                            
                            class escrow_withdraw(
                                val fee: BigInteger,
                                val token: TokenSpec,
                                val token_id: String,
                                val trx_id: TransactionID,
                                val seller: Account__1,
                                val extensible: CandyShared,
                                val buyer: Account__1,
                                val amount: BigInteger
                            ): TxnType()
                            
                            class canister_managers_updated(
                                val managers: kotlin.Array<ICPPrincipalApiModel>,
                                val extensible: CandyShared
                            ): TxnType()
                            
                            class auction_bid(
                                val token: TokenSpec,
                                val extensible: CandyShared,
                                val buyer: Account__1,
                                val amount: BigInteger,
                                val sale_id: String
                            ): TxnType()
                            
                            class burn(
                                val from: Account__1?,
                                val extensible: CandyShared
                            ): TxnType()
                            
                            class data(
                                val hash: kotlin.Array<UByte>?,
                                val extensible: CandyShared,
                                val data_dapp: String?,
                                val data_path: String?
                            ): TxnType()
                            
                            class sale_ended(
                                val token: TokenSpec,
                                val seller: Account__1,
                                val extensible: CandyShared,
                                val buyer: Account__1,
                                val amount: BigInteger,
                                val sale_id: String?
                            ): TxnType()
                            
                            class royalty_paid(
                                val tag: String,
                                val token: TokenSpec,
                                val seller: Account__1,
                                val extensible: CandyShared,
                                val buyer: Account__1,
                                val amount: BigInteger,
                                val receiver: Account__1,
                                val sale_id: String?
                            ): TxnType()
                            
                            class extensible(
                                val extensible: CandyShared
                            ): TxnType()
                            
                            class fee_deposit_withdraw(
                                val fee: BigInteger,
                                val token: TokenSpec,
                                val trx_id: TransactionID,
                                val extensible: CandyShared,
                                val account: Account__1,
                                val amount: BigInteger
                            ): TxnType()
                            
                            class owner_transfer(
                                val to: Account__1,
                                val from: Account__1,
                                val extensible: CandyShared
                            ): TxnType()
                            
                            class sale_opened(
                                val pricing: PricingConfigShared,
                                val extensible: CandyShared,
                                val sale_id: String
                            ): TxnType()
                            
                            class canister_owner_updated(
                                val owner: ICPPrincipalApiModel,
                                val extensible: CandyShared
                            ): TxnType()
                            
                            class sale_withdraw(
                                val fee: BigInteger,
                                val token: TokenSpec,
                                val token_id: String,
                                val trx_id: TransactionID,
                                val seller: Account__1,
                                val extensible: CandyShared,
                                val buyer: Account__1,
                                val amount: BigInteger
                            ): TxnType()
                            
                            class deposit_withdraw(
                                val fee: BigInteger,
                                val token: TokenSpec,
                                val trx_id: TransactionID,
                                val extensible: CandyShared,
                                val buyer: Account__1,
                                val amount: BigInteger
                            ): TxnType()
                        }
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type WithdrawResponse = record {
                      token_id : text;
                      txn_type : variant {
                        extensible : CandyShared;
                      };
                      timestamp : int;
                      index : nat;
                    };
                """.trimIndent(),
                """
                    class WithdrawResponse(
                        val token_id: String,
                        val txn_type: TxnType,
                        val timestamp: BigInteger,
                        val index: BigInteger
                        ) {
                            sealed class TxnType {
                                class extensible(
                                    val extensible: CandyShared
                                ): TxnType() 
                            }
                        }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type WithdrawResponse = record {
                      token_id : text;
                      txn_type : variant {
                        mint : record {
                          to : Account__1;
                          from : Account__1;
                          sale : opt record { token : TokenSpec; amount : nat };
                          extensible : CandyShared;
                        };
                      };
                      timestamp : int;
                      index : nat;
                    };
                """.trimIndent(),
                """
                    
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type AuctionStateShared = record {
                      status : variant { closed; open; not_started };
                      participants : vec record { principal; int };
                      token : TokenSpec__1;
                      current_bid_amount : nat;
                      winner : opt Account;
                      end_date : int;
                      current_config : BidConfigShared;
                      start_date : int;
                      wait_for_quiet_count : opt nat;
                      current_escrow : opt EscrowReceipt;
                      allow_list : opt vec record { principal; bool };
                      min_next_bid : nat;
                      config : PricingConfigShared__1;
                    };
                """.trimIndent(),
                """
                    class AuctionStateShared(
                        val status: Status,
                        val participants: kotlin.Array<Participants>,
                        val token: TokenSpec__1,
                        val current_bid_amount: BigInteger,
                        val winner: Account?,
                        val end_date: BigInteger,
                        val current_config: BidConfigShared,
                        val start_date: BigInteger,
                        val wait_for_quiet_count: BigInteger?,
                        val current_escrow: EscrowReceipt?,
                        val allow_list: kotlin.Array<AllowList>?,
                        val min_next_bid: BigInteger,
                        val config: PricingConfigShared__1
                    ) {
                        sealed class Status {
                            object closed: Status()
                            object open: Status()
                            object not_started: Status()
                        }
                        
                        class Participants(
                            val icpPrincipalApiModel: ICPPrincipalApiModel,
                            val intValue: BigInteger
                        )
                        
                        class AllowList(
                            val icpPrincipalApiModel: ICPPrincipalApiModel,
                            val boolValue: Boolean
                        )
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
                    class canister_status(
                        val status: Status,
                        val memory_size: BigInteger,
                        val cycles: BigInteger,
                        val settings: definite_canister_settings,
                        val module_hash: kotlin.Array<UByte>?
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
                    type EscrowReceipt = record {
                      token : TokenSpec;
                      token_id : text;
                      seller : Account__1;
                      buyer : Account__1;
                      amount : nat;
                    };
                """.trimIndent(),
                """
                    class EscrowReceipt(
                        val token: TokenSpec,
                        val token_id: String,
                        val seller: Account__1,
                        val buyer: Account__1,
                        val amount: BigInteger
                    )
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type DutchParams = record {
                      time_unit : variant { day : nat; hour : nat; minute : nat };
                      decay_type : variant { flat : nat; percent : float64 };
                    };
                """.trimIndent(),
                """
                    class DutchParams(
                        val time_unit: TimeUnit,
                        val decay_type: DecayType
                    ) {
                        sealed class TimeUnit {
                            class day(val day: BigInteger): TimeUnit()
                            class hour(val hour: BigInteger): TimeUnit()
                            class minute(val minute: BigInteger): TimeUnit()
                        }
                        
                        sealed class DecayType {
                            class flat(val flat: BigInteger): DecayType()
                            class percent(val percent: Double): DecayType()
                        }
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type CanisterLogMessages = record {
                      data : vec LogMessagesData;
                      lastAnalyzedMessageTimeNanos : opt Nanos;
                    };
                """.trimIndent(),
                """
                    class CanisterLogMessages(
                        val data: kotlin.Array<LogMessagesData>,
                        val lastAnalyzedMessageTimeNanos: Nanos?
                    )
                """.trimIndent()
            ),

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
            ),

            Arguments.of(
                """
                    type AllocationRecordStable = record {
                      allocated_space : nat;
                      token_id : text;
                      available_space : nat;
                      canister : principal;
                      chunks : vec nat;
                      library_id : text;
                    };
                """.trimIndent(),
                """
                    class AllocationRecordStable(
                        val allocated_space: BigInteger,
                        val token_id: String,
                        val available_space: BigInteger,
                        val canister: ICPPrincipalApiModel,
                        val chunks: Array<BigInteger>,
                        val library_id: String
                    )
                """.trimIndent()
            )
        )

    }

}