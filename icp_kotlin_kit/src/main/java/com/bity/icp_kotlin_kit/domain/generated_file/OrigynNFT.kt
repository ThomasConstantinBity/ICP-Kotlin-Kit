package com.bity.icp_kotlin_kit.domain.generated_file

import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVariant
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.di.icpCanisterRepository
import com.bity.icp_kotlin_kit.domain.generated_file.OrigynNFT.ApprovalResultClass
import com.bity.icp_kotlin_kit.domain.model.ICPMethod
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.ICPCanisterRepository

/**
 * type Nanos = nat64;
 */
typealias OrigynNanos = ULong

/**
 * type AskConfigShared = opt AskFeatureArray;
 */
typealias AskConfigShared = AskFeatureArray?

/**
 * type AskFeatureArray = vec AskFeature;
 */
typealias AskFeatureArray = kotlin.Array<OrigynNFT.OrigynAskFeature>

/**
 * type AskSubscribeResponse = bool;
 */
typealias AskSubscribeResponse = Boolean

/**
 * type BidConfigShared = opt vec BidFeature;
 */
typealias BidConfigShared = kotlin.Array<OrigynNFT.OrigynBidFeature>?

/**
 * type Caller = opt principal;
 */
typealias Caller = ICPPrincipalApiModel?

/**
 * type CanisterCyclesAggregatedData = vec nat64;
 */
typealias OrigynCanisterCyclesAggregatedData = kotlin.Array<ULong>

/**
 * type CanisterHeapMemoryAggregatedData = vec nat64;
 */
typealias OrigynCanisterHeapMemoryAggregatedData = kotlin.Array<ULong>

/**
 * type CanisterMemoryAggregatedData = vec nat64;
 */
typealias OrigynCanisterMemoryAggregatedData = kotlin.Array<ULong>

/**
 * type CollectionMetadata = vec record { text; Value };
 */
typealias CollectionMetadata = kotlin.Array<CollectionMetadataClass>
class CollectionMetadataClass(
    val textValue: String,
    val value: Value
)
/**
 * type DistributeSaleResponse = vec Result;
 */
typealias DistributeSaleResponse = kotlin.Array<Result>

/**
 * type EXTAccountIdentifier = text;
 */
typealias EXTAccountIdentifier = String

/**
 * type EXTBalance = nat;
 */
typealias EXTBalance = BigInteger

/**
 * type EXTMemo = vec nat8;
 */
typealias EXTMemo = kotlin.Array<UByte>

/**
 * type EXTSubAccount = vec nat8;
 */
typealias OrigynEXTSubAccount = kotlin.Array<UByte>

/**
 * type EXTTokenIdentifier = text;
 */
typealias EXTTokenIdentifier = String

/**
 * type FeeAccountsParams = vec FeeName;
 */
typealias FeeAccountsParams = kotlin.Array<FeeName>

/**
 * type FeeName = text;
 */
typealias FeeName = String

/**
 * type GetArchivesResult = vec GetArchivesResultItem;
 */
typealias GetArchivesResult = kotlin.Array<OrigynNFT.GetArchivesResultItem>

/**
 * type InstantConfigShared = opt vec InstantFeature;
 */
typealias InstantConfigShared = kotlin.Array<OrigynNFT.InstantFeature>?

/**
 * type NFTUpdateResponse = bool;
 */
typealias NFTUpdateResponse = Boolean

/**
 * type StableEscrowBalances = vec record {
 *   Account;
 *   Account;
 *   text;
 *   EscrowRecord__1;
 * };
 */
typealias StableEscrowBalances = kotlin.Array<StableEscrowBalancesClass>
class StableEscrowBalancesClass(
    val account_1: Account,
    val account_2: Account,
    val textValue: String,
    val escrowRecord: OrigynNFT.EscrowRecord__1
)
/**
 * type StableNftLedger = vec record { text; TransactionRecord };
 */
typealias StableNftLedger = kotlin.Array<StableNftLedgerClass>
class StableNftLedgerClass(
    val textValue: String,
    val transactionRecord: OrigynNFT.TransactionRecord
)
/**
 * type StableOffers = vec record { Account; Account; int };
 */
typealias StableOffers = kotlin.Array<StableOffersClass>
class StableOffersClass(
    val account_1: Account,
    val account_2: Account,
    val intValue: BigInteger
)
/**
 * type StableSalesBalances = vec record {
 *   Account;
 *   Account;
 *   text;
 *   EscrowRecord__1;
 * };
 */
typealias StableSalesBalances = kotlin.Array<StableSalesBalancesClass>
class StableSalesBalancesClass(
    val account_1: Account,
    val account_2: Account,
    val textValue: String,
    val escrowRecord: OrigynNFT.EscrowRecord__1
)
/**
 * type Subaccount = vec nat8;
 */
typealias OrigynSubaccount = kotlin.Array<UByte>

/**
 * type TransferResult = vec opt TransferResultItem;
 */
typealias OrigynTransferResult = kotlin.Array<OrigynNFT.TransferResultItem?>

/**
 * type UpdateCallsAggregatedData = vec nat64;
 */
typealias OrigynUpdateCallsAggregatedData = kotlin.Array<ULong>

/**
 * type canister_id = principal;
 */
typealias canister_id = ICPPrincipalApiModel

typealias ApprovalResult = kotlin.Array<ApprovalResultClass>
object OrigynNFT {

    /**
     * type ApprovalResult = vec record {
     *   token_id : nat;
     *   approval_result : variant { Ok : nat; Err : ApprovalError };
     * };
     */
    class ApprovalResultClass(
        val token_id: BigInteger,
        val approval_result: ApprovalResult
    ) {
        sealed class ApprovalResult {
            class Ok(val Ok: BigInteger): ApprovalResult()
            class Err(val Err: ApprovalError): ApprovalResult()
        }
    }

    /**
     * type Account = variant {
     *   account_id : text;
     *   "principal" : principal;
     *   extensible : CandyShared;
     *   account : record { owner : principal; sub_account : opt vec nat8 };
     * };
     */
    sealed class Account {
        class account_id(val account_id: String): Account()
        class principal(val principal: ICPPrincipalApiModel): Account()
        class extensible(val extensible: CandyShared): Account()
        class account(
            val owner: ICPPrincipalApiModel,
            val sub_account: kotlin.Array<UByte>?
        ): Account()
    }
    /**
     * type Account__1 = variant {
     *   account_id : text;
     *   "principal" : principal;
     *   extensible : CandyShared;
     *   account : record { owner : principal; sub_account : opt vec nat8 };
     * };
     */
    sealed class Account__1 {
        class account_id(val account_id: String): Account__1()
        class principal(val principal: ICPPrincipalApiModel): Account__1()
        class extensible(val extensible: CandyShared): Account__1()
        class account(
            val owner: ICPPrincipalApiModel,
            val sub_account: kotlin.Array<UByte>?
        ): Account__1()
    }
    /**
     * type Account__2 = variant {
     *   account_id : text;
     *   "principal" : principal;
     *   extensible : CandyShared;
     *   account : record { owner : principal; sub_account : opt vec nat8 };
     * };
     */
    sealed class Account__2 {
        class account_id(val account_id: String): Account__2()
        class principal(val principal: ICPPrincipalApiModel): Account__2()
        class extensible(val extensible: CandyShared): Account__2()
        class account(
            val owner: ICPPrincipalApiModel,
            val sub_account: kotlin.Array<UByte>?
        ): Account__2()
    }
    /**
     * type Account__3 = record { owner : principal; subaccount : opt Subaccount };
     */
    class Account__3(
        val owner: ICPPrincipalApiModel,
        val subaccount: OrigynSubaccount?
    )
    /**
     * type AllocationRecordStable = record {
     *   allocated_space : nat;
     *   token_id : text;
     *   available_space : nat;
     *   canister : principal;
     *   chunks : vec nat;
     *   library_id : text;
     * };
     */
    class AllocationRecordStable(
        val allocated_space: BigInteger,
        val token_id: String,
        val available_space: BigInteger,
        val canister: ICPPrincipalApiModel,
        val chunks: kotlin.Array<BigInteger>,
        val library_id: String
    )
    /**
     * type ApprovalArgs = record {
     *   memo : opt vec nat8;
     *   from_subaccount : opt vec nat8;
     *   created_at_time : opt nat64;
     *   expires_at : opt nat64;
     *   spender : Account__3;
     * };
     */
    class ApprovalArgs(
        val memo: kotlin.Array<UByte>?,
        val from_subaccount: kotlin.Array<UByte>?,
        val created_at_time: ULong?,
        val expires_at: ULong?,
        val spender: Account__3
    )
    /**
     * type ApprovalError = variant {
     *   GenericError : record { message : text; error_code : nat };
     *   CreatexInFuture : record { ledger_time : nat64 };
     *   NonExistingTokenId;
     *   Unauthorized;
     *   TooOld;
     * };
     */
    sealed class ApprovalError {
        class GenericError(
            val message: String,
            val error_code: BigInteger
        ): ApprovalError()

        class CreatexInFuture(
            val ledger_time: ULong
        ): ApprovalError()

        object NonExistingTokenId: ApprovalError()
        object Unauthorized: ApprovalError()
        object TooOld: ApprovalError()

    }
    /**
     * type ArchivedTransactionResponse = record {
     *   args : vec TransactionRange__1;
     *   callback : GetTransactionsFn;
     * };
     */
    class ArchivedTransactionResponse(
        val args: kotlin.Array<TransactionRange__1>,
        val callback: GetTransactionsFn
    )

