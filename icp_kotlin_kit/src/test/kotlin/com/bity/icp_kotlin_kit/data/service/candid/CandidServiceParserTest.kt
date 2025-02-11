package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CandidServiceParserTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl { {} }

    @Test
    fun origynNFTService() {
        val serviceDeclaration =
            """
                type Nft_Canister = service {
                  // __advance_time : (int) -> (int);
                  // __set_time_mode : (variant { test; standard }) -> (bool);
                  // __supports : () -> (vec record { text; text }) query;
                  // __version : () -> (text) query;
                  // back_up : (nat) -> (
                  //     variant { eof : NFTBackupChunk; data : NFTBackupChunk },
                  //   ) query;
                  // balance : (EXTBalanceRequest) -> (EXTBalanceResult) query;
                  // balanceEXT : (EXTBalanceRequest) -> (EXTBalanceResult) query;
                  // balance_of_batch_nft_origyn : (vec Account) -> (vec BalanceResult) query;
                  // balance_of_nft_origyn : (Account) -> (BalanceResult) query;
                  // balance_of_secure_batch_nft_origyn : (vec Account) -> (vec BalanceResult);
                  // balance_of_secure_nft_origyn : (Account) -> (BalanceResult);
                  // bearer : (EXTTokenIdentifier) -> (EXTBearerResult) query;
                  // bearerEXT : (EXTTokenIdentifier) -> (EXTBearerResult) query;
                  // bearer_batch_nft_origyn : (vec text) -> (vec BearerResult) query;
                  // bearer_batch_secure_nft_origyn : (vec text) -> (vec BearerResult);
                  // bearer_nft_origyn : (text) -> (BearerResult) query;
                  // bearer_secure_nft_origyn : (text) -> (BearerResult);
                  // canister_status : (record { canister_id : canister_id }) -> (canister_status);
                  // chunk_nft_origyn : (ChunkRequest) -> (ChunkResult) query;
                  // chunk_secure_nft_origyn : (ChunkRequest) -> (ChunkResult);
                  // collectCanisterMetrics : () -> () query;
                  // collection_nft_origyn : (opt vec record { text; opt nat; opt nat }) -> (
                  //     CollectionResult,
                  //   ) query;
                  // collection_secure_nft_origyn : (
                  //     opt vec record { text; opt nat; opt nat },
                  //   ) -> (CollectionResult);
                  // collection_update_batch_nft_origyn : (vec ManageCollectionCommand) -> (
                  //     vec OrigynBoolResult,
                  //   );
                  // collection_update_nft_origyn : (ManageCollectionCommand) -> (
                  //     OrigynBoolResult,
                  //   );
                  // cycles : () -> (nat) query;
                  // dip721_balance_of : (principal) -> (nat) query;
                  // dip721_custodians : () -> (vec principal) query;
                  // dip721_is_approved_for_all : (principal, principal) -> (
                  //     DIP721BoolResult,
                  //   ) query;
                  // dip721_logo : () -> (opt text) query;
                  // dip721_metadata : () -> (DIP721Metadata) query;
                  // dip721_name : () -> (opt text) query;
                  // dip721_operator_token_identifiers : (principal) -> (
                  //     DIP721TokensListMetadata,
                  //   ) query;
                  // dip721_operator_token_metadata : (principal) -> (DIP721TokensMetadata) query;
                  // dip721_owner_of : (nat) -> (OwnerOfResponse) query;
                  // dip721_owner_token_identifiers : (principal) -> (
                  //     DIP721TokensListMetadata,
                  //   ) query;
                  // dip721_owner_token_metadata : (principal) -> (DIP721TokensMetadata) query;
                  // dip721_stats : () -> (DIP721Stats) query;
                  // dip721_supported_interfaces : () -> (vec DIP721SupportedInterface) query;
                  // dip721_symbol : () -> (opt text) query;
                  // dip721_token_metadata : (nat) -> (DIP721TokenMetadata) query;
                  // dip721_total_supply : () -> (nat) query;
                  // dip721_total_transactions : () -> (nat) query;
                  // dip721_transfer : (principal, nat) -> (DIP721NatResult);
                  // dip721_transfer_from : (principal, principal, nat) -> (DIP721NatResult);
                  // getCanisterLog : (opt CanisterLogRequest) -> (opt CanisterLogResponse) query;
                  // getCanisterMetrics : (GetMetricsParameters) -> (opt CanisterMetrics) query;
                  // getEXTTokenIdentifier : (text) -> (text) query;
                  // get_access_key : () -> (OrigynTextResult) query;
                  // get_halt : () -> (bool) query;
                  // get_nat_as_token_id_origyn : (nat) -> (text) query;
                  // get_tip : () -> (Tip) query;
                  // get_token_id_as_nat : (text) -> (nat) query;
                  // governance_batch_nft_origyn : (vec GovernanceRequest) -> (
                  //     vec GovernanceResult,
                  //   );
                  // governance_nft_origyn : (GovernanceRequest) -> (GovernanceResult);
                  // history_batch_nft_origyn : (vec record { text; opt nat; opt nat }) -> (
                  //     vec HistoryResult,
                  //   ) query;
                  // history_batch_secure_nft_origyn : (vec record { text; opt nat; opt nat }) -> (
                  //     vec HistoryResult,
                  //   );
                  // istory_nft_origyn : (text, opt nat, opt nat) -> (HistoryResult) query;
                  // history_secure_nft_origyn : (text, opt nat, opt nat) -> (HistoryResult);
                  // http_access_key : () -> (OrigynTextResult);
                  // http_request : (HttpRequest) -> (HTTPResponse) query;
                  // http_request_streaming_callback : (StreamingCallbackToken) -> (
                  //     StreamingCallbackResponse,
                  //   ) query;
                  // icrc3_get_archives : (GetArchivesArgs) -> (GetArchivesResult) query;
                  // icrc3_get_blocks : (vec TransactionRange) -> (GetTransactionsResult) query;
                  // icrc3_get_tip_certificate : () -> (opt DataCertificate) query;
                  // icrc3_supported_block_types : () -> (vec BlockType) query;
                  // icrc7_approve : (ApprovalArgs) -> (ApprovalResult);
                  // icrc7_atomic_batch_transfers : () -> (opt bool) query;
                  // icrc7_balance_of : (vec Account__3) -> (vec nat) query;
                  // icrc7_collection_metadata : () -> (CollectionMetadata) query;
                  // icrc7_default_take_value : () -> (opt nat) query;
                  // icrc7_description : () -> (opt text) query;
                  // icrc7_logo : () -> (opt text) query;
                  // icrc7_max_approvals_per_token_or_collection : () -> (opt nat) query;
                  // icrc7_max_memo_size : () -> (opt nat) query;
                  // icrc7_max_query_batch_size : () -> (opt nat) query;
                  // icrc7_max_revoke_approvals : () -> (opt nat) query;
                  // icrc7_max_take_value : () -> (opt nat) query;
                  // icrc7_max_update_batch_size : () -> (opt nat) query;
                  // icrc7_name : () -> (text) query;
                  // icrc7_owner_of : (vec nat) -> (vec opt Account__3) query;
                  // icrc7_permitted_drift : () -> (opt nat) query;
                  // icrc7_supply_cap : () -> (opt nat) query;
                  // icrc7_supported_standards : () -> (vec SupportedStandard) query;
                  // icrc7_symbol : () -> (text) query;
                  // icrc7_token_metadata : (vec nat) -> (
                  //     vec opt vec record { text; Value },
                  //   ) query;
                  // icrc7_tokens : (opt nat, opt nat32) -> (vec nat) query;
                  // icrc7_tokens_of : (Account__3, opt nat, opt nat32) -> (vec nat) query;
                  // icrc7_total_supply : () -> (nat) query;
                  // icrc7_transfer : (vec TransferArgs) -> (TransferResult);
                  // icrc7_transfer_fee : (nat) -> (opt nat) query;
                  // icrc7_tx_window : () -> (opt nat) query;
                  // manage_storage_nft_origyn : (ManageStorageRequest) -> (ManageStorageResult);
                  // market_transfer_batch_nft_origyn : (vec MarketTransferRequest) -> (
                  //     vec MarketTransferResult,
                  //   );
                  // market_transfer_nft_origyn : (MarketTransferRequest) -> (
                  //     MarketTransferResult,
                  //   );
                  // metadata : () -> (DIP721Metadata) query;
                  // metadataExt : (EXTTokenIdentifier) -> (EXTMetadataResult) query;
                  // mint_batch_nft_origyn : (vec record { text; Account }) -> (
                  //     vec OrigynTextResult,
                  //   );
                  // mint_nft_origyn : (text, Account) -> (OrigynTextResult);
                  // nftStreamingCallback : (StreamingCallbackToken) -> (
                  //     StreamingCallbackResponse,
                  //   ) query;
                  // nft_batch_origyn : (vec text) -> (vec NFTInfoResult) query;
                  // nft_batch_secure_origyn : (vec text) -> (vec NFTInfoResult);
                  // nft_origyn : (text) -> (NFTInfoResult) query;
                  // nft_secure_origyn : (text) -> (NFTInfoResult);
                  // operaterTokenMetadata : (principal) -> (DIP721TokensMetadata) query;
                  // ownerOf : (nat) -> (OwnerOfResponse) query;
                  // ownerTokenMetadata : (principal) -> (DIP721TokensMetadata) query;
                  // sale_batch_nft_origyn : (vec ManageSaleRequest) -> (vec ManageSaleResult);
                  // sale_info_batch_nft_origyn : (vec SaleInfoRequest) -> (
                  //     vec SaleInfoResult,
                  //   ) query;
                  // sale_info_batch_secure_nft_origyn : (vec SaleInfoRequest) -> (
                  //     vec SaleInfoResult,
                  //   );
                  // sale_info_nft_origyn : (SaleInfoRequest) -> (SaleInfoResult) query;
                  // sale_info_secure_nft_origyn : (SaleInfoRequest) -> (SaleInfoResult);
                  // sale_nft_origyn : (ManageSaleRequest) -> (ManageSaleResult);
                  // set_data_harvester : (nat) -> ();
                  // set_halt : (bool) -> ();
                  // share_wallet_nft_origyn : (ShareWalletRequest) -> (OwnerUpdateResult);
                  // stage_batch_nft_origyn : (vec record { metadata : CandyShared }) -> (
                  //     vec OrigynTextResult,
                  //   );
                  // stage_library_batch_nft_origyn : (vec StageChunkArg) -> (
                  //     vec StageLibraryResult,
                  //   );
                  // stage_library_nft_origyn : (StageChunkArg) -> (StageLibraryResult);
                  // stage_nft_origyn : (record { metadata : CandyShared }) -> (OrigynTextResult);
                  // state_size : () -> (StateSize) query;
                  // storage_info_nft_origyn : () -> (StorageMetricsResult) query;
                  // storage_info_secure_nft_origyn : () -> (StorageMetricsResult);
                  // tokens_ext : (text) -> (EXTTokensResult) query;
                  // transfer : (EXTTransferRequest) -> (EXTTransferResponse);
                  // transferDip721 : (principal, nat) -> (DIP721NatResult);
                  // transferEXT : (EXTTransferRequest) -> (EXTTransferResponse);
                  // transferFrom : (principal, principal, nat) -> (DIP721NatResult);
                  // transferFromDip721 : (principal, principal, nat) -> (DIP721NatResult);
                  // update_app_nft_origyn : (NFTUpdateRequest) -> (NFTUpdateResult);
                  // pdate_icrc3 : (vec UpdateSetting) -> (vec bool);
                  // wallet_receive : () -> (nat);
                  whoami : () -> (principal) query;
                };
            """.trimIndent()
        val candidTypeService = candidTypeParserService.parseCandidType(serviceDeclaration) as? CandidTypeService
        assertNotNull(candidTypeService)
        println(candidTypeService.getServiceDefinition())
    }

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