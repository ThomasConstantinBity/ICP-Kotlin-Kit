/*
package com.bity.icp_kotlin_kit.file_parser.candid_parser

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class CandidTypeParserTest {



    @Test
    fun `canister_status from OrigynNFT file`() {
        val input = """
            
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
                subscribe : record {
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

    @Test
    fun `AuctionConfig from OrigynNFT file`() {
        val input = """
            type AuctionConfig = record {
             start_price : nat;
             token : TokenSpec;
             reserve : opt nat;
             start_date : int;
             min_increase : MinIncreaseType;
             allow_list : opt vec principal;
             buy_now : opt nat;
             ending : variant {
                date : int;
                wait_for_quiet : record {
                    max : nat;
                    date : int;
                    fade : float64;
                    extension : nat64;
                    };
                };
             };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `AuctionStateShared from OrigynNFT file`() {
        val input = """
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
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `CandyShared from OrigynNFT file`() {
        val input = """
            type CandyShared = variant {
                Int : int;
                Map : vec record { CandyShared; CandyShared };
                Nat : nat;
                Set : vec CandyShared;
                Nat16 : nat16;
                Nat32 : nat32;
                Nat64 : nat64;
                Blob : vec nat8;
                Bool : bool;
                Int8 : int8;
                Ints : vec int;
                Nat8 : nat8;
                Nats : vec nat;
                Text : text;
                Bytes : vec nat8;
                Int16 : int16;
                Int32 : int32;
                Int64 : int64;
                Option : opt CandyShared;
                Floats : vec float64;
                Float : float64;
                Principal : principal;
                Array : vec CandyShared;
                Class : vec PropertyShared;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `Vec from OrigynNFT file`() {
        val input = """
            type Vec = vec record {
                text;
                variant {
                    Nat64Content : nat64;
                    Nat32Content : nat32;
                    BoolContent : bool;
                    Nat8Content : nat8;
                    Int64Content : int64;
                    IntContent : int;
                    NatContent : nat;
                    Nat16Content : nat16;
                    Int32Content : int32;
                    Int8Content : int8;
                    FloatContent : float64;
                    Int16Content : int16;
                    BlobContent : vec nat8;
                    NestedContent : Vec;
                    Principal : principal;
                    TextContent : text;
                };
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `CanisterCyclesAggregatedData from OrigynNFT file`() {
        val input = """
            type CanisterCyclesAggregatedData = vec nat64;
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `CanisterHeapMemoryAggregatedData from OrigynNFT file`() {
        val input = """
            type CanisterHeapMemoryAggregatedData = vec nat64;
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `CanisterLogMessagesInfo from OrigynNFT file`() {
        val input = """
            type CanisterLogMessagesInfo = record {
                features : vec opt CanisterLogFeature;
                lastTimeNanos : opt Nanos;
                count : nat32;
                firstTimeNanos : opt Nanos;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `CanisterMemoryAggregatedData from OrigynNFT file`() {
        val input = """
            type CanisterMemoryAggregatedData = vec nat64;
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `ChunkResult from OrigynNFT file`() {
        val input = """
            type ChunkResult = variant { ok : ChunkContent; err : OrigynError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `CollectionMetadata from OrigynNFT file`() {
        val input = """
            type CollectionMetadata = vec record { text; Value };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `CollectionResult from OrigynNFT file`() {
        val input = """
            type CollectionResult = variant { ok : CollectionInfo; err : OrigynError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `DIP721BoolResult from OrigynNFT file`() {
        val input = """
            type DIP721BoolResult = variant { Ok : bool; Err : NftError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `DIP721NatResult from OrigynNFT file`() {
        val input = """
            type DIP721NatResult = variant { Ok : nat; Err : NftError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `DIP721TokenMetadata from OrigynNFT file`() {
        val input = """
            type DIP721TokenMetadata = variant { Ok : TokenMetadata; Err : NftError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `DIP721TokensListMetadata from OrigynNFT file`() {
        val input = """
            type DIP721TokensListMetadata = variant { Ok : vec nat; Err : NftError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `DIP721TokensMetadata from OrigynNFT file`() {
        val input = """
            type DIP721TokensMetadata = variant { Ok : vec TokenMetadata; Err : NftError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `Data from OrigynNFT file`() {
        val input = """
            type Data = variant {
                Int : int;
                Map : vec record { CandyShared; CandyShared };
                Nat : nat;
                Set : vec CandyShared;
                Nat16 : nat16;
                Nat32 : nat32;
                Nat64 : nat64;
                Blob : vec nat8;
                Bool : bool;
                Int8 : int8;
                Ints : vec int;
                Nat8 : nat8;
                Nats : vec nat;
                Text : text;
                Bytes : vec nat8;
                Int16 : int16;
                Int32 : int32;
                Int64 : int64;
                Option : opt CandyShared;
                Floats : vec float64;
                Float : float64;
                Principal : principal;
                Array : vec CandyShared;
                Class : vec PropertyShared;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `DistributeSaleResponse from OrigynNFT file`() {
        val input = """
            type DistributeSaleResponse = vec Result;
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `DutchParams from OrigynNFT file`() {
        val input = """
            type DutchParams = record {
                time_unit : variant { day : nat; hour : nat; minute : nat };
                decay_type : variant { flat : nat; percent : float64 };
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EXTBalanceResult from OrigynNFT file`() {
        val input = """
            type EXTBalanceResult = variant { ok : EXTBalance; err : EXTCommonError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EXTMemo from OrigynNFT file`() {
        val input = """
            type EXTMemo = vec nat8;
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EXTMetadataResult from OrigynNFT file`() {
        val input = """
            type EXTMetadataResult = variant { ok : EXTMetadata; err : EXTCommonError };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EXTSubAccount from OrigynNFT file`() {
        val input = """
            type EXTSubAccount = vec nat8;
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EXTTokensResponse from OrigynNFT file`() {
        val input = """
            type EXTTokensResponse = record {
                nat32;
                opt record { locked : opt int; seller : principal; price : nat64 };
                opt vec nat8;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EXTTransferResponse from OrigynNFT file`() {
        val input = """
            type EXTTransferResponse = variant {
                ok : EXTBalance;
                err : variant {
                    CannotNotify : EXTAccountIdentifier;
                    InsufficientBalance;
                    InvalidToken : EXTTokenIdentifier;
                    Rejected;
                    Unauthorized : EXTAccountIdentifier;
                    Other : text;
                };
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EXTUser from OrigynNFT file`() {
        val input = """
            type EXTUser = variant { "principal" : principal; address : text };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EndSaleResponse from OrigynNFT file`() {
        val input = """
            type EndSaleResponse = record {
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
                mint : record {
                    to : Account__1;
                    from : Account__1;
                    sale : opt record { token : TokenSpec; amount : nat };
                    extensible : CandyShared;
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
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `EndingType from OrigynNFT file`() {
        val input = """
            type EndingType = variant { date : int; timeout : nat };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `FeeAccountsParams from OrigynNFT file`() {
        val input = """
            type FeeAccountsParams = vec FeeName;
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `FeeDepositWithdrawDescription from OrigynNFT file`() {
        val input = """
            type FeeDepositWithdrawDescription = record {
                status : variant { locked : record { sale_id : text }; unlocked };
                token : TokenSpec__1;
                withdraw_to : Account;
                account : Account;
                amount : nat;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `GenericValue from OrigynNFT file`() {
        val input = """
            type GenericValue = variant {
                Nat64Content : nat64;
                Nat32Content : nat32;
                BoolContent : bool;
                Nat8Content : nat8;
                Int64Content : int64;
                IntContent : int;
                NatContent : nat;
                Nat16Content : nat16;
                Int32Content : int32;
                Int8Content : int8;
                FloatContent : float64;
                Int16Content : int16;
                BlobContent : vec nat8;
                NestedContent : Vec;
                Principal : principal;
                TextContent : text;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `GetArchivesResult from OrigynNFT file`() {
        val input = "type GetArchivesResult = vec GetArchivesResultItem;"
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `GetLatestLogMessagesParameters from OrigynNFT file`() {
        val input = """
            type GetLatestLogMessagesParameters = record {
                upToTimeNanos : opt Nanos;
                count : nat32;
                filter : opt GetLogMessagesFilter;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `GetLogMessagesFilter from OrigynNFT file`() {
        val input = """
            type GetLogMessagesFilter = record {
                analyzeCount : nat32;
                messageRegex : opt text;
                messageContains : opt text;
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `BalanceResult from OrigynNFT file`() {
        val input = "type BalanceResult = variant { ok : BalanceResponse; err : OrigynError };"
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `StreamingStrategy from OrigynNFT file`() {
        val input = """
            type StreamingStrategy = variant {
              Callback : record {
                token : StreamingCallbackToken;
                callback : func () -> ();
              };
            };
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }

    @Test
    fun `GetTransactionsFn from OrigynNFT file`() {
        val input = """
            type GetTransactionsFn = func (vec TransactionRange__1) -> (
                GetTransactionsResult__1,
            ) query;
        """.trimIndent()
        CandidTypeParser.parseCandidType(input)
    }


}*/
