package com.bity.icp_kotlin_kit.file_parser.candid_parser

import org.junit.jupiter.api.Test

class CandidTypeParserTest {

    @Test
    fun `AskConfigShared from OrigynNFT file`() {
        val input = "type AskConfigShared = opt AskFeatureArray;"
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `AskFeatureArray from OrigynNFT file`() {
        val input = "type AskFeatureArray = vec AskFeature;"
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `BearerResult from OrigynNFT file`() {
        val input = "type BearerResult = variant { ok : Account; err : OrigynError };"
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `Caller from OrigynNFT file`() {
        val input = "type Caller = opt principal;"
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `canister_status from OrigynNFT file`() {
        val input = """
            type canister_status = record {
                status : variant { stopped; stopping; running };
                memory_size : nat;
                cycles : nat;
                settings : definite_canister_settings;
                module_hash : opt vec nat8;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `AskFeature from OrigynNFT file`() {
        val input = """
            type AskFeature = variant {
              kyc : principal;
              start_price : nat;
              token : TokenSpec;
              fee_schema : text;
              notify : vec principal;
              wait_for_quiet : WaitForQuietType;
              reserve : nat;
              start_date : int;
              min_increase : MinIncreaseType;
              allow_list : vec principal;
              buy_now : nat;
              fee_accounts : FeeAccountsParams;
              nifty_settlement : NiftySettlementType;
              atomic;
              dutch : DutchParams;
              ending : EndingType;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `AskSubscribeResponse from OrigynNFT file`() {
        val input = "type AskSubscribeResponse = bool;"
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `ApprovalResult from OrigynNFT file`() {
        val input = """
            type ApprovalResult = vec record {
              token_id : nat;
              approval_result : variant { Ok : nat; Err : ApprovalError };
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `BidConfigShared from OrigynNFT file`() {
        val input = "type BidConfigShared = opt vec BidFeature;"
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `AskSubscribeRequest from OrigynNFT file`() {
        val input = """
            type AskSubscribeRequest = variant {
                subscribe : r
                ecord {
                    stake : record { principal; nat };
                    filter : opt record {
                        tokens : opt vec TokenSpecFilter;
                        token_ids : opt vec TokenIDFilter;
                        };
                    };
                unsubscribe : record { principal; nat };
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    /**
     * Error for type AuctionConfig = record {
     *   start_price : nat;
     *   token : TokenSpec;
     *   reserve : opt nat;
     *   start_date : int;
     *   min_increase : MinIncreaseType;
     *   allow_list : opt vec principal;
     *   buy_now : opt nat;
     *   ending : variant {
     *     date : int;
     *     wait_for_quiet : record {
     *       max : nat;
     *       date : int;
     *       fade : float64;
     *       extension : nat64;
     *     };
     *   };
     * };
     * Error for type AuctionStateShared = record {
     *   status : variant { closed; open; not_started };
     *   participants : vec record { principal; int };
     *   token : TokenSpec__1;
     *   current_bid_amount : nat;
     *   winner : opt Account;
     *   end_date : int;
     *   current_config : BidConfigShared;
     *   start_date : int;
     *   wait_for_quiet_count : opt nat;
     *   current_escrow : opt EscrowReceipt;
     *   allow_list : opt vec record { principal; bool };
     *   min_next_bid : nat;
     *   config : PricingConfigShared__1;
     * };
     * Error for type BidResponse = record {
     *   token_id : text;
     *   txn_type : variant {
     *     escrow_deposit : record {
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     fee_deposit : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     canister_network_updated : record {
     *       network : principal;
     *       extensible : CandyShared;
     *     };
     *     escrow_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     canister_managers_updated : record {
     *       managers : vec principal;
     *       extensible : CandyShared;
     *     };
     *     auction_bid : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : text;
     *     };
     *     burn : record { from : opt Account__1; extensible : CandyShared };
     *     data : record {
     *       hash : opt vec nat8;
     *       extensible : CandyShared;
     *       data_dapp : opt text;
     *       data_path : opt text;
     *     };
     *     sale_ended : record {
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : opt text;
     *     };
     *     mint : record {
     *       to : Account__1;
     *       from : Account__1;
     *       sale : opt record { token : TokenSpec; amount : nat };
     *       extensible : CandyShared;
     *     };
     *     royalty_paid : record {
     *       tag : text;
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       receiver : Account__1;
     *       sale_id : opt text;
     *     };
     *     extensible : CandyShared;
     *     fee_deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     owner_transfer : record {
     *       to : Account__1;
     *       from : Account__1;
     *       extensible : CandyShared;
     *     };
     *     sale_opened : record {
     *       pricing : PricingConfigShared;
     *       extensible : CandyShared;
     *       sale_id : text;
     *     };
     *     canister_owner_updated : record {
     *       owner : principal;
     *       extensible : CandyShared;
     *     };
     *     sale_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *   };
     *   timestamp : int;
     *   index : nat;
     * };
     * Error for type CandyShared = variant {
     *   Int : int;
     *   Map : vec record { CandyShared; CandyShared };
     *   Nat : nat;
     *   Set : vec CandyShared;
     *   Nat16 : nat16;
     *   Nat32 : nat32;
     *   Nat64 : nat64;
     *   Blob : vec nat8;
     *   Bool : bool;
     *   Int8 : int8;
     *   Ints : vec int;
     *   Nat8 : nat8;
     *   Nats : vec nat;
     *   Text : text;
     *   Bytes : vec nat8;
     *   Int16 : int16;
     *   Int32 : int32;
     *   Int64 : int64;
     *   Option : opt CandyShared;
     *   Floats : vec float64;
     *   Float : float64;
     *   Principal : principal;
     *   Array : vec CandyShared;
     *   Class : vec PropertyShared;
     * };
     * Error for type CanisterCyclesAggregatedData = vec nat64;
     * Error for type CanisterHeapMemoryAggregatedData = vec nat64;
     * Error for type CanisterLogMessagesInfo = record {
     *   features : vec opt CanisterLogFeature;
     *   lastTimeNanos : opt Nanos;
     *   count : nat32;
     *   firstTimeNanos : opt Nanos;
     * };
     * Error for type CanisterMemoryAggregatedData = vec nat64;
     * Error for type ChunkResult = variant { ok : ChunkContent; err : OrigynError };
     * Error for type CollectionMetadata = vec record { text; Value };
     * Error for type CollectionResult = variant { ok : CollectionInfo; err : OrigynError };
     * Error for type DIP721BoolResult = variant { Ok : bool; Err : NftError };
     * Error for type DIP721NatResult = variant { Ok : nat; Err : NftError };
     * Error for type DIP721TokenMetadata = variant { Ok : TokenMetadata; Err : NftError };
     * Error for type DIP721TokensListMetadata = variant { Ok : vec nat; Err : NftError };
     * Error for type DIP721TokensMetadata = variant { Ok : vec TokenMetadata; Err : NftError };
     * Error for type Data = variant {
     *   Int : int;
     *   Map : vec record { CandyShared; CandyShared };
     *   Nat : nat;
     *   Set : vec CandyShared;
     *   Nat16 : nat16;
     *   Nat32 : nat32;
     *   Nat64 : nat64;
     *   Blob : vec nat8;
     *   Bool : bool;
     *   Int8 : int8;
     *   Ints : vec int;
     *   Nat8 : nat8;
     *   Nats : vec nat;
     *   Text : text;
     *   Bytes : vec nat8;
     *   Int16 : int16;
     *   Int32 : int32;
     *   Int64 : int64;
     *   Option : opt CandyShared;
     *   Floats : vec float64;
     *   Float : float64;
     *   Principal : principal;
     *   Array : vec CandyShared;
     *   Class : vec PropertyShared;
     * };
     * Error for type DistributeSaleResponse = vec Result;
     * Error for type DutchParams = record {
     *   time_unit : variant { day : nat; hour : nat; minute : nat };
     *   decay_type : variant { flat : nat; percent : float64 };
     * };
     * Error for type EXTBalanceResult = variant { ok : EXTBalance; err : EXTCommonError };
     * Error for type EXTMemo = vec nat8;
     * Error for type EXTMetadataResult = variant { ok : EXTMetadata; err : EXTCommonError };
     * Error for type EXTSubAccount = vec nat8;
     * Error for type EXTTokensResponse = record {
     *   nat32;
     *   opt record { locked : opt int; seller : principal; price : nat64 };
     *   opt vec nat8;
     * };
     * Error for type EXTTransferResponse = variant {
     *   ok : EXTBalance;
     *   err : variant {
     *     CannotNotify : EXTAccountIdentifier;
     *     InsufficientBalance;
     *     InvalidToken : EXTTokenIdentifier;
     *     Rejected;
     *     Unauthorized : EXTAccountIdentifier;
     *     Other : text;
     *   };
     * };
     * Error for type EXTUser = variant { "principal" : principal; address : text };
     * Error for type EndSaleResponse = record {
     *   token_id : text;
     *   txn_type : variant {
     *     escrow_deposit : record {
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     fee_deposit : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     canister_network_updated : record {
     *       network : principal;
     *       extensible : CandyShared;
     *     };
     *     escrow_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     canister_managers_updated : record {
     *       managers : vec principal;
     *       extensible : CandyShared;
     *     };
     *     auction_bid : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : text;
     *     };
     *     burn : record { from : opt Account__1; extensible : CandyShared };
     *     data : record {
     *       hash : opt vec nat8;
     *       extensible : CandyShared;
     *       data_dapp : opt text;
     *       data_path : opt text;
     *     };
     *     sale_ended : record {
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : opt text;
     *     };
     *     mint : record {
     *       to : Account__1;
     *       from : Account__1;
     *       sale : opt record { token : TokenSpec; amount : nat };
     *       extensible : CandyShared;
     *     };
     *     royalty_paid : record {
     *       tag : text;
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       receiver : Account__1;
     *       sale_id : opt text;
     *     };
     *     extensible : CandyShared;
     *     fee_deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     owner_transfer : record {
     *       to : Account__1;
     *       from : Account__1;
     *       extensible : CandyShared;
     *     };
     *     sale_opened : record {
     *       pricing : PricingConfigShared;
     *       extensible : CandyShared;
     *       sale_id : text;
     *     };
     *     canister_owner_updated : record {
     *       owner : principal;
     *       extensible : CandyShared;
     *     };
     *     sale_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *   };
     *   timestamp : int;
     *   index : nat;
     * };
     * Error for type EndingType = variant { date : int; timeout : nat };
     * Error for type FeeAccountsParams = vec FeeName;
     * Error for type FeeDepositWithdrawDescription = record {
     *   status : variant { locked : record { sale_id : text }; unlocked };
     *   token : TokenSpec__1;
     *   withdraw_to : Account;
     *   account : Account;
     *   amount : nat;
     * };
     * Error for type GenericValue = variant {
     *   Nat64Content : nat64;
     *   Nat32Content : nat32;
     *   BoolContent : bool;
     *   Nat8Content : nat8;
     *   Int64Content : int64;
     *   IntContent : int;
     *   NatContent : nat;
     *   Nat16Content : nat16;
     *   Int32Content : int32;
     *   Int8Content : int8;
     *   FloatContent : float64;
     *   Int16Content : int16;
     *   BlobContent : vec nat8;
     *   NestedContent : Vec;
     *   Principal : principal;
     *   TextContent : text;
     * };
     * Error for type GetArchivesResult = vec GetArchivesResultItem;
     * Error for type GetLatestLogMessagesParameters = record {
     *   upToTimeNanos : opt Nanos;
     *   count : nat32;
     *   filter : opt GetLogMessagesFilter;
     * };
     * Error for type GetLogMessagesFilter = record {
     *   analyzeCount : nat32;
     *   messageRegex : opt text;
     *   messageContains : opt text;
     * };
     * Error for type GetLogMessagesParameters = record {
     *   count : nat32;
     *   filter : opt GetLogMessagesFilter;
     *   fromTimeNanos : opt Nanos;
     * };
     * Error for type GetTransactionsFn = func (vec TransactionRange__1) -> (
     *     GetTransactionsResult__1,
     *   ) query;
     * Error for type GovernanceResult = variant { ok : GovernanceResponse; err : OrigynError };
     * Error for type HTTPResponse = record {
     *   body : vec nat8;
     *   headers : vec HeaderField;
     *   streaming_strategy : opt StreamingStrategy;
     *   status_code : nat16;
     * };
     * Error for type HistoryResult = variant { ok : vec TransactionRecord; err : OrigynError };
     * Error for type ICTokenSpec = record {
     *   id : opt nat;
     *   fee : opt nat;
     *   decimals : nat;
     *   canister : principal;
     *   standard : variant { ICRC1; EXTFungible; DIP20; Other : CandyShared; Ledger };
     *   symbol : text;
     * };
     * Error for type ICTokenSpec__1 = record {
     *   id : opt nat;
     *   fee : opt nat;
     *   decimals : nat;
     *   canister : principal;
     *   standard : variant { ICRC1; EXTFungible; DIP20; Other : CandyShared; Ledger };
     *   symbol : text;
     * };
     * Error for type IndexType = variant { Stable; StableTyped; Managed };
     * Error for type InstantConfigShared = opt vec InstantFeature;
     * Error for type ManageSaleResult = variant { ok : ManageSaleResponse; err : OrigynError };
     * Error for type ManageStorageRequest = variant {
     *   add_storage_canisters : vec record {
     *     principal;
     *     nat;
     *     record { nat; nat; nat };
     *   };
     *   configure_storage : variant { stableBtree : opt nat; heap : opt nat };
     * };
     * Error for type MarketTransferRequestReponse = record {
     *   token_id : text;
     *   txn_type : variant {
     *     escrow_deposit : record {
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     fee_deposit : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     canister_network_updated : record {
     *       network : principal;
     *       extensible : CandyShared;
     *     };
     *     escrow_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     canister_managers_updated : record {
     *       managers : vec principal;
     *       extensible : CandyShared;
     *     };
     *     auction_bid : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : text;
     *     };
     *     burn : record { from : opt Account__1; extensible : CandyShared };
     *     data : record {
     *       hash : opt vec nat8;
     *       extensible : CandyShared;
     *       data_dapp : opt text;
     *       data_path : opt text;
     *     };
     *     sale_ended : record {
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : opt text;
     *     };
     *     mint : record {
     *       to : Account__1;
     *       from : Account__1;
     *       sale : opt record { token : TokenSpec; amount : nat };
     *       extensible : CandyShared;
     *     };
     *     royalty_paid : record {
     *       tag : text;
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       receiver : Account__1;
     *       sale_id : opt text;
     *     };
     *     extensible : CandyShared;
     *     fee_deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     owner_transfer : record {
     *       to : Account__1;
     *       from : Account__1;
     *       extensible : CandyShared;
     *     };
     *     sale_opened : record {
     *       pricing : PricingConfigShared;
     *       extensible : CandyShared;
     *       sale_id : text;
     *     };
     *     canister_owner_updated : record {
     *       owner : principal;
     *       extensible : CandyShared;
     *     };
     *     sale_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *   };
     *   timestamp : int;
     *   index : nat;
     * };
     * Error for type MetricsGranularity = variant { hourly; daily };
     * Error for type MinIncreaseType = variant { amount : nat; percentage : float64 };
     * Error for type NFTInfoResult = variant { ok : NFTInfoStable; err : OrigynError };
     * Error for type NFTUpdateResult = variant { ok : NFTUpdateResponse; err : OrigynError };
     * Error for type Nft_Canister = service {
     *   __advance_time : (int) -> (int);
     *   __set_time_mode : (variant { test; standard }) -> (bool);
     *   __supports : () -> (vec record { text; text }) query;
     *   __version : () -> (text) query;
     *   back_up : (nat) -> (
     *       variant { eof : NFTBackupChunk; data : NFTBackupChunk },
     *     ) query;
     *   balance : (EXTBalanceRequest) -> (EXTBalanceResult) query;
     *   balanceEXT : (EXTBalanceRequest) -> (EXTBalanceResult) query;
     *   balance_of_batch_nft_origyn : (vec Account) -> (vec BalanceResult) query;
     *   balance_of_nft_origyn : (Account) -> (BalanceResult) query;
     *   balance_of_secure_batch_nft_origyn : (vec Account) -> (vec BalanceResult);
     *   balance_of_secure_nft_origyn : (Account) -> (BalanceResult);
     *   bearer : (EXTTokenIdentifier) -> (EXTBearerResult) query;
     *   bearerEXT : (EXTTokenIdentifier) -> (EXTBearerResult) query;
     *   bearer_batch_nft_origyn : (vec text) -> (vec BearerResult) query;
     *   bearer_batch_secure_nft_origyn : (vec text) -> (vec BearerResult);
     *   bearer_nft_origyn : (text) -> (BearerResult) query;
     *   bearer_secure_nft_origyn : (text) -> (BearerResult);
     *   canister_status : (record { canister_id : canister_id }) -> (canister_status);
     *   chunk_nft_origyn : (ChunkRequest) -> (ChunkResult) query;
     *   chunk_secure_nft_origyn : (ChunkRequest) -> (ChunkResult);
     *   collectCanisterMetrics : () -> () query;
     *   collection_nft_origyn : (opt vec record { text; opt nat; opt nat }) -> (
     *       CollectionResult,
     *     ) query;
     *   collection_secure_nft_origyn : (
     *       opt vec record { text; opt nat; opt nat },
     *     ) -> (CollectionResult);
     *   collection_update_batch_nft_origyn : (vec ManageCollectionCommand) -> (
     *       vec OrigynBoolResult,
     *     );
     *   collection_update_nft_origyn : (ManageCollectionCommand) -> (
     *       OrigynBoolResult,
     *     );
     *   cycles : () -> (nat) query;
     *   dip721_balance_of : (principal) -> (nat) query;
     *   dip721_custodians : () -> (vec principal) query;
     *   dip721_is_approved_for_all : (principal, principal) -> (
     *       DIP721BoolResult,
     *     ) query;
     *   dip721_logo : () -> (opt text) query;
     *   dip721_metadata : () -> (DIP721Metadata) query;
     *   dip721_name : () -> (opt text) query;
     *   dip721_operator_token_identifiers : (principal) -> (
     *       DIP721TokensListMetadata,
     *     ) query;
     *   dip721_operator_token_metadata : (principal) -> (DIP721TokensMetadata) query;
     *   dip721_owner_of : (nat) -> (OwnerOfResponse) query;
     *   dip721_owner_token_identifiers : (principal) -> (
     *       DIP721TokensListMetadata,
     *     ) query;
     *   dip721_owner_token_metadata : (principal) -> (DIP721TokensMetadata) query;
     *   dip721_stats : () -> (DIP721Stats) query;
     *   dip721_supported_interfaces : () -> (vec DIP721SupportedInterface) query;
     *   dip721_symbol : () -> (opt text) query;
     *   dip721_token_metadata : (nat) -> (DIP721TokenMetadata) query;
     *   dip721_total_supply : () -> (nat) query;
     *   dip721_total_transactions : () -> (nat) query;
     *   dip721_transfer : (principal, nat) -> (DIP721NatResult);
     *   dip721_transfer_from : (principal, principal, nat) -> (DIP721NatResult);
     *   getCanisterLog : (opt CanisterLogRequest) -> (opt CanisterLogResponse) query;
     *   getCanisterMetrics : (GetMetricsParameters) -> (opt CanisterMetrics) query;
     *   getEXTTokenIdentifier : (text) -> (text) query;
     *   get_access_key : () -> (OrigynTextResult) query;
     *   get_halt : () -> (bool) query;
     *   get_nat_as_token_id_origyn : (nat) -> (text) query;
     *   get_tip : () -> (Tip) query;
     *   get_token_id_as_nat : (text) -> (nat) query;
     *   governance_batch_nft_origyn : (vec GovernanceRequest) -> (
     *       vec GovernanceResult,
     *     );
     *   governance_nft_origyn : (GovernanceRequest) -> (GovernanceResult);
     *   history_batch_nft_origyn : (vec record { text; opt nat; opt nat }) -> (
     *       vec HistoryResult,
     *     ) query;
     *   history_batch_secure_nft_origyn : (vec record { text; opt nat; opt nat }) -> (
     *       vec HistoryResult,
     *     );
     *   history_nft_origyn : (text, opt nat, opt nat) -> (HistoryResult) query;
     *   history_secure_nft_origyn : (text, opt nat, opt nat) -> (HistoryResult);
     *   http_access_key : () -> (OrigynTextResult);
     *   http_request : (HttpRequest) -> (HTTPResponse) query;
     *   http_request_streaming_callback : (StreamingCallbackToken) -> (
     *       StreamingCallbackResponse,
     *     ) query;
     *   icrc3_get_archives : (GetArchivesArgs) -> (GetArchivesResult) query;
     *   icrc3_get_blocks : (vec TransactionRange) -> (GetTransactionsResult) query;
     *   icrc3_get_tip_certificate : () -> (opt DataCertificate) query;
     *   icrc3_supported_block_types : () -> (vec BlockType) query;
     *   icrc7_approve : (ApprovalArgs) -> (ApprovalResult);
     *   icrc7_atomic_batch_transfers : () -> (opt bool) query;
     *   icrc7_balance_of : (vec Account__3) -> (vec nat) query;
     *   icrc7_collection_metadata : () -> (CollectionMetadata) query;
     *   icrc7_default_take_value : () -> (opt nat) query;
     *   icrc7_description : () -> (opt text) query;
     *   icrc7_logo : () -> (opt text) query;
     *   icrc7_max_approvals_per_token_or_collection : () -> (opt nat) query;
     *   icrc7_max_memo_size : () -> (opt nat) query;
     *   icrc7_max_query_batch_size : () -> (opt nat) query;
     *   icrc7_max_revoke_approvals : () -> (opt nat) query;
     *   icrc7_max_take_value : () -> (opt nat) query;
     *   icrc7_max_update_batch_size : () -> (opt nat) query;
     *   icrc7_name : () -> (text) query;
     *   icrc7_owner_of : (vec nat) -> (vec opt Account__3) query;
     *   icrc7_permitted_drift : () -> (opt nat) query;
     *   icrc7_supply_cap : () -> (opt nat) query;
     *   icrc7_supported_standards : () -> (vec SupportedStandard) query;
     *   icrc7_symbol : () -> (text) query;
     *   icrc7_token_metadata : (vec nat) -> (
     *       vec opt vec record { text; Value },
     *     ) query;
     *   icrc7_tokens : (opt nat, opt nat32) -> (vec nat) query;
     *   icrc7_tokens_of : (Account__3, opt nat, opt nat32) -> (vec nat) query;
     *   icrc7_total_supply : () -> (nat) query;
     *   icrc7_transfer : (vec TransferArgs) -> (TransferResult);
     *   icrc7_transfer_fee : (nat) -> (opt nat) query;
     *   icrc7_tx_window : () -> (opt nat) query;
     *   manage_storage_nft_origyn : (ManageStorageRequest) -> (ManageStorageResult);
     *   market_transfer_batch_nft_origyn : (vec MarketTransferRequest) -> (
     *       vec MarketTransferResult,
     *     );
     *   market_transfer_nft_origyn : (MarketTransferRequest) -> (
     *       MarketTransferResult,
     *     );
     *   metadata : () -> (DIP721Metadata) query;
     *   metadataExt : (EXTTokenIdentifier) -> (EXTMetadataResult) query;
     *   mint_batch_nft_origyn : (vec record { text; Account }) -> (
     *       vec OrigynTextResult,
     *     );
     *   mint_nft_origyn : (text, Account) -> (OrigynTextResult);
     *   nftStreamingCallback : (StreamingCallbackToken) -> (
     *       StreamingCallbackResponse,
     *     ) query;
     *   nft_batch_origyn : (vec text) -> (vec NFTInfoResult) query;
     *   nft_batch_secure_origyn : (vec text) -> (vec NFTInfoResult);
     *   nft_origyn : (text) -> (NFTInfoResult) query;
     *   nft_secure_origyn : (text) -> (NFTInfoResult);
     *   operaterTokenMetadata : (principal) -> (DIP721TokensMetadata) query;
     *   ownerOf : (nat) -> (OwnerOfResponse) query;
     *   ownerTokenMetadata : (principal) -> (DIP721TokensMetadata) query;
     *   sale_batch_nft_origyn : (vec ManageSaleRequest) -> (vec ManageSaleResult);
     *   sale_info_batch_nft_origyn : (vec SaleInfoRequest) -> (
     *       vec SaleInfoResult,
     *     ) query;
     *   sale_info_batch_secure_nft_origyn : (vec SaleInfoRequest) -> (
     *       vec SaleInfoResult,
     *     );
     *   sale_info_nft_origyn : (SaleInfoRequest) -> (SaleInfoResult) query;
     *   sale_info_secure_nft_origyn : (SaleInfoRequest) -> (SaleInfoResult);
     *   sale_nft_origyn : (ManageSaleRequest) -> (ManageSaleResult);
     *   set_data_harvester : (nat) -> ();
     *   set_halt : (bool) -> ();
     *   share_wallet_nft_origyn : (ShareWalletRequest) -> (OwnerUpdateResult);
     *   stage_batch_nft_origyn : (vec record { metadata : CandyShared }) -> (
     *       vec OrigynTextResult,
     *     );
     *   stage_library_batch_nft_origyn : (vec StageChunkArg) -> (
     *       vec StageLibraryResult,
     *     );
     *   stage_library_nft_origyn : (StageChunkArg) -> (StageLibraryResult);
     *   stage_nft_origyn : (record { metadata : CandyShared }) -> (OrigynTextResult);
     *   state_size : () -> (StateSize) query;
     *   storage_info_nft_origyn : () -> (StorageMetricsResult) query;
     *   storage_info_secure_nft_origyn : () -> (StorageMetricsResult);
     *   tokens_ext : (text) -> (EXTTokensResult) query;
     *   transfer : (EXTTransferRequest) -> (EXTTransferResponse);
     *   transferDip721 : (principal, nat) -> (DIP721NatResult);
     *   transferEXT : (EXTTransferRequest) -> (EXTTransferResponse);
     *   transferFrom : (principal, principal, nat) -> (DIP721NatResult);
     *   transferFromDip721 : (principal, principal, nat) -> (DIP721NatResult);
     *   update_app_nft_origyn : (NFTUpdateRequest) -> (NFTUpdateResult);
     *   update_icrc3 : (vec UpdateSetting) -> (vec bool);
     *   wallet_receive : () -> (nat);
     *   whoami : () -> (principal) query;
     * };
     * Error for type OrigynBoolResult = variant { ok : bool; err : OrigynError };
     * Error for type OrigynError = record {
     *   "text" : text;
     *   error : Errors;
     *   number : nat32;
     *   flag_point : text;
     * };
     * Error for type OrigynTextResult = variant { ok : text; err : OrigynError };
     * Error for type OwnerOfResponse = variant { Ok : opt principal; Err : NftError };
     * Error for type Result = variant { ok : ManageSaleResponse; err : OrigynError };
     * Error for type SaleInfoRequest = variant {
     *   status : text;
     *   fee_deposit_info : opt Account;
     *   active : opt record { nat; nat };
     *   deposit_info : opt Account;
     *   history : opt record { nat; nat };
     *   escrow_info : EscrowReceipt;
     * };
     * Error for type SaleInfoResult = variant { ok : SaleInfoResponse; err : OrigynError };
     * Error for type SaleStatusShared = record {
     *   token_id : text;
     *   sale_type : variant { auction : AuctionStateShared };
     *   broker_id : opt principal;
     *   original_broker_id : opt principal;
     *   sale_id : text;
     * };
     * Error for type StableEscrowBalances = vec record {
     *   Account;
     *   Account;
     *   text;
     *   EscrowRecord__1;
     * };
     * Error for type StableNftLedger = vec record { text; TransactionRecord };
     * Error for type StableOffers = vec record { Account; Account; int };
     * Error for type StableSalesBalances = vec record {
     *   Account;
     *   Account;
     *   text;
     *   EscrowRecord__1;
     * };
     * Error for type StorageMetricsResult = variant { ok : StorageMetrics; err : OrigynError };
     * Error for type StreamingStrategy = variant {
     *   Callback : record {
     *     token : StreamingCallbackToken;
     *     callback : func () -> ();
     *   };
     * };
     * Error for type Subaccount = vec nat8;
     * Error for type TokenIDFilter = record {
     *   filter_type : variant { allow; block };
     *   token_id : text;
     *   tokens : vec record {
     *     token : TokenSpec__1;
     *     min_amount : opt nat;
     *     max_amount : opt nat;
     *   };
     * };
     * Error for type TokenSpec = variant { ic : ICTokenSpec; extensible : CandyShared };
     * Error for type TokenSpecFilter = record {
     *   token : TokenSpec__1;
     *   filter_type : variant { allow; block };
     * };
     * Error for type TokenSpec__1 = variant { ic : ICTokenSpec__1; extensible : CandyShared };
     * Error for type TokenSpec__2 = variant { ic : ICTokenSpec; extensible : CandyShared };
     * Error for type TransactionRecord = record {
     *   token_id : text;
     *   txn_type : variant {
     *     escrow_deposit : record {
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     fee_deposit : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     canister_network_updated : record {
     *       network : principal;
     *       extensible : CandyShared;
     *     };
     *     escrow_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     canister_managers_updated : record {
     *       managers : vec principal;
     *       extensible : CandyShared;
     *     };
     *     auction_bid : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : text;
     *     };
     *     burn : record { from : opt Account__1; extensible : CandyShared };
     *     data : record {
     *       hash : opt vec nat8;
     *       extensible : CandyShared;
     *       data_dapp : opt text;
     *       data_path : opt text;
     *     };
     *     sale_ended : record {
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : opt text;
     *     };
     *     mint : record {
     *       to : Account__1;
     *       from : Account__1;
     *       sale : opt record { token : TokenSpec; amount : nat };
     *       extensible : CandyShared;
     *     };
     *     royalty_paid : record {
     *       tag : text;
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       receiver : Account__1;
     *       sale_id : opt text;
     *     };
     *     extensible : CandyShared;
     *     fee_deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     owner_transfer : record {
     *       to : Account__1;
     *       from : Account__1;
     *       extensible : CandyShared;
     *     };
     *     sale_opened : record {
     *       pricing : PricingConfigShared;
     *       extensible : CandyShared;
     *       sale_id : text;
     *     };
     *     canister_owner_updated : record {
     *       owner : principal;
     *       extensible : CandyShared;
     *     };
     *     sale_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *   };
     *   timestamp : int;
     *   index : nat;
     * };
     * Error for type TransferResult = vec opt TransferResultItem;
     * Error for type TransferResultItem = record {
     *   token_id : nat;
     *   transfer_result : variant { Ok : nat; Err : TransferError };
     * };
     * Error for type UpdateCallsAggregatedData = vec nat64;
     * Error for type Value = variant {
     *   Int : int;
     *   Map : vec record { text; Value };
     *   Nat : nat;
     *   Blob : vec nat8;
     *   Text : text;
     *   Array : vec Value;
     * };
     * Error for type Value__1 = variant {
     *   Int : int;
     *   Map : vec record { text; Value__1 };
     *   Nat : nat;
     *   Blob : vec nat8;
     *   Text : text;
     *   Array : vec Value__1;
     * };
     * Error for type Vec = vec record {
     *   text;
     *   variant {
     *     Nat64Content : nat64;
     *     Nat32Content : nat32;
     *     BoolContent : bool;
     *     Nat8Content : nat8;
     *     Int64Content : int64;
     *     IntContent : int;
     *     NatContent : nat;
     *     Nat16Content : nat16;
     *     Int32Content : int32;
     *     Int8Content : int8;
     *     FloatContent : float64;
     *     Int16Content : int16;
     *     BlobContent : vec nat8;
     *     NestedContent : Vec;
     *     Principal : principal;
     *     TextContent : text;
     *   };
     * };
     * Error for type WithdrawResponse = record {
     *   token_id : text;
     *   txn_type : variant {
     *     escrow_deposit : record {
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     fee_deposit : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     canister_network_updated : record {
     *       network : principal;
     *       extensible : CandyShared;
     *     };
     *     escrow_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     canister_managers_updated : record {
     *       managers : vec principal;
     *       extensible : CandyShared;
     *     };
     *     auction_bid : record {
     *       token : TokenSpec;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : text;
     *     };
     *     burn : record { from : opt Account__1; extensible : CandyShared };
     *     data : record {
     *       hash : opt vec nat8;
     *       extensible : CandyShared;
     *       data_dapp : opt text;
     *       data_path : opt text;
     *     };
     *     sale_ended : record {
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       sale_id : opt text;
     *     };
     *     mint : record {
     *       to : Account__1;
     *       from : Account__1;
     *       sale : opt record { token : TokenSpec; amount : nat };
     *       extensible : CandyShared;
     *     };
     *     royalty_paid : record {
     *       tag : text;
     *       token : TokenSpec;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *       receiver : Account__1;
     *       sale_id : opt text;
     *     };
     *     extensible : CandyShared;
     *     fee_deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       account : Account__1;
     *       amount : nat;
     *     };
     *     owner_transfer : record {
     *       to : Account__1;
     *       from : Account__1;
     *       extensible : CandyShared;
     *     };
     *     sale_opened : record {
     *       pricing : PricingConfigShared;
     *       extensible : CandyShared;
     *       sale_id : text;
     *     };
     *     canister_owner_updated : record {
     *       owner : principal;
     *       extensible : CandyShared;
     *     };
     *     sale_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       token_id : text;
     *       trx_id : TransactionID;
     *       seller : Account__1;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *     deposit_withdraw : record {
     *       fee : nat;
     *       token : TokenSpec;
     *       trx_id : TransactionID;
     *       extensible : CandyShared;
     *       buyer : Account__1;
     *       amount : nat;
     *     };
     *   };
     *   timestamp : int;
     *   index : nat;
     * };
     */
    @Test
    fun `BalanceResult from OrigynNFT file`() {
        val input = "type BalanceResult = variant { ok : BalanceResponse; err : OrigynError };"
        CandidTypeParser.parseCandidType(input)
    }


}