    class GetTransactionsFn(
        methodName: String,
        canister: ICPPrincipal
    ) : ICPQuery(
        methodName = methodName,
        canister = canister
    ) {
        suspend operator fun invoke(
            array: kotlin.Array<TransactionRange>,
        ): GetTransactionsResult__1 {
            val result = this.query(
                values = listOf(
                    ValueToEncode(
                        arg = array,
                        expectedClass = Array::class,
                        expectedClassNullable = false,
                        arrayType = TransactionRange::class
                    )
                ),
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }
    }
    /**
     * type AskFeature = variant {
     *   kyc : principal;
     *   start_price : nat;
     *   token : TokenSpec;
     *   fee_schema : text;
     *   notify : vec principal;
     *   wait_for_quiet : WaitForQuietType;
     *   reserve : nat;
     *   start_date : int;
     *   min_increase : MinIncreaseType;
     *   allow_list : vec principal;
     *   buy_now : nat;
     *   fee_accounts : FeeAccountsParams;
     *   nifty_settlement : NiftySettlementType;
     *   atomic;
     *   dutch : DutchParams;
     *   ending : EndingType;
     * };
     */
    sealed class OrigynAskFeature {
        class kyc(val kyc: ICPPrincipalApiModel): OrigynAskFeature()
        class start_price(val start_price: BigInteger): OrigynAskFeature()
        class token(val token: TokenSpec): OrigynAskFeature()
        class fee_schema(val fee_schema: String): OrigynAskFeature()
        class Notify(val notify: kotlin.Array<ICPPrincipalApiModel>): OrigynAskFeature()
        class wait_for_quiet(val wait_for_quiet: WaitForQuietType): OrigynAskFeature()
        class reserve(val reserve: BigInteger): OrigynAskFeature()
        class start_date(val start_date: BigInteger): OrigynAskFeature()
        class min_increase(val min_increase: MinIncreaseType): OrigynAskFeature()
        class Allow_list(val allow_list: kotlin.Array<ICPPrincipalApiModel>): OrigynAskFeature()
        class buy_now(val buy_now: BigInteger): OrigynAskFeature()
        class fee_accounts(val fee_accounts: FeeAccountsParams): OrigynAskFeature()
        class nifty_settlement(val nifty_settlement: NiftySettlementType): OrigynAskFeature()
        object atomic: OrigynAskFeature()
        class dutch(val dutch: DutchParams): OrigynAskFeature()
        class ending(val ending: EndingType): OrigynAskFeature()

    }
    /**
     * type AskSubscribeRequest = variant {
     *   subscribe : record {
     *     stake : record { principal; nat };
     *     filter : opt record {
     *       tokens : opt vec TokenSpecFilter;
     *       token_ids : opt vec TokenIDFilter;
     *     };
     *   };
     *   unsubscribe : record { principal; nat };
     * };
     */
    sealed class AskSubscribeRequest {

        class subscribe(
            val stake: Stake,
            val filter: Filter?
        ): AskSubscribeRequest() {

            class Stake(
                val principal: ICPPrincipalApiModel,
                val natValue: BigInteger
            )
            class Filter(
                val tokens: kotlin.Array<TokenSpecFilter>?,
                val token_ids: kotlin.Array<TokenIDFilter>?
            )

        }

        class unsubscribe(
            val icpPrincipalApiModel: ICPPrincipalApiModel,
            val natValue: BigInteger
        ): AskSubscribeRequest()
    }

    /**
     * type AuctionConfig = record {
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
     */
    class AuctionConfig(
        val start_price: BigInteger,
        val token: TokenSpec,
        val reserve: BigInteger?,
        val start_date: BigInteger,
        val min_increase: MinIncreaseType,
        val allow_list: kotlin.Array<ICPPrincipalApiModel>?,
        val buy_now: BigInteger?,
        val ending: Ending
    ) {
        sealed class Ending {
            class date(val date: BigInteger): Ending()
            class wait_for_quiet(
                val max: BigInteger,
                val date: BigInteger,
                val fade: Double,
                val extension: ULong
            ): Ending()
        }

    }
    /**
     * type AuctionStateShared = record {
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
     */
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
    /**
     * type BalanceResponse = record {
     *   nfts : vec text;
     *   offers : vec EscrowRecord__1;
     *   sales : vec EscrowRecord__1;
     *   stake : vec StakeRecord;
     *   multi_canister : opt vec principal;
     *   escrow : vec EscrowRecord__1;
     * };
     */
    data class BalanceResponse(
        val nfts: kotlin.Array<String>,
        val offers: kotlin.Array<EscrowRecord__1>?,
        val sales: kotlin.Array<EscrowRecord__1>?,
        val stake: kotlin.Array<StakeRecord>?,
        val multi_canister: kotlin.Array<ICPPrincipalApiModel>?,
        val escrow: kotlin.Array<EscrowRecord__1>?
    )
    /**
     * type BalanceResult = variant { ok : BalanceResponse; err : OrigynError };
     */
    sealed class BalanceResult {
        class ok(val ok: BalanceResponse): BalanceResult()
        class err(val err: OrigynError): BalanceResult()

    }
    /**
     * type BearerResult = variant { ok : Account; err : OrigynError };
     */
    sealed class BearerResult {
        class ok(val ok: Account): BearerResult()
        class err(val err: OrigynError): BearerResult()

    }
    /**
     * type BidFeature = variant {
     *   fee_schema : text;
     *   broker : Account__1;
     *   fee_accounts : FeeAccountsParams;
     * };
     */
    sealed class OrigynBidFeature {
        class fee_schema(val fee_schema: String): OrigynBidFeature()
        class broker(val broker: Account__1): OrigynBidFeature()
        class fee_accounts(val fee_accounts: FeeAccountsParams): OrigynBidFeature()

    }
    /**
     * type BidRequest = record {
     *   config : BidConfigShared;
     *   escrow_record : EscrowRecord;
     * };
     */
    class BidRequest(
        val config: BidConfigShared,
        val escrow_record: EscrowRecord
    )
    /**
     * type BidResponse = record {
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
    class BidResponse(
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

            class mint(
                val to: Account__1,
                val from: Account__1,
                val sale: kotlin.Array<Sale>?,
                val extensible: CandyShared
            ): TxnType() {
                class Sale(
                    val token: TokenSpec,
                    val amount: BigInteger
                )
            }

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

            class extensible(val extensible: CandyShared): TxnType()
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
    /**
     * type BlockType = record { url : text; block_type : text };
     */
    class BlockType(
        val url: String,
        val block_type: String
    )
    /**
     * type CandyShared = variant {
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
     */
    sealed class CandyShared {
        class Int(val Int: BigInteger): CandyShared()
        class Map(val Map: kotlin.Array<MapClass>): CandyShared()
        class Nat(val Nat: BigInteger): CandyShared()
        class Set(val Set: kotlin.Array<CandyShared>): CandyShared()
        class Nat16(val Nat16: UShort): CandyShared()
        class Nat32(val Nat32: UInt): CandyShared()
        class Nat64(val Nat64: ULong): CandyShared()
        class Blob(val Blob: kotlin.Array<UByte>): CandyShared()
        class Bool(val Bool: Boolean): CandyShared()
        class Int8(val Int8: Byte): CandyShared()
        class Ints(val Ints: kotlin.Array<BigInteger>): CandyShared()
        class Nat8(val Nat8: UByte): CandyShared()
        class Nats(val Nats: kotlin.Array<BigInteger>): CandyShared()
        class Text(val Text: String): CandyShared()
        class Bytes(val Bytes: kotlin.Array<UByte>): CandyShared()
        class Int16(val Int16: Short): CandyShared()
        class Int32(val Int32: Int): CandyShared()
        class Int64(val Int64: Long): CandyShared()
        class Option(val Option: CandyShared?): CandyShared()
        class Floats(val Floats: kotlin.Array<Float>): CandyShared()
        class Float(val Float: Double): CandyShared()
        class Principal(val Principal: ICPPrincipalApiModel): CandyShared()
        class Array(val Array: kotlin.Array<CandyShared>): CandyShared()
        class Class(val Class: kotlin.Array<PropertyShared>): CandyShared()

        class MapClass(
            val key: CandyShared,
            val value: CandyShared
        )

    }
    /**
     * type CanisterLogFeature = variant {
     *   filterMessageByContains;
     *   filterMessageByRegex;
     * };
     */
    sealed class CanisterLogFeature {
        object filterMessageByContains: CanisterLogFeature()
        object filterMessageByRegex: CanisterLogFeature()

    }
    /**
     * type CanisterLogMessages = record {
     *   data : vec LogMessagesData;
     *   lastAnalyzedMessageTimeNanos : opt Nanos;
     * };
     */
    class CanisterLogMessages(
        val data: kotlin.Array<LogMessagesData>,
        val lastAnalyzedMessageTimeNanos: OrigynNanos?
    )
    /**
     * type CanisterLogMessagesInfo = record {
     *   features : vec opt CanisterLogFeature;
     *   lastTimeNanos : opt Nanos;
     *   count : nat32;
     *   firstTimeNanos : opt Nanos;
     * };
     */
    class CanisterLogMessagesInfo(
        val features: kotlin.Array<CanisterLogFeature?>,
        val lastTimeNanos: OrigynNanos?,
        val count: UInt,
        val firstTimeNanos: OrigynNanos?
    )
    /**
     * type CanisterLogRequest = variant {
     *   getMessagesInfo;
     *   getMessages : GetLogMessagesParameters;
     *   getLatestMessages : GetLatestLogMessagesParameters;
     * };
     */
    sealed class CanisterLogRequest {
        object getMessagesInfo: CanisterLogRequest()
        class getMessages(val getMessages: GetLogMessagesParameters): CanisterLogRequest()
        class getLatestMessages(val getLatestMessages: GetLatestLogMessagesParameters): CanisterLogRequest()

    }
    /**
     * type CanisterLogResponse = variant {
     *   messagesInfo : CanisterLogMessagesInfo;
     *   messages : CanisterLogMessages;
     * };
     */
    sealed class CanisterLogResponse {
        class messagesInfo(val messagesInfo: CanisterLogMessagesInfo): CanisterLogResponse()
        class messages(val messages: CanisterLogMessages): CanisterLogResponse()

    }
    /**
     * type CanisterMetrics = record { data : CanisterMetricsData };
     */
    class CanisterMetrics(
        val data: CanisterMetricsData
    )
    /**
     * type CanisterMetricsData = variant {
     *   hourly : vec HourlyMetricsData;
     *   daily : vec DailyMetricsData;
     * };
     */
    sealed class CanisterMetricsData {
        class Hourly(val hourly: kotlin.Array<HourlyMetricsData>): CanisterMetricsData()
        class Daily(val daily: kotlin.Array<DailyMetricsData>): CanisterMetricsData()

    }
    /**
     * type ChunkContent = variant {
     *   remote : record { args : ChunkRequest; canister : principal };
     *   chunk : record {
     *     total_chunks : nat;
     *     content : vec nat8;
     *     storage_allocation : AllocationRecordStable;
     *     current_chunk : opt nat;
     *   };
     * };
     */
    sealed class ChunkContent {
        class remote(
            val args: ChunkRequest,
            val canister: ICPPrincipalApiModel
        ): ChunkContent()

        class chunk(
            val total_chunks: BigInteger,
            val content: kotlin.Array<UByte>,
            val storage_allocation: AllocationRecordStable,
            val current_chunk: BigInteger?
        ): ChunkContent()
    }
    /**
     * type ChunkRequest = record {
     *   token_id : text;
     *   chunk : opt nat;
     *   library_id : text;
     * };
     */
    class ChunkRequest(
        val token_id: String,
        val chunk: BigInteger?,
        val library_id: String
    )
    /**
     * type ChunkResult = variant { ok : ChunkContent; err : OrigynError };
     */
    sealed class ChunkResult {
        class ok(val ok: ChunkContent): ChunkResult()
        class err(val err: OrigynError): ChunkResult()

    }
    /**
     * type CollectionInfo = record {
     *   multi_canister_count : opt nat;
     *   managers : opt vec principal;
     *   owner : opt principal;
     *   metadata : opt CandyShared;
     *   logo : opt text;
     *   name : opt text;
     *   network : opt principal;
     *   created_at : opt nat64;
     *   fields : opt vec record { text; opt nat; opt nat };
     *   upgraded_at : opt nat64;
     *   token_ids_count : opt nat;
     *   available_space : opt nat;
     *   multi_canister : opt vec principal;
     *   token_ids : opt vec text;
     *   transaction_count : opt nat;
     *   unique_holders : opt nat;
     *   total_supply : opt nat;
     *   symbol : opt text;
     *   allocated_storage : opt nat;
     * };
     */
    class CollectionInfo(
        val multi_canister_count: BigInteger?,
        val managers: kotlin.Array<ICPPrincipalApiModel>?,
        val owner: ICPPrincipalApiModel?,
        val metadata: CandyShared?,
        val logo: String?,
        val name: String?,
        val network: ICPPrincipalApiModel?,
        val created_at: ULong?,
        val fields: kotlin.Array<Fields>?,
        val upgraded_at: ULong?,
        val token_ids_count: BigInteger?,
        val available_space: BigInteger?,
        val multi_canister: kotlin.Array<ICPPrincipalApiModel>?,
        val token_ids: kotlin.Array<String>?,
        val transaction_count: BigInteger?,
        val unique_holders: BigInteger?,
        val total_supply: BigInteger?,
        val symbol: String?,
        val allocated_storage: BigInteger?
    ) {
        class Fields(
            val textValue: String,
            val natValue: BigInteger?,
            val natValue_2: BigInteger?
        )

    }
    /**
     * type CollectionResult = variant { ok : CollectionInfo; err : OrigynError };
     */
    sealed class CollectionResult {
        class ok(val ok: CollectionInfo): CollectionResult()
        class err(val err: OrigynError): CollectionResult()

    }
    /**
     * type DIP721BoolResult = variant { Ok : bool; Err : NftError };
     */
    sealed class DIP721BoolResult {
        class Ok(val Ok: Boolean): DIP721BoolResult()
        class Err(val Err: NftError): DIP721BoolResult()

    }
    /**
     * type DIP721Metadata = record {
     *   logo : opt text;
     *   name : opt text;
     *   created_at : nat64;
     *   upgraded_at : nat64;
     *   custodians : vec principal;
     *   symbol : opt text;
     * };
     */
    class DIP721Metadata(
        val logo: String?,
        val name: String?,
        val created_at: ULong,
        val upgraded_at: ULong,
        val custodians: kotlin.Array<ICPPrincipalApiModel>,
        val symbol: String?
    )
    /**
     * type DIP721NatResult = variant { Ok : nat; Err : NftError };
     */
    sealed class DIP721NatResult {
        class Ok(val Ok: BigInteger): DIP721NatResult()
        class Err(val Err: NftError): DIP721NatResult()

    }
    /**
     * type DIP721Stats = record {
     *   cycles : nat;
     *   total_transactions : nat;
     *   total_unique_holders : nat;
     *   total_supply : nat;
     * };
     */
    class DIP721Stats(
        val cycles: BigInteger,
        val total_transactions: BigInteger,
        val total_unique_holders: BigInteger,
        val total_supply: BigInteger
    )
    /**
     * type DIP721SupportedInterface = variant {
     *   Burn;
     *   Mint;
     *   Approval;
     *   TransactionHistory;
     * };
     */
    sealed class DIP721SupportedInterface {
        object Burn: DIP721SupportedInterface()
        object Mint: DIP721SupportedInterface()
        object Approval: DIP721SupportedInterface()
        object TransactionHistory: DIP721SupportedInterface()

    }
    /**
     * type DIP721TokenMetadata = variant { Ok : TokenMetadata; Err : NftError };
     */
    sealed class DIP721TokenMetadata {
        class Ok(val Ok: TokenMetadata): DIP721TokenMetadata()
        class Err(val Err: NftError): DIP721TokenMetadata()

    }
    /**
     * type DIP721TokensListMetadata = variant { Ok : vec nat; Err : NftError };
     */
    sealed class DIP721TokensListMetadata {
        class Ok(val Ok: kotlin.Array<BigInteger>): DIP721TokensListMetadata()
        class Err(val Err: NftError): DIP721TokensListMetadata()

    }
    /**
     * type DIP721TokensMetadata = variant { Ok : vec TokenMetadata; Err : NftError };
     */
    sealed class DIP721TokensMetadata {
        class Ok(val Ok: kotlin.Array<TokenMetadata>): DIP721TokensMetadata()
        class Err(val Err: NftError): DIP721TokensMetadata()

    }
    /**
     * type DailyMetricsData = record {
     *   updateCalls : nat64;
     *   canisterHeapMemorySize : NumericEntity;
     *   canisterCycles : NumericEntity;
     *   canisterMemorySize : NumericEntity;
     *   timeMillis : int;
     * };
     */
    class DailyMetricsData(
        val updateCalls: ULong,
        val canisterHeapMemorySize: NumericEntity,
        val canisterCycles: NumericEntity,
        val canisterMemorySize: NumericEntity,
        val timeMillis: BigInteger
    )
    /**
     * type Data = variant {
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
     */
    sealed class Data {
        class Int(val Int: BigInteger): Data()
        class Map(val Map: kotlin.Array<MapClass>): Data()
        class Nat(val Nat: BigInteger): Data()
        class Set(val Set: kotlin.Array<CandyShared>): Data()
        class Nat16(val Nat16: UShort): Data()
        class Nat32(val Nat32: UInt): Data()
        class Nat64(val Nat64: ULong): Data()
        class Blob(val Blob: kotlin.Array<UByte>): Data()
        class Bool(val Bool: Boolean): Data()
        class Int8(val Int8: Byte): Data()
        class Ints(val Ints: kotlin.Array<BigInteger>): Data()
        class Nat8(val Nat8: UByte): Data()
        class Nats(val Nats: kotlin.Array<BigInteger>): Data()
        class Text(val Text: String): Data()
        class Bytes(val Bytes: kotlin.Array<UByte>): Data()
        class Int16(val Int16: Short): Data()
        class Int32(val Int32: Int): Data()
        class Int64(val Int64: Long): Data()
        class Option(val Option: CandyShared?): Data()
        class Floats(val Floats: kotlin.Array<Float>): Data()
        class Float(val Float: Double): Data()
        class Principal(val Principal: ICPPrincipalApiModel): Data()
        class Array(val Array: kotlin.Array<CandyShared>): Data()
        class Class(val Class: kotlin.Array<PropertyShared>): Data()

        class MapClass(
            val key: CandyShared,
            val value: CandyShared
        )

    }
    /**
     * type DataCertificate = record { certificate : vec nat8; hash_tree : vec nat8 };
     */
    class DataCertificate(
        val certificate: kotlin.Array<UByte>,
        val hash_tree: kotlin.Array<UByte>
    )
    /**
     * type DepositDetail = record {
     *   token : TokenSpec__1;
     *   trx_id : opt TransactionID__1;
     *   seller : Account;
     *   buyer : Account;
     *   amount : nat;
     *   sale_id : opt text;
     * };
     */
    class DepositDetail(
        val token: TokenSpec__1,
        val trx_id: TransactionID__1?,
        val seller: Account,
        val buyer: Account,
        val amount: BigInteger,
        val sale_id: String?
    )
    /**
     * type DepositWithdrawDescription = record {
     *   token : TokenSpec__1;
     *   withdraw_to : Account;
     *   buyer : Account;
     *   amount : nat;
     * };
     */
    class DepositWithdrawDescription(
        val token: TokenSpec__1,
        val withdraw_to: Account,
        val buyer: Account,
        val amount: BigInteger
    )
    /**
     * type DistributeSaleRequest = record { seller : opt Account };
     */
    class DistributeSaleRequest(
        val seller: Account?
    )
    /**
     * type DutchParams = record {
     *   time_unit : variant { day : nat; hour : nat; minute : nat };
     *   decay_type : variant { flat : nat; percent : float64 };
     * };
     */
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
    /**
     * type EXTBalanceRequest = record { token : EXTTokenIdentifier; user : EXTUser };
     */
    class EXTBalanceRequest(
        val token: EXTTokenIdentifier,
        val user: EXTUser
    )
    /**
     * type EXTBalanceResult = variant { ok : EXTBalance; err : EXTCommonError };
     */
    sealed class EXTBalanceResult {
        class ok(val ok: EXTBalance): EXTBalanceResult()
        class err(val err: EXTCommonError): EXTBalanceResult()

    }
    /**
     * type EXTBearerResult = variant {
     *   ok : EXTAccountIdentifier;
     *   err : EXTCommonError;
     * };
     */
    sealed class EXTBearerResult {
        class ok(val ok: EXTAccountIdentifier): EXTBearerResult()
        class err(val err: EXTCommonError): EXTBearerResult()

    }
    /**
     * type EXTCommonError = variant {
     *   InvalidToken : EXTTokenIdentifier;
     *   Other : text;
     * };
     */
    sealed class EXTCommonError {
        class InvalidToken(val InvalidToken: EXTTokenIdentifier): EXTCommonError()
        class Other(val Other: String): EXTCommonError()

    }
    /**
     * type EXTMetadata = variant {
     *   fungible : record {
     *     decimals : nat8;
     *     metadata : opt vec nat8;
     *     name : text;
     *     symbol : text;
     *   };
     *   nonfungible : record { metadata : opt vec nat8 };
     * };
     */
    sealed class EXTMetadata {
        class fungible(
            val decimals: UByte,
            val metadata: kotlin.Array<UByte>?,
            val name: String,
            val symbol: String
        ): EXTMetadata()

        class nonfungible(
            val metadata: kotlin.Array<UByte>?
        ): EXTMetadata()
    }
    /**
     * type EXTMetadataResult = variant { ok : EXTMetadata; err : EXTCommonError };
     */
    sealed class EXTMetadataResult {
        class ok(val ok: EXTMetadata): EXTMetadataResult()
        class err(val err: EXTCommonError): EXTMetadataResult()

    }

    /**
     * type EXTTokensResponse = record {
     *   nat32;
     *   opt record { locked : opt int; seller : principal; price : nat64 };
     *   opt vec nat8;
     * };
     */
    class EXTTokensResponse(
        val nat32Value: UInt,
        val record: Record?,
        val vecValue: kotlin.Array<UByte>?
    ) {
        class Record(
            val locked: BigInteger?,
            val seller: ICPPrincipalApiModel,
            val price: ULong
        )
    }

    /**
     * type EXTTokensResult = variant {
     *   ok : vec EXTTokensResponse;
     *   err : EXTCommonError;
     * };
     */
    sealed class EXTTokensResult {
        class Ok(val ok: kotlin.Array<EXTTokensResponse>): EXTTokensResult()
        class err(val err: EXTCommonError): EXTTokensResult()

    }
    /**
     * type EXTTransferRequest = record {
     *   to : EXTUser;
     *   token : EXTTokenIdentifier;
     *   notify : bool;
     *   from : EXTUser;
     *   memo : EXTMemo;
     *   subaccount : opt EXTSubAccount;
     *   amount : EXTBalance;
     * };
     */
    class EXTTransferRequest(
        val to: EXTUser,
        val token: EXTTokenIdentifier,
        val notify: Boolean,
        val from: EXTUser,
        val memo: EXTMemo,
        val subaccount: OrigynEXTSubAccount?,
        val amount: EXTBalance
    )
    /**
     * type EXTTransferResponse = variant {
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
     */
    sealed class EXTTransferResponse {
        class ok(val ok: EXTBalance): EXTTransferResponse()
        class err(val err: err): EXTTransferResponse()

    }
    /**
     * type EXTUser = variant { "principal" : principal; address : text };
     */
    sealed class EXTUser {
        class principal(val principal: ICPPrincipalApiModel): EXTUser()
        class address(val address: String): EXTUser()

    }
    /**
     * type EndSaleResponse = record {
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
    class EndSaleResponse(
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

            class mint(
                val to: Account__1,
                val from: Account__1,
                val sale: Sale?,
                val extensible: CandyShared
            ): TxnType() {
                class Sale(
                    val token: TokenSpec,
                    val amount: BigInteger
                )
            }

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

            class extensible(val extensible: CandyShared): TxnType()
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
    /**
     * type EndingType = variant { date : int; timeout : nat };
     */
    sealed class EndingType {
        class date(val date: BigInteger): EndingType()
        class timeout(val timeout: BigInteger): EndingType()

    }
    /**
     * type Errors = variant {
     *   nyi;
     *   storage_configuration_error;
     *   escrow_withdraw_payment_failed;
     *   token_not_found;
     *   owner_not_found;
     *   content_not_found;
     *   auction_ended;
     *   out_of_range;
     *   sale_id_does_not_match;
     *   sale_not_found;
     *   kyc_fail;
     *   item_not_owned;
     *   property_not_found;
     *   validate_trx_wrong_host;
     *   withdraw_too_large;
     *   content_not_deserializable;
     *   bid_too_low;
     *   validate_deposit_wrong_amount;
     *   existing_sale_found;
     *   noop;
     *   asset_mismatch;
     *   escrow_cannot_be_removed;
     *   deposit_burned;
     *   cannot_restage_minted_token;
     *   cannot_find_status_in_metadata;
     *   receipt_data_mismatch;
     *   validate_deposit_failed;
     *   unreachable;
     *   unauthorized_access;
     *   item_already_minted;
     *   no_escrow_found;
     *   escrow_owner_not_the_owner;
     *   improper_interface;
     *   app_id_not_found;
     *   token_non_transferable;
     *   kyc_error;
     *   sale_not_over;
     *   escrow_not_large_enough;
     *   update_class_error;
     *   malformed_metadata;
     *   token_id_mismatch;
     *   id_not_found_in_metadata;
     *   auction_not_started;
     *   low_fee_balance;
     *   library_not_found;
     *   attempt_to_stage_system_data;
     *   no_fee_accounts_provided;
     *   validate_deposit_wrong_buyer;
     *   not_enough_storage;
     *   sales_withdraw_payment_failed;
     * };
     */
    sealed class Errors {
        object nyi: Errors()
        object storage_configuration_error: Errors()
        object escrow_withdraw_payment_failed: Errors()
        object token_not_found: Errors()
        object owner_not_found: Errors()
        object content_not_found: Errors()
        object auction_ended: Errors()
        object out_of_range: Errors()
        object sale_id_does_not_match: Errors()
        object sale_not_found: Errors()
        object kyc_fail: Errors()
        object item_not_owned: Errors()
        object property_not_found: Errors()
        object validate_trx_wrong_host: Errors()
        object withdraw_too_large: Errors()
        object content_not_deserializable: Errors()
        object bid_too_low: Errors()
        object validate_deposit_wrong_amount: Errors()
        object existing_sale_found: Errors()
        object noop: Errors()
        object asset_mismatch: Errors()
        object escrow_cannot_be_removed: Errors()
        object deposit_burned: Errors()
        object cannot_restage_minted_token: Errors()
        object cannot_find_status_in_metadata: Errors()
        object receipt_data_mismatch: Errors()
        object validate_deposit_failed: Errors()
        object unreachable: Errors()
        object unauthorized_access: Errors()
        object item_already_minted: Errors()
        object no_escrow_found: Errors()
        object escrow_owner_not_the_owner: Errors()
        object improper_interface: Errors()
        object app_id_not_found: Errors()
        object token_non_transferable: Errors()
        object kyc_error: Errors()
        object sale_not_over: Errors()
        object escrow_not_large_enough: Errors()
        object update_class_error: Errors()
        object malformed_metadata: Errors()
        object token_id_mismatch: Errors()
        object id_not_found_in_metadata: Errors()
        object auction_not_started: Errors()
        object low_fee_balance: Errors()
        object library_not_found: Errors()
        object attempt_to_stage_system_data: Errors()
        object no_fee_accounts_provided: Errors()
        object validate_deposit_wrong_buyer: Errors()
        object not_enough_storage: Errors()
        object sales_withdraw_payment_failed: Errors()

    }
    /**
     * type EscrowReceipt = record {
     *   token : TokenSpec;
     *   token_id : text;
     *   seller : Account__1;
     *   buyer : Account__1;
     *   amount : nat;
     * };
     */
    class EscrowReceipt(
        val token: TokenSpec,
        val token_id: String,
        val seller: Account__1,
        val buyer: Account__1,
        val amount: BigInteger
    )
    /**
     * type EscrowReceipt__1 = record {
     *   token : TokenSpec;
     *   token_id : text;
     *   seller : Account__1;
     *   buyer : Account__1;
     *   amount : nat;
     * };
     */
    class EscrowReceipt__1(
        val token: TokenSpec,
        val token_id: String,
        val seller: Account__1,
        val buyer: Account__1,
        val amount: BigInteger
    )
    /**
     * type EscrowRecord = record {
     *   token : TokenSpec__2;
     *   token_id : text;
     *   seller : Account__2;
     *   lock_to_date : opt int;
     *   buyer : Account__2;
     *   amount : nat;
     *   sale_id : opt text;
     *   account_hash : opt vec nat8;
     * };
     */
    class EscrowRecord(
        val token: TokenSpec__2,
        val token_id: String,
        val seller: Account__2,
        val lock_to_date: BigInteger?,
        val buyer: Account__2,
        val amount: BigInteger,
        val sale_id: String?,
        val account_hash: kotlin.Array<UByte>?
    )
    /**
     * type EscrowRecord__1 = record {
     *   token : TokenSpec__1;
     *   token_id : text;
     *   seller : Account;
     *   lock_to_date : opt int;
     *   buyer : Account;
     *   amount : nat;
     *   sale_id : opt text;
     *   account_hash : opt vec nat8;
     * };
     */
    class EscrowRecord__1(
        val token: TokenSpec__1,
        val token_id: String,
        val seller: Account,
        val lock_to_date: BigInteger?,
        val buyer: Account,
        val amount: BigInteger,
        val sale_id: String?,
        val account_hash: kotlin.Array<UByte>?
    )
    /**
     * type EscrowRequest = record {
     *   token_id : text;
     *   deposit : DepositDetail;
     *   lock_to_date : opt int;
     * };
     */
    class EscrowRequest(
        val token_id: String,
        val deposit: DepositDetail,
        val lock_to_date: BigInteger?
    )
    /**
     * type EscrowResponse = record {
     *   balance : nat;
     *   receipt : EscrowReceipt;
     *   transaction : TransactionRecord;
     * };
     */
    class EscrowResponse(
        val balance: BigInteger,
        val receipt: EscrowReceipt,
        val transaction: TransactionRecord
    )
    /**
     * type FeeDepositRequest = record { token : TokenSpec__1; account : Account };
     */
    class FeeDepositRequest(
        val token: TokenSpec__1,
        val account: Account
    )
    /**
     * type FeeDepositResponse = record {
     *   balance : nat;
     *   transaction : TransactionRecord;
     * };
     */
    class FeeDepositResponse(
        val balance: BigInteger,
        val transaction: TransactionRecord
    )
    /**
     * type FeeDepositWithdrawDescription = record {
     *   status : variant { locked : record { sale_id : text }; unlocked };
     *   token : TokenSpec__1;
     *   withdraw_to : Account;
     *   account : Account;
     *   amount : nat;
     * };
     */
    class FeeDepositWithdrawDescription(
        val status: Status,
        val token: TokenSpec__1,
        val withdraw_to: Account,
        val account: Account,
        val amount: BigInteger
    ) {
        sealed class Status {
            class locked(
                val sale_id: String
            ): Status()

            object unlocked: Status()

        }

    }

    /**
     * type Vec = vec record {
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
     */
    class Vec(
        val textValue: String,
        val variant: VecVariant
    ) {
        sealed class VecVariant
    }

    /**
     * type GenericValue = variant {
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
     */
    sealed class GenericValue {
        class Nat64Content(val Nat64Content: ULong): GenericValue()
        class Nat32Content(val Nat32Content: UInt): GenericValue()
        class BoolContent(val BoolContent: Boolean): GenericValue()
        class Nat8Content(val Nat8Content: UByte): GenericValue()
        class Int64Content(val Int64Content: Long): GenericValue()
        class IntContent(val IntContent: BigInteger): GenericValue()
        class NatContent(val NatContent: BigInteger): GenericValue()
        class Nat16Content(val Nat16Content: UShort): GenericValue()
        class Int32Content(val Int32Content: Int): GenericValue()
        class Int8Content(val Int8Content: Byte): GenericValue()
        class FloatContent(val FloatContent: Double): GenericValue()
        class Int16Content(val Int16Content: Short): GenericValue()
        class BlobContent(val BlobContent: kotlin.Array<UByte>): GenericValue()
        class NestedContent(val NestedContent: Vec): GenericValue()
        class Principal(val Principal: ICPPrincipalApiModel): GenericValue()
        class TextContent(val TextContent: String): GenericValue()

    }
    /**
     * type GetArchivesArgs = record { from : opt principal };
     */
    class GetArchivesArgs(
        val from: ICPPrincipalApiModel?
    )
    /**
     * type GetArchivesResultItem = record {
     *   end : nat;
     *   canister_id : principal;
     *   start : nat;
     * };
     */
    class GetArchivesResultItem(
        val end: BigInteger,
        val canister_id: ICPPrincipalApiModel,
        val start: BigInteger
    )
    /**
     * type GetLatestLogMessagesParameters = record {
     *   upToTimeNanos : opt Nanos;
     *   count : nat32;
     *   filter : opt GetLogMessagesFilter;
     * };
     */
    class GetLatestLogMessagesParameters(
        val upToTimeNanos: OrigynNanos?,
        val count: UInt,
        val filter: GetLogMessagesFilter?
    )
    /**
     * type GetLogMessagesFilter = record {
     *   analyzeCount : nat32;
     *   messageRegex : opt text;
     *   messageContains : opt text;
     * };
     */
    class GetLogMessagesFilter(
        val analyzeCount: UInt,
        val messageRegex: String?,
        val messageContains: String?
    )
    /**
     * type GetLogMessagesParameters = record {
     *   count : nat32;
     *   filter : opt GetLogMessagesFilter;
     *   fromTimeNanos : opt Nanos;
     * };
     */
    class GetLogMessagesParameters(
        val count: UInt,
        val filter: GetLogMessagesFilter?,
        val fromTimeNanos: OrigynNanos?
    )
    /**
     * type GetMetricsParameters = record {
     *   dateToMillis : nat;
     *   granularity : MetricsGranularity;
     *   dateFromMillis : nat;
     * };
     */
    class GetMetricsParameters(
        val dateToMillis: BigInteger,
        val granularity: MetricsGranularity,
        val dateFromMillis: BigInteger
    )
    /**
     * type GetTransactionsResult = record {
     *   log_length : nat;
     *   blocks : vec record { id : nat; block : Value__1 };
     *   archived_blocks : vec ArchivedTransactionResponse;
     * };
     */
    class GetTransactionsResult(
        val log_length: BigInteger,
        val blocks: kotlin.Array<Blocks>,
        val archived_blocks: kotlin.Array<ArchivedTransactionResponse>
    ) {
        class Blocks(
            val id: BigInteger,
            val block: Value__1
        )

    }
    /**
     * type GetTransactionsResult__1 = record {
     *   log_length : nat;
     *   blocks : vec record { id : nat; block : Value__1 };
     *   archived_blocks : vec ArchivedTransactionResponse;
     * };
     */
    class GetTransactionsResult__1(
        val log_length: BigInteger,
        val blocks: kotlin.Array<Blocks>,
        val archived_blocks: kotlin.Array<ArchivedTransactionResponse>
    ) {
        class Blocks(
            val id: BigInteger,
            val block: Value__1
        )

    }
    /**
     * type GovernanceRequest = variant {
     *   update_system_var : record { key : text; val : CandyShared; token_id : text };
     *   clear_shared_wallets : text;
     * };
     */
    sealed class GovernanceRequest {
        class update_system_var(
            val key: String,
            val candyShared: CandyShared,
            val token_id: String
        ): GovernanceRequest()

        class clear_shared_wallets(val clear_shared_wallets: String): GovernanceRequest()

    }
    /**
     * type GovernanceResponse = variant {
     *   update_system_var : bool;
     *   clear_shared_wallets : bool;
     * };
     */
    sealed class GovernanceResponse {
        class update_system_var(val update_system_var: Boolean): GovernanceResponse()
        class clear_shared_wallets(val clear_shared_wallets: Boolean): GovernanceResponse()

    }
    /**
     * type GovernanceResult = variant { ok : GovernanceResponse; err : OrigynError };
     */
    sealed class GovernanceResult {
        class ok(val ok: GovernanceResponse): GovernanceResult()
        class err(val err: OrigynError): GovernanceResult()

    }
    /**
     * type HTTPResponse = record {
     *   body : vec nat8;
     *   headers : vec HeaderField;
     *   streaming_strategy : opt StreamingStrategy;
     *   status_code : nat16;
     * };
     */
    class HTTPResponse(
        val body: kotlin.Array<UByte>,
        val headers: kotlin.Array<HeaderField>,
        val streaming_strategy: StreamingStrategy?,
        val status_code: UShort
    )
    /**
     * type HeaderField = record { text; text };
     */
    class HeaderField(
        val textValue_1: String,
        val textValue_2: String
    )
    /**
     * type HistoryResult = variant { ok : vec TransactionRecord; err : OrigynError };
     */
    sealed class HistoryResult {
        class Ok(val ok: kotlin.Array<TransactionRecord>): HistoryResult()
        class err(val err: OrigynError): HistoryResult()

    }
    /**
     * type HourlyMetricsData = record {
     *   updateCalls : UpdateCallsAggregatedData;
     *   canisterHeapMemorySize : CanisterHeapMemoryAggregatedData;
     *   canisterCycles : CanisterCyclesAggregatedData;
     *   canisterMemorySize : CanisterMemoryAggregatedData;
     *   timeMillis : int;
     * };
     */
    class HourlyMetricsData(
        val updateCalls: OrigynUpdateCallsAggregatedData,
        val canisterHeapMemorySize: OrigynCanisterHeapMemoryAggregatedData,
        val canisterCycles: OrigynCanisterCyclesAggregatedData,
        val canisterMemorySize: OrigynCanisterMemoryAggregatedData,
        val timeMillis: BigInteger
    )
    /**
     * type HttpRequest = record {
     *   url : text;
     *   method : text;
     *   body : vec nat8;
     *   headers : vec HeaderField;
     * };
     */
    class HttpRequest(
        val url: String,
        val method: String,
        val body: kotlin.Array<UByte>,
        val headers: kotlin.Array<HeaderField>
    )
    /**
     * type ICTokenSpec = record {
     *   id : opt nat;
     *   fee : opt nat;
     *   decimals : nat;
     *   canister : principal;
     *   standard : variant { ICRC1; EXTFungible; DIP20; Other : CandyShared; Ledger };
     *   symbol : text;
     * };
     */
    class ICTokenSpec(
        val id: BigInteger?,
        val fee: BigInteger?,
        val decimals: BigInteger,
        val canister: ICPPrincipalApiModel,
        val standard: Standard,
        val symbol: String
    ) {
        sealed class Standard {
            object ICRC1: Standard()
            object EXTFungible: Standard()
            object DIP20: Standard()
            class Other(val Other: CandyShared): Standard()
            object Ledger: Standard()

        }

    }
    /**
     * type ICTokenSpec__1 = record {
     *   id : opt nat;
     *   fee : opt nat;
     *   decimals : nat;
     *   canister : principal;
     *   standard : variant { ICRC1; EXTFungible; DIP20; Other : CandyShared; Ledger };
     *   symbol : text;
     * };
     */
    class ICTokenSpec__1(
        val id: BigInteger?,
        val fee: BigInteger?,
        val decimals: BigInteger,
        val canister: ICPPrincipalApiModel,
        val standard: Standard,
        val symbol: String
    ) {
        sealed class Standard {
            object ICRC1: Standard()
            object EXTFungible: Standard()
            object DIP20: Standard()
            class Other(val Other: CandyShared): Standard()
            object Ledger: Standard()

        }

    }
    /**
     * type IndexType = variant { Stable; StableTyped; Managed };
     */
    sealed class IndexType {
        object Stable: IndexType()
        object StableTyped: IndexType()
        object Managed: IndexType()

    }
    /**
     * type InstantFeature = variant {
     *   fee_schema : text;
     *   fee_accounts : FeeAccountsParams;
     * };
     */
    sealed class InstantFeature {
        class fee_schema(val fee_schema: String): InstantFeature()
        class fee_accounts(val fee_accounts: FeeAccountsParams): InstantFeature()

    }
    /**
     * type LogMessagesData = record {
     *   data : Data;
     *   timeNanos : Nanos;
     *   message : text;
     *   caller : Caller;
     * };
     */
    class LogMessagesData(
        val data: Data,
        val timeNanos: OrigynNanos,
        val message: String,
        val caller: Caller
    )
    /**
     * type ManageCollectionCommand = variant {
     *   UpdateOwner : principal;
     *   UpdateManagers : vec principal;
     *   UpdateMetadata : record { text; opt CandyShared; bool };
     *   UpdateAnnounceCanister : opt principal;
     *   UpdateNetwork : opt principal;
     *   UpdateSymbol : opt text;
     *   UpdateLogo : opt text;
     *   UpdateName : opt text;
     * };
     */
    sealed class ManageCollectionCommand {
        class UpdateOwner(val UpdateOwner: ICPPrincipalApiModel): ManageCollectionCommand()
        class UpdateManagers(val UpdateManagers: kotlin.Array<ICPPrincipalApiModel>): ManageCollectionCommand()
        class UpdateMetadata(
            val textValue: String,
            val candyShared: CandyShared?,
            val boolValue: Boolean
        ): ManageCollectionCommand()

        class UpdateAnnounceCanister(val UpdateAnnounceCanister: ICPPrincipalApiModel?): ManageCollectionCommand()
        class UpdateNetwork(val UpdateNetwork: ICPPrincipalApiModel?): ManageCollectionCommand()
        class UpdateSymbol(val UpdateSymbol: String?): ManageCollectionCommand()
        class UpdateLogo(val UpdateLogo: String?): ManageCollectionCommand()
        class UpdateName(val UpdateName: String?): ManageCollectionCommand()

    }
    /**
     * type ManageSaleRequest = variant {
     *   bid : BidRequest;
     *   escrow_deposit : EscrowRequest;
     *   fee_deposit : FeeDepositRequest;
     *   recognize_escrow : EscrowRequest;
     *   withdraw : WithdrawRequest;
     *   ask_subscribe : AskSubscribeRequest;
     *   end_sale : text;
     *   refresh_offers : opt Account;
     *   distribute_sale : DistributeSaleRequest;
     *   open_sale : text;
     * };
     */
    sealed class ManageSaleRequest {
        class bid(val bid: BidRequest): ManageSaleRequest()
        class escrow_deposit(val escrow_deposit: EscrowRequest): ManageSaleRequest()
        class fee_deposit(val fee_deposit: FeeDepositRequest): ManageSaleRequest()
        class recognize_escrow(val recognize_escrow: EscrowRequest): ManageSaleRequest()
        class withdraw(val withdraw: WithdrawRequest): ManageSaleRequest()
        class ask_subscribe(val ask_subscribe: AskSubscribeRequest): ManageSaleRequest()
        class end_sale(val end_sale: String): ManageSaleRequest()
        class refresh_offers(val refresh_offers: Account?): ManageSaleRequest()
        class distribute_sale(val distribute_sale: DistributeSaleRequest): ManageSaleRequest()
        class open_sale(val open_sale: String): ManageSaleRequest()

    }
    /**
     * type ManageSaleResponse = variant {
     *   bid : BidResponse;
     *   escrow_deposit : EscrowResponse;
     *   fee_deposit : FeeDepositResponse;
     *   recognize_escrow : RecognizeEscrowResponse;
     *   withdraw : WithdrawResponse;
     *   ask_subscribe : AskSubscribeResponse;
     *   end_sale : EndSaleResponse;
     *   refresh_offers : vec EscrowRecord__1;
     *   distribute_sale : DistributeSaleResponse;
     *   open_sale : bool;
     * };
     */
    sealed class ManageSaleResponse {
        class bid(val bid: BidResponse): ManageSaleResponse()
        class escrow_deposit(val escrow_deposit: EscrowResponse): ManageSaleResponse()
        class fee_deposit(val fee_deposit: FeeDepositResponse): ManageSaleResponse()
        class recognize_escrow(val recognize_escrow: RecognizeEscrowResponse): ManageSaleResponse()
        class withdraw(val withdraw: WithdrawResponse): ManageSaleResponse()
        class ask_subscribe(val ask_subscribe: AskSubscribeResponse): ManageSaleResponse()
        class end_sale(val end_sale: EndSaleResponse): ManageSaleResponse()
        class Refresh_offers(val refresh_offers: kotlin.Array<EscrowRecord__1>): ManageSaleResponse()
        class distribute_sale(val distribute_sale: DistributeSaleResponse): ManageSaleResponse()
        class open_sale(val open_sale: Boolean): ManageSaleResponse()

    }
    /**
     * type ManageSaleResult = variant { ok : ManageSaleResponse; err : OrigynError };
     */
    sealed class ManageSaleResult {
        class ok(val ok: ManageSaleResponse): ManageSaleResult()
        class err(val err: OrigynError): ManageSaleResult()

    }
    /**
     * type ManageStorageRequest = variant {
     *   add_storage_canisters : vec record {
     *     principal;
     *     nat;
     *     record { nat; nat; nat };
     *   };
     *   configure_storage : variant { stableBtree : opt nat; heap : opt nat };
     * };
     */
    sealed class ManageStorageRequest {

        class add_storage_canisters(
            val principal: ICPPrincipalApiModel,
            val natValue: BigInteger,
            val record: RecordClass
        ) : ManageStorageRequest() {
            class RecordClass(
                val natValue_1 : BigInteger,
                val natValue_2 : BigInteger,
                val natValue_3 : BigInteger
            )
        }

        sealed class configure_storage: ManageStorageRequest() {
            class stableBtree(val stableBtree: BigInteger?)
            class heap(val heap: BigInteger?)
        }

    }
    /**
     * type ManageStorageResponse = variant {
     *   add_storage_canisters : record { nat; nat };
     *   configure_storage : record { nat; nat };
     * };
     */
    sealed class ManageStorageResponse {
        class add_storage_canisters(
            val natValue_1: BigInteger,
            val natValue_2: BigInteger
        ): ManageStorageResponse()

        class configure_storage(
            val natValue_1: BigInteger,
            val natValue_2: BigInteger
        ): ManageStorageResponse()
    }
    /**
     * type ManageStorageResult = variant {
     *   ok : ManageStorageResponse;
     *   err : OrigynError;
     * };
     */
    sealed class ManageStorageResult {
        class ok(val ok: ManageStorageResponse): ManageStorageResult()
        class err(val err: OrigynError): ManageStorageResult()

    }
    /**
     * type MarketTransferRequest = record {
     *   token_id : text;
     *   sales_config : SalesConfig;
     * };
     */
    class MarketTransferRequest(
        val token_id: String,
        val sales_config: SalesConfig
    )
    /**
     * type MarketTransferRequestReponse = record {
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
    class MarketTransferRequestReponse(
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

            class mint(
                val to: Account__1,
                val from: Account__1,
                val sale: Sale?,
                val extensible: CandyShared
            ): TxnType() {
                class Sale(
                    val token_id: TokenSpec,
                    val amount: BigInteger
                )
            }

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

            class extensible(val extensible: CandyShared): TxnType()
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
    /**
     * type MarketTransferResult = variant {
     *   ok : MarketTransferRequestReponse;
     *   err : OrigynError;
     * };
     */
    sealed class MarketTransferResult {
        class ok(val ok: MarketTransferRequestReponse): MarketTransferResult()
        class err(val err: OrigynError): MarketTransferResult()

    }
    /**
     * type MetricsGranularity = variant { hourly; daily };
     */
    sealed class MetricsGranularity {
        object hourly: MetricsGranularity()
        object daily: MetricsGranularity()

    }
    /**
     * type MinIncreaseType = variant { amount : nat; percentage : float64 };
     */
    sealed class MinIncreaseType {
        class amount(val amount: BigInteger): MinIncreaseType()
        class percentage(val percentage: Double): MinIncreaseType()

    }
    /**
     * type NFTBackupChunk = record {
     *   sales_balances : StableSalesBalances;
     *   offers : StableOffers;
     *   collection_data : StableCollectionData;
     *   nft_ledgers : StableNftLedger;
     *   canister : principal;
     *   allocations : vec record { record { text; text }; AllocationRecordStable };
     *   nft_sales : vec record { text; SaleStatusShared };
     *   buckets : vec record { principal; StableBucketData };
     *   escrow_balances : StableEscrowBalances;
     * };
     */
    class NFTBackupChunk(
        val sales_balances: StableSalesBalances,
        val offers: StableOffers,
        val collection_data: StableCollectionData,
        val nft_ledgers: StableNftLedger,
        val canister: ICPPrincipalApiModel,
        val allocations: kotlin.Array<Allocations>,
        val nft_sales: kotlin.Array<NftSales>,
        val buckets: kotlin.Array<Buckets>,
        val escrow_balances: StableEscrowBalances
    ) {
        class Allocations(
            val allocationsValue: AllocationsValue,
            val allocationRecordStable: AllocationRecordStable
        ) {
            class AllocationsValue(
                val textValue_1: String,
                val textValue_2: String
            )
        }

        class NftSales(
            val textValue: String,
            val saleStatusShared: SaleStatusShared
        )

        class Buckets(
            val icpPrincipalApiModel: ICPPrincipalApiModel,
            val stableBucketData: StableBucketData
        )

    }
    /**
     * type NFTInfoResult = variant { ok : NFTInfoStable; err : OrigynError };
     */
    sealed class NFTInfoResult {
        class ok(val ok: NFTInfoStable): NFTInfoResult()
        class err(val err: OrigynError): NFTInfoResult()

    }
    /**
     * type NFTInfoStable = record {
     *   metadata : CandyShared;
     *   current_sale : opt SaleStatusShared;
     * };
     */
    class NFTInfoStable(
        val metadata: CandyShared,
        val current_sale: SaleStatusShared?
    )
    /**
     * type NFTUpdateRequest = variant {
     *   update : record {
     *     token_id : text;
     *     update : UpdateRequestShared;
     *     app_id : text;
     *   };
     *   replace : record { token_id : text; data : CandyShared };
     * };
     */
    sealed class NFTUpdateRequest {
        class update(
            val token_id: String,
            val update: UpdateRequestShared,
            val app_id: String
        ): NFTUpdateRequest()

        class replace(
            val token_id: String,
            val data: CandyShared
        ): NFTUpdateRequest()
    }
    /**
     * type NFTUpdateResult = variant { ok : NFTUpdateResponse; err : OrigynError };
     */
    sealed class NFTUpdateResult {
        class ok(val ok: NFTUpdateResponse): NFTUpdateResult()
        class err(val err: OrigynError): NFTUpdateResult()

    }
    /**
     * type NftError = variant {
     *   UnauthorizedOperator;
     *   SelfTransfer;
     *   TokenNotFound;
     *   UnauthorizedOwner;
     *   TxNotFound;
     *   SelfApprove;
     *   OperatorNotFound;
     *   ExistedNFT;
     *   OwnerNotFound;
     *   Other : text;
     * };
     */
    sealed class NftError {
        object UnauthorizedOperator: NftError()
        object SelfTransfer: NftError()
        object TokenNotFound: NftError()
        object UnauthorizedOwner: NftError()
        object TxNotFound: NftError()
        object SelfApprove: NftError()
        object OperatorNotFound: NftError()
        object ExistedNFT: NftError()
        object OwnerNotFound: NftError()
        class Other(val Other: String): NftError()

    }
    /**
     * type Nft_Canister = service {
     *                   // __advance_time : (int) -> (int);
     *                   // __set_time_mode : (variant { test; standard }) -> (bool);
     *                   // __supports : () -> (vec record { text; text }) query;
     *                   // __version : () -> (text) query;
     *                   // back_up : (nat) -> (
     *                   //     variant { eof : NFTBackupChunk; data : NFTBackupChunk },
     *                   //   ) query;
     *                   // balance : (EXTBalanceRequest) -> (EXTBalanceResult) query;
     *                   // balanceEXT : (EXTBalanceRequest) -> (EXTBalanceResult) query;
     *                   // balance_of_batch_nft_origyn : (vec Account) -> (vec BalanceResult) query;
     *                   balance_of_nft_origyn : (Account) -> (BalanceResult) query;
     *                   // balance_of_secure_batch_nft_origyn : (vec Account) -> (vec BalanceResult);
     *                   // balance_of_secure_nft_origyn : (Account) -> (BalanceResult);
     *                   // bearer : (EXTTokenIdentifier) -> (EXTBearerResult) query;
     *                   // bearerEXT : (EXTTokenIdentifier) -> (EXTBearerResult) query;
     *                   // bearer_batch_nft_origyn : (vec text) -> (vec BearerResult) query;
     *                   // bearer_batch_secure_nft_origyn : (vec text) -> (vec BearerResult);
     *                   // bearer_nft_origyn : (text) -> (BearerResult) query;
     *                   // bearer_secure_nft_origyn : (text) -> (BearerResult);
     *                   // canister_status : (record { canister_id : canister_id }) -> (canister_status);
     *                   // chunk_nft_origyn : (ChunkRequest) -> (ChunkResult) query;
     *                   // chunk_secure_nft_origyn : (ChunkRequest) -> (ChunkResult);
     *                   // collectCanisterMetrics : () -> () query;
     *                   // collection_nft_origyn : (opt vec record { text; opt nat; opt nat }) -> (
     *                   //     CollectionResult,
     *                   //   ) query;
     *                   // collection_secure_nft_origyn : (
     *                   //     opt vec record { text; opt nat; opt nat },
     *                   //   ) -> (CollectionResult);
     *                   // collection_update_batch_nft_origyn : (vec ManageCollectionCommand) -> (
     *                   //     vec OrigynBoolResult,
     *                   //   );
     *                   // collection_update_nft_origyn : (ManageCollectionCommand) -> (
     *                   //     OrigynBoolResult,
     *                   //   );
     *                   // cycles : () -> (nat) query;
     *                   // dip721_balance_of : (principal) -> (nat) query;
     *                   // dip721_custodians : () -> (vec principal) query;
     *                   // dip721_is_approved_for_all : (principal, principal) -> (
     *                   //     DIP721BoolResult,
     *                   //   ) query;
     *                   // dip721_logo : () -> (opt text) query;
     *                   // dip721_metadata : () -> (DIP721Metadata) query;
     *                   // dip721_name : () -> (opt text) query;
     *                   // dip721_operator_token_identifiers : (principal) -> (
     *                   //     DIP721TokensListMetadata,
     *                   //   ) query;
     *                   // dip721_operator_token_metadata : (principal) -> (DIP721TokensMetadata) query;
     *                   // dip721_owner_of : (nat) -> (OwnerOfResponse) query;
     *                   // dip721_owner_token_identifiers : (principal) -> (
     *                   //     DIP721TokensListMetadata,
     *                   //   ) query;
     *                   // dip721_owner_token_metadata : (principal) -> (DIP721TokensMetadata) query;
     *                   // dip721_stats : () -> (DIP721Stats) query;
     *                   // dip721_supported_interfaces : () -> (vec DIP721SupportedInterface) query;
     *                   // dip721_symbol : () -> (opt text) query;
     *                   // dip721_token_metadata : (nat) -> (DIP721TokenMetadata) query;
     *                   // dip721_total_supply : () -> (nat) query;
     *                   // dip721_total_transactions : () -> (nat) query;
     *                   // dip721_transfer : (principal, nat) -> (DIP721NatResult);
     *                   // dip721_transfer_from : (principal, principal, nat) -> (DIP721NatResult);
     *                   // getCanisterLog : (opt CanisterLogRequest) -> (opt CanisterLogResponse) query;
     *                   // getCanisterMetrics : (GetMetricsParameters) -> (opt CanisterMetrics) query;
     *                   // getEXTTokenIdentifier : (text) -> (text) query;
     *                   // get_access_key : () -> (OrigynTextResult) query;
     *                   // get_halt : () -> (bool) query;
     *                   // get_nat_as_token_id_origyn : (nat) -> (text) query;
     *                   // get_tip : () -> (Tip) query;
     *                   // get_token_id_as_nat : (text) -> (nat) query;
     *                   // governance_batch_nft_origyn : (vec GovernanceRequest) -> (
     *                   //     vec GovernanceResult,
     *                   //   );
     *                   // governance_nft_origyn : (GovernanceRequest) -> (GovernanceResult);
     *                   // history_batch_nft_origyn : (vec record { text; opt nat; opt nat }) -> (
     *                   //     vec HistoryResult,
     *                   //   ) query;
     *                   // history_batch_secure_nft_origyn : (vec record { text; opt nat; opt nat }) -> (
     *                   //     vec HistoryResult,
     *                   //   );
     *                   // istory_nft_origyn : (text, opt nat, opt nat) -> (HistoryResult) query;
     *                   // history_secure_nft_origyn : (text, opt nat, opt nat) -> (HistoryResult);
     *                   // http_access_key : () -> (OrigynTextResult);
     *                   // http_request : (HttpRequest) -> (HTTPResponse) query;
     *                   // http_request_streaming_callback : (StreamingCallbackToken) -> (
     *                   //     StreamingCallbackResponse,
     *                   //   ) query;
     *                   // icrc3_get_archives : (GetArchivesArgs) -> (GetArchivesResult) query;
     *                   // icrc3_get_blocks : (vec TransactionRange) -> (GetTransactionsResult) query;
     *                   // icrc3_get_tip_certificate : () -> (opt DataCertificate) query;
     *                   // icrc3_supported_block_types : () -> (vec BlockType) query;
     *                   // icrc7_approve : (ApprovalArgs) -> (ApprovalResult);
     *                   // icrc7_atomic_batch_transfers : () -> (opt bool) query;
     *                   // icrc7_balance_of : (vec Account__3) -> (vec nat) query;
     *                   // icrc7_collection_metadata : () -> (CollectionMetadata) query;
     *                   // icrc7_default_take_value : () -> (opt nat) query;
     *                   // icrc7_description : () -> (opt text) query;
     *                   // icrc7_logo : () -> (opt text) query;
     *                   // icrc7_max_approvals_per_token_or_collection : () -> (opt nat) query;
     *                   // icrc7_max_memo_size : () -> (opt nat) query;
     *                   // icrc7_max_query_batch_size : () -> (opt nat) query;
     *                   // icrc7_max_revoke_approvals : () -> (opt nat) query;
     *                   // icrc7_max_take_value : () -> (opt nat) query;
     *                   // icrc7_max_update_batch_size : () -> (opt nat) query;
     *                   // icrc7_name : () -> (text) query;
     *                   // icrc7_owner_of : (vec nat) -> (vec opt Account__3) query;
     *                   // icrc7_permitted_drift : () -> (opt nat) query;
     *                   // icrc7_supply_cap : () -> (opt nat) query;
     *                   // icrc7_supported_standards : () -> (vec SupportedStandard) query;
     *                   // icrc7_symbol : () -> (text) query;
     *                   // icrc7_token_metadata : (vec nat) -> (
     *                   //     vec opt vec record { text; Value },
     *                   //   ) query;
     *                   // icrc7_tokens : (opt nat, opt nat32) -> (vec nat) query;
     *                   // icrc7_tokens_of : (Account__3, opt nat, opt nat32) -> (vec nat) query;
     *                   // icrc7_total_supply : () -> (nat) query;
     *                   // icrc7_transfer : (vec TransferArgs) -> (TransferResult);
     *                   // icrc7_transfer_fee : (nat) -> (opt nat) query;
     *                   // icrc7_tx_window : () -> (opt nat) query;
     *                   // manage_storage_nft_origyn : (ManageStorageRequest) -> (ManageStorageResult);
     *                   // market_transfer_batch_nft_origyn : (vec MarketTransferRequest) -> (
     *                   //     vec MarketTransferResult,
     *                   //   );
     *                   // market_transfer_nft_origyn : (MarketTransferRequest) -> (
     *                   //     MarketTransferResult,
     *                   //   );
     *                   // metadata : () -> (DIP721Metadata) query;
     *                   // metadataExt : (EXTTokenIdentifier) -> (EXTMetadataResult) query;
     *                   // mint_batch_nft_origyn : (vec record { text; Account }) -> (
     *                   //     vec OrigynTextResult,
     *                   //   );
     *                   // mint_nft_origyn : (text, Account) -> (OrigynTextResult);
     *                   // nftStreamingCallback : (StreamingCallbackToken) -> (
     *                   //     StreamingCallbackResponse,
     *                   //   ) query;
     *                   nft_batch_origyn : (vec text) -> (vec NFTInfoResult) query;
     *                   // nft_batch_secure_origyn : (vec text) -> (vec NFTInfoResult);
     *                   // nft_origyn : (text) -> (NFTInfoResult) query;
     *                   // nft_secure_origyn : (text) -> (NFTInfoResult);
     *                   // operaterTokenMetadata : (principal) -> (DIP721TokensMetadata) query;
     *                   // ownerOf : (nat) -> (OwnerOfResponse) query;
     *                   // ownerTokenMetadata : (principal) -> (DIP721TokensMetadata) query;
     *                   // sale_batch_nft_origyn : (vec ManageSaleRequest) -> (vec ManageSaleResult);
     *                   // sale_info_batch_nft_origyn : (vec SaleInfoRequest) -> (
     *                   //     vec SaleInfoResult,
     *                   //   ) query;
     *                   // sale_info_batch_secure_nft_origyn : (vec SaleInfoRequest) -> (
     *                   //     vec SaleInfoResult,
     *                   //   );
     *                   // sale_info_nft_origyn : (SaleInfoRequest) -> (SaleInfoResult) query;
     *                   // sale_info_secure_nft_origyn : (SaleInfoRequest) -> (SaleInfoResult);
     *                   // sale_nft_origyn : (ManageSaleRequest) -> (ManageSaleResult);
     *                   // set_data_harvester : (nat) -> ();
     *                   // set_halt : (bool) -> ();
     *                   // share_wallet_nft_origyn : (ShareWalletRequest) -> (OwnerUpdateResult);
     *                   // stage_batch_nft_origyn : (vec record { metadata : CandyShared }) -> (
     *                   //     vec OrigynTextResult,
     *                   //   );
     *                   // stage_library_batch_nft_origyn : (vec StageChunkArg) -> (
     *                   //     vec StageLibraryResult,
     *                   //   );
     *                   // stage_library_nft_origyn : (StageChunkArg) -> (StageLibraryResult);
     *                   // stage_nft_origyn : (record { metadata : CandyShared }) -> (OrigynTextResult);
     *                   // state_size : () -> (StateSize) query;
     *                   // storage_info_nft_origyn : () -> (StorageMetricsResult) query;
     *                   // storage_info_secure_nft_origyn : () -> (StorageMetricsResult);
     *                   // tokens_ext : (text) -> (EXTTokensResult) query;
     *                   // transfer : (EXTTransferRequest) -> (EXTTransferResponse);
     *                   // transferDip721 : (principal, nat) -> (DIP721NatResult);
     *                   // transferEXT : (EXTTransferRequest) -> (EXTTransferResponse);
     *                   // transferFrom : (principal, principal, nat) -> (DIP721NatResult);
     *                   // transferFromDip721 : (principal, principal, nat) -> (DIP721NatResult);
     *                   // update_app_nft_origyn : (NFTUpdateRequest) -> (NFTUpdateResult);
     *                   // pdate_icrc3 : (vec UpdateSetting) -> (vec bool);
     *                   // wallet_receive : () -> (nat);
     *                   whoami : () -> (principal) query;
     *                 };
     */
    class Nft_Canister(
        private val canister: ICPPrincipal
    ) {

        /**
         * balance_of_nft_origyn : (Account) -> (BalanceResult) query;
         */
        suspend fun balance_of_nft_origyn(
            account: Account
        ): BalanceResult {

            /*val query = ICPQuery(
                methodName = "balance_of_nft_origyn",
                canister = canister
            )
            val result = query.query(
                values = listOf(
                    ValueToEncode(
                        arg = account,
                        expectedClass = Account::class,
                        expectedClassNullable = false
                    )
                )
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())*/

            val canisterRepository: ICPCanisterRepository = icpCanisterRepository
            val args = listOf(
                CandidValue.Variant(
                    CandidVariant(
                        candidTypes = mapOf("principal" to CandidType.Principal),
                        value = Pair("principal", CandidValue.Principal((account as Account.principal).principal.string))
                    )
                )
            )
            val icpMethod = ICPMethod(
                canister = canister,
                methodName = "balance_of_nft_origyn",
                args = args
            )
            val result = canisterRepository.query(icpMethod)
            val ok = CandidDecoder.decodeNotNull<BalanceResult>(result.getOrThrow().first())
            return ok
        }

        /**
         * nft_batch_origyn : (vec text) -> (vec NFTInfoResult) query;
         */
        suspend fun nft_batch_origyn(
            textValues: Array<String>
        ): Array<NFTInfoResult> {
            val query = ICPQuery(
                methodName = "nft_batch_origyn",
                canister = canister
            )
            val result = query.query(
                values = listOf(
                    ValueToEncode(
                        arg = textValues,
                        arrayType = String::class,
                        arrayTypeNullable = false
                    )
                )
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

    }

    /**
     * type NiftySettlementType = record {
     *   fixed : bool;
     *   interestRatePerSecond : float64;
     *   duration : opt int;
     *   expiration : opt int;
     *   lenderOffer : bool;
     * };
     */
    class NiftySettlementType(
        val fixed: Boolean,
        val interestRatePerSecond: Double,
        val duration: BigInteger?,
        val expiration: BigInteger?,
        val lenderOffer: Boolean
    )
    /**
     * type NumericEntity = record {
     *   avg : nat64;
     *   max : nat64;
     *   min : nat64;
     *   first : nat64;
     *   last : nat64;
     * };
     */
    class NumericEntity(
        val avg: ULong,
        val max: ULong,
        val min: ULong,
        val first: ULong,
        val last: ULong
    )
    /**
     * type OrigynBoolResult = variant { ok : bool; err : OrigynError };
     */
    sealed class OrigynBoolResult {
        class ok(val ok: Boolean): OrigynBoolResult()
        class err(val err: OrigynError): OrigynBoolResult()

    }
    /**
     * type OrigynError = record {
     *   "text" : text;
     *   error : Errors;
     *   number : nat32;
     *   flag_point : text;
     * };
     */
    class OrigynError(
        val text: String,
        val error: Errors,
        val number: UInt,
        val flag_point: String
    )
    /**
     * type OrigynTextResult = variant { ok : text; err : OrigynError };
     */
    sealed class OrigynTextResult {
        class ok(val ok: String): OrigynTextResult()
        class err(val err: OrigynError): OrigynTextResult()

    }
    /**
     * type OwnerOfResponse = variant { Ok : opt principal; Err : NftError };
     */
    sealed class OwnerOfResponse {
        class Ok(val Ok: ICPPrincipalApiModel?): OwnerOfResponse()
        class Err(val Err: NftError): OwnerOfResponse()

    }
    /**
     * type OwnerTransferResponse = record {
     *   transaction : TransactionRecord;
     *   assets : vec CandyShared;
     * };
     */
    class OwnerTransferResponse(
        val transaction: TransactionRecord,
        val assets: kotlin.Array<CandyShared>
    )
    /**
     * type OwnerUpdateResult = variant {
     *   ok : OwnerTransferResponse;
     *   err : OrigynError;
     * };
     */
    sealed class OwnerUpdateResult {
        class ok(val ok: OwnerTransferResponse): OwnerUpdateResult()
        class err(val err: OrigynError): OwnerUpdateResult()

    }
    /**
     * type PricingConfigShared = variant {
     *   ask : AskConfigShared;
     *   extensible : CandyShared;
     *   instant : InstantConfigShared;
     *   auction : AuctionConfig;
     * };
     */
    sealed class PricingConfigShared {
        class ask(val ask: AskConfigShared): PricingConfigShared()
        class extensible(val extensible: CandyShared): PricingConfigShared()
        class instant(val instant: InstantConfigShared): PricingConfigShared()
        class auction(val auction: AuctionConfig): PricingConfigShared()

    }
    /**
     * type PricingConfigShared__1 = variant {
     *   ask : AskConfigShared;
     *   extensible : CandyShared;
     *   instant : InstantConfigShared;
     *   auction : AuctionConfig;
     * };
     */
    sealed class PricingConfigShared__1 {
        class ask(val ask: AskConfigShared): PricingConfigShared__1()
        class extensible(val extensible: CandyShared): PricingConfigShared__1()
        class instant(val instant: InstantConfigShared): PricingConfigShared__1()
        class auction(val auction: AuctionConfig): PricingConfigShared__1()

    }
    /**
     * type PropertyShared = record {
     *   value : CandyShared;
     *   name : text;
     *   immutable : bool;
     * };
     */
    class PropertyShared(
        val value: CandyShared,
        val name: String,
        val immutable: Boolean
    )
    /**
     * type RecognizeEscrowResponse = record {
     *   balance : nat;
     *   receipt : EscrowReceipt;
     *   transaction : opt TransactionRecord;
     * };
     */
    class RecognizeEscrowResponse(
        val balance: BigInteger,
        val receipt: EscrowReceipt,
        val transaction: TransactionRecord?
    )
    /**
     * type RejectDescription = record {
     *   token : TokenSpec__1;
     *   token_id : text;
     *   seller : Account;
     *   buyer : Account;
     * };
     */
    class RejectDescription(
        val token: TokenSpec__1,
        val token_id: String,
        val seller: Account,
        val buyer: Account
    )
    /**
     * type Result = variant { ok : ManageSaleResponse; err : OrigynError };
     */
    sealed class Result {
        class ok(val ok: ManageSaleResponse): Result()
        class err(val err: OrigynError): Result()

    }
    /**
     * type SaleInfoRequest = variant {
     *   status : text;
     *   fee_deposit_info : opt Account;
     *   active : opt record { nat; nat };
     *   deposit_info : opt Account;
     *   history : opt record { nat; nat };
     *   escrow_info : EscrowReceipt;
     * };
     */
    sealed class SaleInfoRequest {
        class status(val status: String): SaleInfoRequest()
        class fee_deposit_info(val fee_deposit_info: Account?): SaleInfoRequest()
        class active(
            val natValue_1: BigInteger,
            val natValue_2: BigInteger
        ): SaleInfoRequest()

        class deposit_info(val deposit_info: Account?): SaleInfoRequest()
        class history(
            val natValue_1: BigInteger,
            val natValue_2: BigInteger
        ): SaleInfoRequest()

        class escrow_info(val escrow_info: EscrowReceipt): SaleInfoRequest()

    }
    /**
     * type SaleInfoResponse = variant {
     *   status : opt SaleStatusShared;
     *   fee_deposit_info : SubAccountInfo;
     *   active : record {
     *     eof : bool;
     *     records : vec record { text; opt SaleStatusShared };
     *     count : nat;
     *   };
     *   deposit_info : SubAccountInfo;
     *   history : record {
     *     eof : bool;
     *     records : vec opt SaleStatusShared;
     *     count : nat;
     *   };
     *   escrow_info : SubAccountInfo;
     * };
     */
    sealed class SaleInfoResponse {
        class status(val status: SaleStatusShared?): SaleInfoResponse()
        class fee_deposit_info(val subAccountInfo: SubAccountInfo): SaleInfoResponse()
        class Active(
            val eof: Boolean,
            val records: kotlin.Array<Records>,
            val count: BigInteger
        ): SaleInfoResponse() {
            class Records(
                val textValue: String,
                val saleStatusShared: SaleStatusShared
            )
        }

        class deposit_info(val subAccountInfo: SubAccountInfo): SaleInfoResponse()
        class history(
            val eof: Boolean,
            val records: kotlin.Array<SaleStatusShared?>,
            val count: BigInteger
        ): SaleInfoResponse()

        class escrow_info(val escrow_info: SubAccountInfo): SaleInfoResponse()
    }
    /**
     * type SaleInfoResult = variant { ok : SaleInfoResponse; err : OrigynError };
     */
    sealed class SaleInfoResult {
        class ok(val ok: SaleInfoResponse): SaleInfoResult()
        class err(val err: OrigynError): SaleInfoResult()

    }
    /**
     * type SaleStatusShared = record {
     *   token_id : text;
     *   sale_type : variant { auction : AuctionStateShared };
     *   broker_id : opt principal;
     *   original_broker_id : opt principal;
     *   sale_id : text;
     * };
     */
    class SaleStatusShared(
        val token_id: String,
        val sale_type: SaleType,
        val broker_id: ICPPrincipalApiModel?,
        val original_broker_id: ICPPrincipalApiModel?,
        val sale_id: String
    ) {
        sealed class SaleType {
            class auction(val auction: AuctionStateShared): SaleType()

        }

    }
    /**
     * type SalesConfig = record {
     *   broker_id : opt Account__1;
     *   pricing : PricingConfigShared;
     *   escrow_receipt : opt EscrowReceipt__1;
     * };
     */
    class SalesConfig(
        val broker_id: Account__1?,
        val pricing: PricingConfigShared,
        val escrow_receipt: EscrowReceipt__1?
    )
    /**
     * type ShareWalletRequest = record {
     *   to : Account;
     *   token_id : text;
     *   from : Account;
     * };
     */
    class ShareWalletRequest(
        val to: Account,
        val token_id: String,
        val from: Account
    )
    /**
     * type StableBucketData = record {
     *   "principal" : principal;
     *   allocated_space : nat;
     *   date_added : int;
     *   version : record { nat; nat; nat };
     *   b_gateway : bool;
     *   available_space : nat;
     *   allocations : vec record { record { text; text }; int };
     * };
     */
    class StableBucketData(
        val principal: ICPPrincipalApiModel,
        val allocated_space: BigInteger,
        val date_added: BigInteger,
        val version: Version,
        val b_gateway: Boolean,
        val available_space: BigInteger,
        val allocations: kotlin.Array<Allocations>
    ) {

        class Version(
            val natValue_1: BigInteger,
            val natValue_2: BigInteger,
            val natValue_3: BigInteger,
        )

        class Allocations(
            val allocationValue: AllocationValue,
            val intValue: BigInteger
        ) {
            class AllocationValue(
                val textValue_1: String,
                val textValue_2: String
            )
        }

    }
    /**
     * type StableCollectionData = record {
     *   active_bucket : opt principal;
     *   managers : vec principal;
     *   owner : principal;
     *   metadata : opt CandyShared;
     *   logo : opt text;
     *   name : opt text;
     *   network : opt principal;
     *   available_space : nat;
     *   symbol : opt text;
     *   allocated_storage : nat;
     * };
     */
    class StableCollectionData(
        val active_bucket: ICPPrincipalApiModel?,
        val managers: kotlin.Array<ICPPrincipalApiModel>,
        val owner: ICPPrincipalApiModel,
        val metadata: CandyShared?,
        val logo: String?,
        val name: String?,
        val network: ICPPrincipalApiModel?,
        val available_space: BigInteger,
        val symbol: String?,
        val allocated_storage: BigInteger
    )
    /**
     * type StageChunkArg = record {
     *   content : vec nat8;
     *   token_id : text;
     *   chunk : nat;
     *   filedata : CandyShared;
     *   library_id : text;
     * };
     */
    class StageChunkArg(
        val content: kotlin.Array<UByte>,
        val token_id: String,
        val chunk: BigInteger,
        val filedata: CandyShared,
        val library_id: String
    )
    /**
     * type StageLibraryResponse = record { canister : principal };
     */
    class StageLibraryResponse(
        val canister: ICPPrincipalApiModel
    )
    /**
     * type StageLibraryResult = variant {
     *   ok : StageLibraryResponse;
     *   err : OrigynError;
     * };
     */
    sealed class StageLibraryResult {
        class ok(val ok: StageLibraryResponse): StageLibraryResult()
        class err(val err: OrigynError): StageLibraryResult()

    }
    /**
     * type StakeRecord = record { staker : Account; token_id : text; amount : nat };
     */
    class StakeRecord(
        val staker: Account,
        val token_id: String,
        val amount: BigInteger
    )
    /**
     * type StateSize = record {
     *   sales_balances : nat;
     *   offers : nat;
     *   nft_ledgers : nat;
     *   allocations : nat;
     *   nft_sales : nat;
     *   buckets : nat;
     *   escrow_balances : nat;
     * };
     */
    class StateSize(
        val sales_balances: BigInteger,
        val offers: BigInteger,
        val nft_ledgers: BigInteger,
        val allocations: BigInteger,
        val nft_sales: BigInteger,
        val buckets: BigInteger,
        val escrow_balances: BigInteger
    )
    /**
     * type StorageMetrics = record {
     *   gateway : principal;
     *   available_space : nat;
     *   allocations : vec AllocationRecordStable;
     *   allocated_storage : nat;
     * };
     */
    class StorageMetrics(
        val gateway: ICPPrincipalApiModel,
        val available_space: BigInteger,
        val allocations: kotlin.Array<AllocationRecordStable>,
        val allocated_storage: BigInteger
    )
    /**
     * type StorageMetricsResult = variant { ok : StorageMetrics; err : OrigynError };
     */
    sealed class StorageMetricsResult {
        class ok(val ok: StorageMetrics): StorageMetricsResult()
        class err(val err: OrigynError): StorageMetricsResult()

    }
    /**
     * type StreamingCallbackResponse = record {
     *   token : opt StreamingCallbackToken;
     *   body : vec nat8;
     * };
     */
    class StreamingCallbackResponse(
        val token: StreamingCallbackToken?,
        val body: kotlin.Array<UByte>
    )
    /**
     * type StreamingCallbackToken = record {
     *   key : text;
     *   index : nat;
     *   content_encoding : text;
     * };
     */
    class StreamingCallbackToken(
        val key: String,
        val index: BigInteger,
        val content_encoding: String
    )
    /**
     * type SubAccountInfo = record {
     *   account_id : vec nat8;
     *   "principal" : principal;
     *   account_id_text : text;
     *   account : record { "principal" : principal; sub_account : vec nat8 };
     * };
     */
    class SubAccountInfo(
        val account_id: kotlin.Array<UByte>,
        val principal: ICPPrincipalApiModel,
        val account_id_text: String,
        val account: Account
    ) {
        class Account(
            val principal: ICPPrincipalApiModel,
            val sub_account: kotlin.Array<UByte>
        )
    }
    /**
     * type SupportedStandard = record { url : text; name : text };
     */
    class SupportedStandard(
        val url: String,
        val name: String
    )
    /**
     * type Tip = record {
     *   last_block_index : vec nat8;
     *   hash_tree : vec nat8;
     *   last_block_hash : vec nat8;
     * };
     */
    class Tip(
        val last_block_index: kotlin.Array<UByte>,
        val hash_tree: kotlin.Array<UByte>,
        val last_block_hash: kotlin.Array<UByte>
    )
    /**
     * type TokenIDFilter = record {
     *   filter_type : variant { allow; block };
     *   token_id : text;
     *   tokens : vec record {
     *     token : TokenSpec__1;
     *     min_amount : opt nat;
     *     max_amount : opt nat;
     *   };
     * };
     */
    class TokenIDFilter(
        val filter_type: FilterType,
        val token_id: String,
        val tokens: kotlin.Array<Tokens>
    ) {
        sealed class FilterType {
            object allow: FilterType()
            object block: FilterType()

        }

        class Tokens(
            val token: TokenSpec__1,
            val min_amount: BigInteger?,
            val max_amount: BigInteger?
        )

    }
    /**
     * type TokenMetadata = record {
     *   transferred_at : opt nat64;
     *   transferred_by : opt principal;
     *   owner : opt principal;
     *   operator : opt principal;
     *   approved_at : opt nat64;
     *   approved_by : opt principal;
     *   properties : vec record { text; GenericValue };
     *   is_burned : bool;
     *   token_identifier : nat;
     *   burned_at : opt nat64;
     *   burned_by : opt principal;
     *   minted_at : nat64;
     *   minted_by : principal;
     * };
     */
    class TokenMetadata(
        val transferred_at: ULong?,
        val transferred_by: ICPPrincipalApiModel?,
        val owner: ICPPrincipalApiModel?,
        val operator: ICPPrincipalApiModel?,
        val approved_at: ULong?,
        val approved_by: ICPPrincipalApiModel?,
        val properties: kotlin.Array<Properties>,
        val is_burned: Boolean,
        val token_identifier: BigInteger,
        val burned_at: ULong?,
        val burned_by: ICPPrincipalApiModel?,
        val minted_at: ULong,
        val minted_by: ICPPrincipalApiModel
    ) {
        class Properties(
            val textValue: String,
            val genericValue: GenericValue
        )

    }
    /**
     * type TokenSpec = variant { ic : ICTokenSpec; extensible : CandyShared };
     */
    sealed class TokenSpec {
        class ic(val ic: ICTokenSpec): TokenSpec()
        class extensible(val extensible: CandyShared): TokenSpec()

    }
    /**
     * type TokenSpecFilter = record {
     *   token : TokenSpec__1;
     *   filter_type : variant { allow; block };
     * };
     */
    class TokenSpecFilter(
        val token: TokenSpec__1,
        val filter_type: FilterType
    ) {
        sealed class FilterType {
            object allow: FilterType()
            object block: FilterType()

        }

    }
    /**
     * type TokenSpec__1 = variant { ic : ICTokenSpec__1; extensible : CandyShared };
     */
    sealed class TokenSpec__1 {
        class ic(val ic: ICTokenSpec__1): TokenSpec__1()
        class extensible(val extensible: CandyShared): TokenSpec__1()

    }
    /**
     * type TokenSpec__2 = variant { ic : ICTokenSpec; extensible : CandyShared };
     */
    sealed class TokenSpec__2 {
        class ic(val ic: ICTokenSpec): TokenSpec__2()
        class extensible(val extensible: CandyShared): TokenSpec__2()

    }
    /**
     * type TransactionID = variant {
     *   "nat" : nat;
     *   "text" : text;
     *   extensible : CandyShared;
     * };
     */
    sealed class TransactionID {
        class Nat(val nat: BigInteger): TransactionID()
        class Text(val text: String): TransactionID()
        class extensible(val candyShared: CandyShared): TransactionID()

    }
    /**
     * type TransactionID__1 = variant {
     *   "nat" : nat;
     *   "text" : text;
     *   extensible : CandyShared;
     * };
     */
    sealed class TransactionID__1 {
        class Nat(val nat: BigInteger): TransactionID__1()
        class Text(val text: String): TransactionID__1()
        class extensible(val candyShared: CandyShared): TransactionID__1()

    }
    /**
     * type TransactionRange = record { start : nat; length : nat };
     */
    class TransactionRange(
        val start: BigInteger,
        val length: BigInteger
    )
    /**
     * type TransactionRange__1 = record { start : nat; length : nat };
     */
    class TransactionRange__1(
        val start: BigInteger,
        val length: BigInteger
    )
    /**
     * type TransactionRecord = record {
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
    class TransactionRecord(
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

            class mint(
                val to: Account__1,
                val from: Account__1,
                val sale: Sale?,
                val extensible: CandyShared
            ): TxnType() {
                class Sale(
                    val token: TokenSpec,
                    val amount: BigInteger
                )
            }

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

            class extensible(val extensible: CandyShared): TxnType()
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
    /**
     * type TransferArgs = record {
     *   to : Account__3;
     *   token_id : nat;
     *   memo : opt vec nat8;
     *   from_subaccount : opt vec nat8;
     *   created_at_time : opt nat64;
     * };
     */
    class TransferArgs(
        val to: Account__3,
        val token_id: BigInteger,
        val memo: kotlin.Array<UByte>?,
        val from_subaccount: kotlin.Array<UByte>?,
        val created_at_time: ULong?
    )
    /**
     * type TransferError = variant {
     *   GenericError : record { message : text; error_code : nat };
     *   Duplicate : record { duplicate_of : nat };
     *   NonExistingTokenId;
     *   Unauthorized;
     *   CreatedInFuture : record { ledger_time : nat64 };
     *   TooOld;
     * };
     */
    sealed class TransferError {
        class GenericError(
            val message: String,
            val error_code: BigInteger
        ): TransferError()

        class Duplicate(
            val duplicate_of: BigInteger
        ): TransferError()

        object NonExistingTokenId: TransferError()
        object Unauthorized: TransferError()
        class CreatedInFuture(
            val ledger_time: ULong
        ): TransferError()

        object TooOld: TransferError()

    }
    /**
     * type TransferResultItem = record {
     *   token_id : nat;
     *   transfer_result : variant { Ok : nat; Err : TransferError };
     * };
     */
    class TransferResultItem(
        val token_id: BigInteger,
        val transfer_result: TransferResult
    ) {
        sealed class TransferResult {
            class Ok(val Ok: BigInteger): TransferResult()
            class Err(val Err: TransferError): TransferResult()

        }

    }
    /**
     * type UpdateModeShared = variant {
     *   Set : CandyShared;
     *   Lock : CandyShared;
     *   Next : vec UpdateShared;
     * };
     */
    sealed class UpdateModeShared {
        class Set(val Set: CandyShared): UpdateModeShared()
        class Lock(val Lock: CandyShared): UpdateModeShared()
        class Next(val Next: kotlin.Array<UpdateShared>): UpdateModeShared()

    }
    /**
     * type UpdateRequestShared = record { id : text; update : vec UpdateShared };
     */
    class UpdateRequestShared(
        val id: String,
        val update: kotlin.Array<UpdateShared>
    )
    /**
     * type UpdateSetting = variant {
     *   maxRecordsToArchive : nat;
     *   archiveIndexType : IndexType;
     *   maxArchivePages : nat;
     *   settleToRecords : nat;
     *   archiveCycles : nat;
     *   maxActiveRecords : nat;
     *   maxRecordsInArchiveInstance : nat;
     *   archiveControllers : opt opt vec principal;
     * };
     */
    sealed class UpdateSetting {
        class maxRecordsToArchive(val maxRecordsToArchive: BigInteger): UpdateSetting()
        class archiveIndexType(val archiveIndexType: IndexType): UpdateSetting()
        class maxArchivePages(val maxArchivePages: BigInteger): UpdateSetting()
        class settleToRecords(val settleToRecords: BigInteger): UpdateSetting()
        class archiveCycles(val archiveCycles: BigInteger): UpdateSetting()
        class maxActiveRecords(val maxActiveRecords: BigInteger): UpdateSetting()
        class maxRecordsInArchiveInstance(val maxRecordsInArchiveInstance: BigInteger): UpdateSetting()
        class ArchiveControllers(val archiveControllers: kotlin.Array<ICPPrincipalApiModel>): UpdateSetting()

    }
    /**
     * type UpdateShared = record { mode : UpdateModeShared; name : text };
     */
    class UpdateShared(
        val mode: UpdateModeShared,
        val name: String
    )
    /**
     * type Value = variant {
     *   Int : int;
     *   Map : vec record { text; Value };
     *   Nat : nat;
     *   Blob : vec nat8;
     *   Text : text;
     *   Array : vec Value;
     * };
     */
    sealed class Value {
        class Int(val Int: BigInteger): Value()
        class Map(val Map: kotlin.Array<MapClass>): Value()
        class Nat(val Nat: BigInteger): Value()
        class Blob(val Blob: kotlin.Array<UByte>): Value()
        class Text(val Text: String): Value()
        class Array(val Array: kotlin.Array<Value>): Value()

        class MapClass(
            val key: String,
            val value: Value
        )

    }
    /**
     * type Value__1 = variant {
     *   Int : int;
     *   Map : vec record { text; Value__1 };
     *   Nat : nat;
     *   Blob : vec nat8;
     *   Text : text;
     *   Array : vec Value__1;
     * };
     */
    sealed class Value__1 {
        class Int(val Int: BigInteger): Value__1()
        class Map(val Map: kotlin.Array<MapClass>): Value__1()
        class Nat(val Nat: BigInteger): Value__1()
        class Blob(val Blob: kotlin.Array<UByte>): Value__1()
        class Text(val Text: String): Value__1()
        class Array(val Array: kotlin.Array<Value__1>): Value__1()

        class MapClass(
            val key: String,
            val value: Value__1
        )

    }
    /**
     * type WaitForQuietType = record { max : nat; fade : float64; extension : nat64 };
     */
    class WaitForQuietType(
        val max: BigInteger,
        val fade: Double,
        val extension: ULong
    )
    /**
     * type WithdrawDescription = record {
     *   token : TokenSpec__1;
     *   token_id : text;
     *   seller : Account;
     *   withdraw_to : Account;
     *   buyer : Account;
     *   amount : nat;
     * };
     */
    class WithdrawDescription(
        val token: TokenSpec__1,
        val token_id: String,
        val seller: Account,
        val withdraw_to: Account,
        val buyer: Account,
        val amount: BigInteger
    )
    /**
     * type WithdrawRequest = variant {
     *   reject : RejectDescription;
     *   fee_deposit : FeeDepositWithdrawDescription;
     *   sale : WithdrawDescription;
     *   deposit : DepositWithdrawDescription;
     *   escrow : WithdrawDescription;
     * };
     */
    sealed class WithdrawRequest {
        class reject(val reject: RejectDescription): WithdrawRequest()
        class fee_deposit(val fee_deposit: FeeDepositWithdrawDescription): WithdrawRequest()
        class sale(val sale: WithdrawDescription): WithdrawRequest()
        class deposit(val deposit: DepositWithdrawDescription): WithdrawRequest()
        class escrow(val escrow: WithdrawDescription): WithdrawRequest()

    }
    /**
     * type WithdrawResponse = record {
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

            class mint(
                val to: Account__1,
                val from: Account__1,
                val sale: Sale?,
                val extensible: CandyShared
            ): TxnType() {
                class Sale(
                    val token: TokenSpec,
                    val amount: BigInteger
                )
            }

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

            class extensible(val extensible: CandyShared): TxnType()
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
    /**
     * type canister_status = record {
     *   status : variant { stopped; stopping; running };
     *   memory_size : nat;
     *   cycles : nat;
     *   settings : definite_canister_settings;
     *   module_hash : opt vec nat8;
     * };
     */
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
    /**
     * type definite_canister_settings = record {
     *   freezing_threshold : nat;
     *   controllers : opt vec principal;
     *   memory_allocation : nat;
     *   compute_allocation : nat;
     * };
     */
    class definite_canister_settings(
        val freezing_threshold: BigInteger,
        val controllers: kotlin.Array<ICPPrincipalApiModel>?,
        val memory_allocation: BigInteger,
        val compute_allocation: BigInteger
    )
}