/*
package com.bity.icp_kotlin_kit.domain.generated_file

import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
import com.bity.icp_kotlin_kit.domain.request.PollingValues

*/
/**
 * type ApprovalResult = vec record {
 *   token_id : nat;
 *   approval_result : variant { Ok : nat; Err : ApprovalError };
 * };
 *//*

*/
/* typealias ApprovalResult = kotlin.Array<ApprovalResult>class ApprovalResult(
    val token_id: BigInteger,
    val null: approval_result
) *//*

*/
/**
 * type AskConfigShared = opt AskFeatureArray;
 *//*

// typealias AskConfigShared = AskFeatureArray?

*/
/**
 * type AskFeatureArray = vec AskFeature;
 *//*

// typealias AskFeatureArray = kotlin.Array<AskFeature>

*/
/**
 * type AskSubscribeResponse = bool;
 *//*

typealias AskSubscribeResponse = Boolean

*/
/**
 * type BidConfigShared = opt vec BidFeature;
 *//*

// typealias BidConfigShared = kotlin.Array<BidFeature>?

*/
/**
 * type Caller = opt principal;
 *//*

typealias Caller = ICPPrincipalApiModel?

*/
/**
 * type CollectionMetadata = vec record { text; Value };
 *//*

*/
/* typealias CollectionMetadata = kotlin.Array<CollectionMetadata>class CollectionMetadata(
    val textValue: String,
    val value: Value
) *//*

*/
/**
 * type DistributeSaleResponse = vec Result;
 *//*

typealias DistributeSaleResponse = kotlin.Array<Result>

*/
/**
 * type EXTMemo = vec nat8;
 *//*

typealias EXTMemo = kotlin.Array<UByte>

*/
/**
 * type EXTSubAccount = vec nat8;
 *//*

// typealias EXTSubAccount = kotlin.Array<UByte>

*/
/**
 * type FeeAccountsParams = vec FeeName;
 *//*

// typealias FeeAccountsParams = kotlin.Array<FeeName>

*/
/**
 * type GetArchivesResult = vec GetArchivesResultItem;
 *//*

// typealias GetArchivesResult = kotlin.Array<GetArchivesResultItem>

*/
/**
 * type InstantConfigShared = opt vec InstantFeature;
 *//*

// typealias InstantConfigShared = kotlin.Array<InstantFeature>?

*/
/**
 * type NFTUpdateResponse = bool;
 *//*

typealias NFTUpdateResponse = Boolean

*/
/**
 * type StableEscrowBalances = vec record {
 *   Account;
 *   Account;
 *   text;
 *   EscrowRecord__1;
 * };
 *//*

*/
/* typealias StableEscrowBalances = kotlin.Array<StableEscrowBalances>class StableEscrowBalances(
    val account: Account,
    val account: Account,
    val textValue: String,
    val escrowRecord__1: EscrowRecord__1
) *//*

*/
/**
 * type StableNftLedger = vec record { text; TransactionRecord };
 *//*

*/
/*typealias StableNftLedger = kotlin.Array<StableNftLedger>class StableNftLedger(
    val textValue: String,
    val transactionRecord: TransactionRecord
)*//*

*/
/**
 * type StableSalesBalances = vec record {
 *   Account;
 *   Account;
 *   text;
 *   EscrowRecord__1;
 * };
 *//*

*/
/*typealias StableSalesBalances = kotlin.Array<StableSalesBalances>class StableSalesBalances(
    val account: Account,
    val account: Account,
    val textValue: String,
    val escrowRecord__1: EscrowRecord__1
)*//*

*/
/**
 * type Subaccount = vec nat8;
 *//*

// typealias Subaccount = kotlin.Array<UByte>

*/
/**
 * type TransferResult = vec opt TransferResultItem;
 *//*

// typealias TransferResult = kotlin.Array<TransferResultItem?>

*/
/**
 * type canister_id = principal;
 *//*

typealias canister_id = ICPPrincipalApiModel
object OrigynNFT {

    */
/**
     * type Account = variant {
     *   account_id : text;
     *   "principal" : principal;
     *   extensible : CandyShared;
     *   account : record { owner : principal; sub_account : opt vec nat8 };
     * };
     *//*

    sealed class Account {
        class account_id(val account_id: String): Account()
        class principal(val principal: ICPPrincipalApiModel): Account()
        class extensible(val extensible: CandyShared): Account()
        class account(
            val owner: ICPPrincipalApiModel,
            val sub_account: kotlin.Array<UByte>?
        ): Account()
    }
    */
/**
     * type Account__1 = variant {
     *   account_id : text;
     *   "principal" : principal;
     *   extensible : CandyShared;
     *   account : record { owner : principal; sub_account : opt vec nat8 };
     * };
     *//*

    sealed class Account__1 {
        class account_id(val account_id: String): Account__1()
        class principal(val principal: ICPPrincipalApiModel): Account__1()
        class extensible(val extensible: CandyShared): Account__1()
        class account(
            val owner: ICPPrincipalApiModel,
            val sub_account: kotlin.Array<UByte>?
        ): Account__1()
    }
    */
/**
     * type Account__2 = variant {
     *   account_id : text;
     *   "principal" : principal;
     *   extensible : CandyShared;
     *   account : record { owner : principal; sub_account : opt vec nat8 };
     * };
     *//*

    sealed class Account__2 {
        class Account_id(val account_id: String): Account__2()
        class "principal"(val "principal": ICPPrincipalApiModel): Account__2()
        class extensible(val extensible: CandyShared): Account__2()
        class account(
            val owner: ICPPrincipalApiModel,
            val sub_account: kotlin.Array<UByte>?
        ): Account__2()
    }
    */
/**
     * type Account__3 = record { owner : principal; subaccount : opt Subaccount };
     *//*

    class Account__3(
        val owner: ICPPrincipalApiModel,
        val subaccount: Subaccount?
    )
    */
/**
     * type AllocationRecordStable = record {
     *   allocated_space : nat;
     *   token_id : text;
     *   available_space : nat;
     *   canister : principal;
     *   chunks : vec nat;
     *   library_id : text;
     * };
     *//*

    class AllocationRecordStable(
        val allocated_space: BigInteger,
        val token_id: String,
        val available_space: BigInteger,
        val canister: ICPPrincipalApiModel,
        val chunks: kotlin.Array<BigInteger>,
        val library_id: String
    )
    */
/**
     * type ApprovalArgs = record {
     *   memo : opt vec nat8;
     *   from_subaccount : opt vec nat8;
     *   created_at_time : opt nat64;
     *   expires_at : opt nat64;
     *   spender : Account__3;
     * };
     *//*

    class ApprovalArgs(
        val memo: kotlin.Array<UByte>?,
        val from_subaccount: kotlin.Array<UByte>?,
        val created_at_time: ULong?,
        val expires_at: ULong?,
        val account__3: Account__3
    )
    */
/**
     * type ApprovalError = variant {
     *   GenericError : record { message : text; error_code : nat };
     *   CreatexInFuture : record { ledger_time : nat64 };
     *   NonExistingTokenId;
     *   Unauthorized;
     *   TooOld;
     * };
     *//*

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
    */
/**
     * type ArchivedTransactionResponse = record {
     *   args : vec TransactionRange__1;
     *   callback : GetTransactionsFn;
     * };
     *//*

    class ArchivedTransactionResponse(
        val args: kotlin.Array<TransactionRange__1>,
        val getTransactionsFn: GetTransactionsFn
    )
    */
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
     *//*

    sealed class AskFeature {
        class Kyc(val kyc: ICPPrincipalApiModel): AskFeature()
        class Start_price(val start_price: BigInteger): AskFeature()
        class token(val token: TokenSpec): AskFeature()
        class Fee_schema(val fee_schema: String): AskFeature()
        class Notify(val notify: kotlin.Array<ICPPrincipalApiModel>): AskFeature()
        class wait_for_quiet(val wait_for_quiet: WaitForQuietType): AskFeature()
        class Reserve(val reserve: BigInteger): AskFeature()
        class Start_date(val start_date: BigInteger): AskFeature()
        class min_increase(val min_increase: MinIncreaseType): AskFeature()
        class Allow_list(val allow_list: kotlin.Array<ICPPrincipalApiModel>): AskFeature()
        class Buy_now(val buy_now: BigInteger): AskFeature()
        class fee_accounts(val fee_accounts: FeeAccountsParams): AskFeature()
        class nifty_settlement(val nifty_settlement: NiftySettlementType): AskFeature()
        object atomic: AskFeature()
        class dutch(val dutch: DutchParams): AskFeature()
        class ending(val ending: EndingType): AskFeature()

    }
    */
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
     *//*

    class AuctionConfig(
        val start_price: BigInteger,
        val tokenSpec: TokenSpec,
        val reserve: BigInteger?,
        val start_date: BigInteger,
        val minIncreaseType: MinIncreaseType,
        val allow_list: kotlin.Array<ICPPrincipalApiModel>?,
        val buy_now: BigInteger?,
        val null: ending
    )
    */
/**
     * type BalanceResponse = record {
     *   nfts : vec text;
     *   offers : vec EscrowRecord__1;
     *   sales : vec EscrowRecord__1;
     *   stake : vec StakeRecord;
     *   multi_canister : opt vec principal;
     *   escrow : vec EscrowRecord__1;
     * };
     *//*

    class BalanceResponse(
        val nfts: kotlin.Array<String>,
        val offers: kotlin.Array<EscrowRecord__1>,
        val sales: kotlin.Array<EscrowRecord__1>,
        val stake: kotlin.Array<StakeRecord>,
        val multi_canister: kotlin.Array<ICPPrincipalApiModel>?,
        val escrow: kotlin.Array<EscrowRecord__1>
    )
    */
/**
     * type BalanceResult = variant { ok : BalanceResponse; err : OrigynError };
     *//*

    sealed class BalanceResult {
        class ok(val ok: BalanceResponse): BalanceResult()
        class err(val err: OrigynError): BalanceResult()

    }
    */
/**
     * type BearerResult = variant { ok : Account; err : OrigynError };
     *//*

    sealed class BearerResult {
        class ok(val ok: Account): BearerResult()
        class err(val err: OrigynError): BearerResult()

    }
    */
/**
     * type BidFeature = variant {
     *   fee_schema : text;
     *   broker : Account__1;
     *   fee_accounts : FeeAccountsParams;
     * };
     *//*

    sealed class BidFeature {
        class Fee_schema(val fee_schema: String): BidFeature()
        class broker(val broker: Account__1): BidFeature()
        class fee_accounts(val fee_accounts: FeeAccountsParams): BidFeature()

    }
    */
/**
     * type BidRequest = record {
     *   config : BidConfigShared;
     *   escrow_record : EscrowRecord;
     * };
     *//*

    class BidRequest(
        val bidConfigShared: BidConfigShared,
        val escrowRecord: EscrowRecord
    )
    */
/**
     * type BlockType = record { url : text; block_type : text };
     *//*

    class BlockType(
        val url: String,
        val block_type: String
    )
    */
/**
     * type CanisterLogFeature = variant {
     *   filterMessageByContains;
     *   filterMessageByRegex;
     * };
     *//*

    sealed class CanisterLogFeature {
        object filterMessageByContains: CanisterLogFeature()
        object filterMessageByRegex: CanisterLogFeature()

    }
    */
/**
     * type CanisterLogMessages = record {
     *   data : vec LogMessagesData;
     *   lastAnalyzedMessageTimeNanos : opt Nanos;
     * };
     *//*

    class CanisterLogMessages(
        val data: kotlin.Array<LogMessagesData>,
        val lastAnalyzedMessageTimeNanos: Nanos?
    )
    */
/**
     * type CanisterLogRequest = variant {
     *   getMessagesInfo;
     *   getMessages : GetLogMessagesParameters;
     *   getLatestMessages : GetLatestLogMessagesParameters;
     * };
     *//*

    sealed class CanisterLogRequest {
        object getMessagesInfo: CanisterLogRequest()
        class getMessages(val getMessages: GetLogMessagesParameters): CanisterLogRequest()
        class getLatestMessages(val getLatestMessages: GetLatestLogMessagesParameters): CanisterLogRequest()

    }
    */
/**
     * type CanisterLogResponse = variant {
     *   messagesInfo : CanisterLogMessagesInfo;
     *   messages : CanisterLogMessages;
     * };
     *//*

    sealed class CanisterLogResponse {
        class messagesInfo(val messagesInfo: CanisterLogMessagesInfo): CanisterLogResponse()
        class messages(val messages: CanisterLogMessages): CanisterLogResponse()

    }
    */
/**
     * type CanisterMetrics = record { data : CanisterMetricsData };
     *//*

    class CanisterMetrics(
        val canisterMetricsData: CanisterMetricsData
    )
    */
/**
     * type CanisterMetricsData = variant {
     *   hourly : vec HourlyMetricsData;
     *   daily : vec DailyMetricsData;
     * };
     *//*

    sealed class CanisterMetricsData {
        class Hourly(val hourly: kotlin.Array<HourlyMetricsData>): CanisterMetricsData()
        class Daily(val daily: kotlin.Array<DailyMetricsData>): CanisterMetricsData()

    }
    */
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
     *//*

    sealed class ChunkContent {
        class remote(
            val chunkRequest: ChunkRequest,
            val canister: ICPPrincipalApiModel
        ): ChunkContent()

        class chunk(
            val total_chunks: BigInteger,
            val content: kotlin.Array<UByte>,
            val allocationRecordStable: AllocationRecordStable,
            val current_chunk: BigInteger?
        ): ChunkContent()
    }
    */
/**
     * type ChunkRequest = record {
     *   token_id : text;
     *   chunk : opt nat;
     *   library_id : text;
     * };
     *//*

    class ChunkRequest(
        val token_id: String,
        val chunk: BigInteger?,
        val library_id: String
    )
    */
/**
     * type ChunkResult = variant { ok : ChunkContent; err : OrigynError };
     *//*

    sealed class ChunkResult {
        class ok(val ok: ChunkContent): ChunkResult()
        class err(val err: OrigynError): ChunkResult()

    }
    */
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
     *//*

    class CollectionInfo(
        val multi_canister_count: BigInteger?,
        val managers: kotlin.Array<ICPPrincipalApiModel>?,
        val owner: ICPPrincipalApiModel?,
        val candyShared: CandyShared?,
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
            val natValue: BigInteger?
        )

    }
    */
/**
     * type CollectionResult = variant { ok : CollectionInfo; err : OrigynError };
     *//*

    sealed class CollectionResult {
        class ok(val ok: CollectionInfo): CollectionResult()
        class err(val err: OrigynError): CollectionResult()

    }
    */
/**
     * type DIP721Metadata = record {
     *   logo : opt text;
     *   name : opt text;
     *   created_at : nat64;
     *   upgraded_at : nat64;
     *   custodians : vec principal;
     *   symbol : opt text;
     * };
     *//*

    class DIP721Metadata(
        val logo: String?,
        val name: String?,
        val created_at: ULong,
        val upgraded_at: ULong,
        val custodians: kotlin.Array<ICPPrincipalApiModel>,
        val symbol: String?
    )
    */
/**
     * type DIP721NatResult = variant { Ok : nat; Err : NftError };
     *//*

    sealed class DIP721NatResult {
        class Ok(val ok: BigInteger): DIP721NatResult()
        class Err(val err: NftError): DIP721NatResult()

    }
    */
/**
     * type DIP721Stats = record {
     *   cycles : nat;
     *   total_transactions : nat;
     *   total_unique_holders : nat;
     *   total_supply : nat;
     * };
     *//*

    class DIP721Stats(
        val cycles: BigInteger,
        val total_transactions: BigInteger,
        val total_unique_holders: BigInteger,
        val total_supply: BigInteger
    )
    */
/**
     * type DIP721SupportedInterface = variant {
     *   Burn;
     *   Mint;
     *   Approval;
     *   TransactionHistory;
     * };
     *//*

    sealed class DIP721SupportedInterface {
        object Burn: DIP721SupportedInterface()
        object Mint: DIP721SupportedInterface()
        object Approval: DIP721SupportedInterface()
        object TransactionHistory: DIP721SupportedInterface()

    }
    */
/**
     * type DIP721TokenMetadata = variant { Ok : TokenMetadata; Err : NftError };
     *//*

    sealed class DIP721TokenMetadata {
        class Ok(val ok: TokenMetadata): DIP721TokenMetadata()
        class Err(val err: NftError): DIP721TokenMetadata()

    }
    */
/**
     * type DIP721TokensListMetadata = variant { Ok : vec nat; Err : NftError };
     *//*

    sealed class DIP721TokensListMetadata {
        class Ok(val ok: kotlin.Array<BigInteger>): DIP721TokensListMetadata()
        class Err(val err: NftError): DIP721TokensListMetadata()

    }
    */
/**
     * type DIP721TokensMetadata = variant { Ok : vec TokenMetadata; Err : NftError };
     *//*

    sealed class DIP721TokensMetadata {
        class Ok(val ok: kotlin.Array<TokenMetadata>): DIP721TokensMetadata()
        class Err(val err: NftError): DIP721TokensMetadata()

    }
    */
/**
     * type DailyMetricsData = record {
     *   updateCalls : nat64;
     *   canisterHeapMemorySize : NumericEntity;
     *   canisterCycles : NumericEntity;
     *   canisterMemorySize : NumericEntity;
     *   timeMillis : int;
     * };
     *//*

    class DailyMetricsData(
        val updateCalls: ULong,
        val numericEntity: NumericEntity,
        val numericEntity: NumericEntity,
        val numericEntity: NumericEntity,
        val timeMillis: BigInteger
    )
    */
/**
     * type DataCertificate = record { certificate : vec nat8; hash_tree : vec nat8 };
     *//*

    class DataCertificate(
        val certificate: kotlin.Array<UByte>,
        val hash_tree: kotlin.Array<UByte>
    )
    */
/**
     * type DepositDetail = record {
     *   token : TokenSpec__1;
     *   trx_id : opt TransactionID__1;
     *   seller : Account;
     *   buyer : Account;
     *   amount : nat;
     *   sale_id : opt text;
     * };
     *//*

    class DepositDetail(
        val tokenSpec__1: TokenSpec__1,
        val transactionID__1: TransactionID__1?,
        val account: Account,
        val account: Account,
        val amount: BigInteger,
        val sale_id: String?
    )
    */
/**
     * type DepositWithdrawDescription = record {
     *   token : TokenSpec__1;
     *   withdraw_to : Account;
     *   buyer : Account;
     *   amount : nat;
     * };
     *//*

    class DepositWithdrawDescription(
        val tokenSpec__1: TokenSpec__1,
        val account: Account,
        val account: Account,
        val amount: BigInteger
    )
    */
/**
     * type DistributeSaleRequest = record { seller : opt Account };
     *//*

    class DistributeSaleRequest(
        val account: Account?
    )
    */
/**
     * type DutchParams = record {
     *   time_unit : variant { day : nat; hour : nat; minute : nat };
     *   decay_type : variant { flat : nat; percent : float64 };
     * };
     *//*

    class DutchParams(
        val time_unit: TimeUnit,
        val decay_type: DecayType
    ) {
        sealed class TimeUnit {
            class day(val day: BigInteger): time_unit()
            class hour(val hour: BigInteger): time_unit()
            class minute(val minute: BigInteger): time_unit()
        }

        sealed class DecayType {
            class flat(val flat: BigInteger): decay_type()
            class percent(val percent: Double): decay_type()
        }
    }
    */
/**
     * type EXTBalanceRequest = record { token : EXTTokenIdentifier; user : EXTUser };
     *//*

    class EXTBalanceRequest(
        val eXTTokenIdentifier: EXTTokenIdentifier,
        val eXTUser: EXTUser
    )
    */
/**
     * type EXTBalanceResult = variant { ok : EXTBalance; err : EXTCommonError };
     *//*

    sealed class EXTBalanceResult {
        class ok(val ok: EXTBalance): EXTBalanceResult()
        class err(val err: EXTCommonError): EXTBalanceResult()

    }
    */
/**
     * type EXTBearerResult = variant {
     *   ok : EXTAccountIdentifier;
     *   err : EXTCommonError;
     * };
     *//*

    sealed class EXTBearerResult {
        class ok(val ok: EXTAccountIdentifier): EXTBearerResult()
        class err(val err: EXTCommonError): EXTBearerResult()

    }
    */
/**
     * type EXTCommonError = variant {
     *   InvalidToken : EXTTokenIdentifier;
     *   Other : text;
     * };
     *//*

    sealed class EXTCommonError {
        class InvalidToken(val invalidToken: EXTTokenIdentifier): EXTCommonError()
        class Other(val other: String): EXTCommonError()

    }
    */
/**
     * type EXTMetadataResult = variant { ok : EXTMetadata; err : EXTCommonError };
     *//*

    sealed class EXTMetadataResult {
        class ok(val ok: EXTMetadata): EXTMetadataResult()
        class err(val err: EXTCommonError): EXTMetadataResult()

    }
    */
/**
     * type EXTTokensResult = variant {
     *   ok : vec EXTTokensResponse;
     *   err : EXTCommonError;
     * };
     *//*

    sealed class EXTTokensResult {
        class Ok(val ok: kotlin.Array<EXTTokensResponse>): EXTTokensResult()
        class err(val err: EXTCommonError): EXTTokensResult()

    }
    */
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
     *//*

    sealed class EXTTransferResponse {
        class ok(val ok: EXTBalance): EXTTransferResponse()
        class null(val null: err): EXTTransferResponse()

    }
    */
/**
     * type EXTUser = variant { "principal" : principal; address : text };
     *//*

    sealed class EXTUser {
        class "principal"(val "principal": ICPPrincipalApiModel): EXTUser()
        class Address(val address: String): EXTUser()

    }
    */
/**
     * type EndingType = variant { date : int; timeout : nat };
     *//*

    sealed class EndingType {
        class Date(val date: BigInteger): EndingType()
        class Timeout(val timeout: BigInteger): EndingType()

    }
    */
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
     *//*

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
    */
/**
     * type EscrowReceipt = record {
     *   token : TokenSpec;
     *   token_id : text;
     *   seller : Account__1;
     *   buyer : Account__1;
     *   amount : nat;
     * };
     *//*

    class EscrowReceipt(
        val tokenSpec: TokenSpec,
        val token_id: String,
        val account__1: Account__1,
        val account__1: Account__1,
        val amount: BigInteger
    )
    */
/**
     * type EscrowReceipt__1 = record {
     *   token : TokenSpec;
     *   token_id : text;
     *   seller : Account__1;
     *   buyer : Account__1;
     *   amount : nat;
     * };
     *//*

    class EscrowReceipt__1(
        val tokenSpec: TokenSpec,
        val token_id: String,
        val account__1: Account__1,
        val account__1: Account__1,
        val amount: BigInteger
    )
    */
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
     *//*

    class EscrowRecord(
        val tokenSpec__2: TokenSpec__2,
        val token_id: String,
        val account__2: Account__2,
        val lock_to_date: BigInteger?,
        val account__2: Account__2,
        val amount: BigInteger,
        val sale_id: String?,
        val account_hash: kotlin.Array<UByte>?
    )
    */
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
     *//*

    class EscrowRecord__1(
        val tokenSpec__1: TokenSpec__1,
        val token_id: String,
        val account: Account,
        val lock_to_date: BigInteger?,
        val account: Account,
        val amount: BigInteger,
        val sale_id: String?,
        val account_hash: kotlin.Array<UByte>?
    )
    */
/**
     * type EscrowRequest = record {
     *   token_id : text;
     *   deposit : DepositDetail;
     *   lock_to_date : opt int;
     * };
     *//*

    class EscrowRequest(
        val token_id: String,
        val depositDetail: DepositDetail,
        val lock_to_date: BigInteger?
    )
    */
/**
     * type EscrowResponse = record {
     *   balance : nat;
     *   receipt : EscrowReceipt;
     *   transaction : TransactionRecord;
     * };
     *//*

    class EscrowResponse(
        val balance: BigInteger,
        val escrowReceipt: EscrowReceipt,
        val transactionRecord: TransactionRecord
    )
    */
/**
     * type FeeDepositRequest = record { token : TokenSpec__1; account : Account };
     *//*

    class FeeDepositRequest(
        val tokenSpec__1: TokenSpec__1,
        val account: Account
    )
    */
/**
     * type FeeDepositResponse = record {
     *   balance : nat;
     *   transaction : TransactionRecord;
     * };
     *//*

    class FeeDepositResponse(
        val balance: BigInteger,
        val transactionRecord: TransactionRecord
    )
    */
/**
     * type FeeDepositWithdrawDescription = record {
     *   status : variant { locked : record { sale_id : text }; unlocked };
     *   token : TokenSpec__1;
     *   withdraw_to : Account;
     *   account : Account;
     *   amount : nat;
     * };
     *//*

    class FeeDepositWithdrawDescription(
        val null: status,
        val tokenSpec__1: TokenSpec__1,
        val account: Account,
        val account: Account,
        val amount: BigInteger
    )
    */
/**
     * type GetArchivesArgs = record { from : opt principal };
     *//*

    class GetArchivesArgs(
        val from: ICPPrincipalApiModel?
    )
    */
/**
     * type GetArchivesResultItem = record {
     *   end : nat;
     *   canister_id : principal;
     *   start : nat;
     * };
     *//*

    class GetArchivesResultItem(
        val end: BigInteger,
        val canister_id: ICPPrincipalApiModel,
        val start: BigInteger
    )
    */
/**
     * type GetMetricsParameters = record {
     *   dateToMillis : nat;
     *   granularity : MetricsGranularity;
     *   dateFromMillis : nat;
     * };
     *//*

    class GetMetricsParameters(
        val dateToMillis: BigInteger,
        val metricsGranularity: MetricsGranularity,
        val dateFromMillis: BigInteger
    )
    */
/**
     * type GetTransactionsResult = record {
     *   log_length : nat;
     *   blocks : vec record { id : nat; block : Value__1 };
     *   archived_blocks : vec ArchivedTransactionResponse;
     * };
     *//*

    class GetTransactionsResult(
        val log_length: BigInteger,
        val blocks: kotlin.Array<Blocks>,
        val archived_blocks: kotlin.Array<ArchivedTransactionResponse>
    ) {
        class Blocks(
            val id: BigInteger,
            val value__1: Value__1
        )

    }
    */
/**
     * type GetTransactionsResult__1 = record {
     *   log_length : nat;
     *   blocks : vec record { id : nat; block : Value__1 };
     *   archived_blocks : vec ArchivedTransactionResponse;
     * };
     *//*

    class GetTransactionsResult__1(
        val log_length: BigInteger,
        val blocks: kotlin.Array<Blocks>,
        val archived_blocks: kotlin.Array<ArchivedTransactionResponse>
    ) {
        class Blocks(
            val id: BigInteger,
            val value__1: Value__1
        )

    }
    */
/**
     * type GovernanceRequest = variant {
     *   update_system_var : record { key : text; val : CandyShared; token_id : text };
     *   clear_shared_wallets : text;
     * };
     *//*

    sealed class GovernanceRequest {
        class update_system_var(
            val key: String,
            val candyShared: CandyShared,
            val token_id: String
        ): GovernanceRequest()

        class Clear_shared_wallets(val clear_shared_wallets: String): GovernanceRequest()

    }
    */
/**
     * type GovernanceResult = variant { ok : GovernanceResponse; err : OrigynError };
     *//*

    sealed class GovernanceResult {
        class ok(val ok: GovernanceResponse): GovernanceResult()
        class err(val err: OrigynError): GovernanceResult()

    }
    */
/**
     * type HeaderField = record { text; text };
     *//*

    class HeaderField(
        val textValue: String,
        val textValue: String
    )
    */
/**
     * type HistoryResult = variant { ok : vec TransactionRecord; err : OrigynError };
     *//*

    sealed class HistoryResult {
        class Ok(val ok: kotlin.Array<TransactionRecord>): HistoryResult()
        class err(val err: OrigynError): HistoryResult()

    }
    */
/**
     * type HourlyMetricsData = record {
     *   updateCalls : UpdateCallsAggregatedData;
     *   canisterHeapMemorySize : CanisterHeapMemoryAggregatedData;
     *   canisterCycles : CanisterCyclesAggregatedData;
     *   canisterMemorySize : CanisterMemoryAggregatedData;
     *   timeMillis : int;
     * };
     *//*

    class HourlyMetricsData(
        val updateCallsAggregatedData: UpdateCallsAggregatedData,
        val canisterHeapMemoryAggregatedData: CanisterHeapMemoryAggregatedData,
        val canisterCyclesAggregatedData: CanisterCyclesAggregatedData,
        val canisterMemoryAggregatedData: CanisterMemoryAggregatedData,
        val timeMillis: BigInteger
    )
    */
/**
     * type HttpRequest = record {
     *   url : text;
     *   method : text;
     *   body : vec nat8;
     *   headers : vec HeaderField;
     * };
     *//*

    class HttpRequest(
        val url: String,
        val method: String,
        val body: kotlin.Array<UByte>,
        val headers: kotlin.Array<HeaderField>
    )
    */
/**
     * type ICTokenSpec = record {
     *   id : opt nat;
     *   fee : opt nat;
     *   decimals : nat;
     *   canister : principal;
     *   standard : variant { ICRC1; EXTFungible; DIP20; Other : CandyShared; Ledger };
     *   symbol : text;
     * };
     *//*

    class ICTokenSpec(
        val id: BigInteger?,
        val fee: BigInteger?,
        val decimals: BigInteger,
        val canister: ICPPrincipalApiModel,
        val null: standard,
        val symbol: String
    )
    */
/**
     * type ICTokenSpec__1 = record {
     *   id : opt nat;
     *   fee : opt nat;
     *   decimals : nat;
     *   canister : principal;
     *   standard : variant { ICRC1; EXTFungible; DIP20; Other : CandyShared; Ledger };
     *   symbol : text;
     * };
     *//*

    class ICTokenSpec__1(
        val id: BigInteger?,
        val fee: BigInteger?,
        val decimals: BigInteger,
        val canister: ICPPrincipalApiModel,
        val null: standard,
        val symbol: String
    )
    */
/**
     * type IndexType = variant { Stable; StableTyped; Managed };
     *//*

    sealed class IndexType {
        object Stable: IndexType()
        object StableTyped: IndexType()
        object Managed: IndexType()

    }
    */
/**
     * type InstantFeature = variant {
     *   fee_schema : text;
     *   fee_accounts : FeeAccountsParams;
     * };
     *//*

    sealed class InstantFeature {
        class Fee_schema(val fee_schema: String): InstantFeature()
        class fee_accounts(val fee_accounts: FeeAccountsParams): InstantFeature()

    }
    */
/**
     * type LogMessagesData = record {
     *   data : Data;
     *   timeNanos : Nanos;
     *   message : text;
     *   caller : Caller;
     * };
     *//*

    class LogMessagesData(
        val data: Data,
        val nanos: Nanos,
        val message: String,
        val caller: Caller
    )
    */
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
     *//*

    sealed class ManageSaleRequest {
        class bid(val bid: BidRequest): ManageSaleRequest()
        class escrow_deposit(val escrow_deposit: EscrowRequest): ManageSaleRequest()
        class fee_deposit(val fee_deposit: FeeDepositRequest): ManageSaleRequest()
        class recognize_escrow(val recognize_escrow: EscrowRequest): ManageSaleRequest()
        class withdraw(val withdraw: WithdrawRequest): ManageSaleRequest()
        class ask_subscribe(val ask_subscribe: AskSubscribeRequest): ManageSaleRequest()
        class End_sale(val end_sale: String): ManageSaleRequest()
        class refresh_offers(val refresh_offers: Account?): ManageSaleRequest()
        class distribute_sale(val distribute_sale: DistributeSaleRequest): ManageSaleRequest()
        class Open_sale(val open_sale: String): ManageSaleRequest()

    }
    */
/**
     * type ManageSaleResult = variant { ok : ManageSaleResponse; err : OrigynError };
     *//*

    sealed class ManageSaleResult {
        class ok(val ok: ManageSaleResponse): ManageSaleResult()
        class err(val err: OrigynError): ManageSaleResult()

    }
    */
/**
     * type ManageStorageRequest = variant {
     *   add_storage_canisters : vec record {
     *     principal;
     *     nat;
     *     record { nat; nat; nat };
     *   };
     *   configure_storage : variant { stableBtree : opt nat; heap : opt nat };
     * };
     *//*

    sealed class ManageStorageRequest {
        class Add_storage_canisters(val add_storage_canisters: kotlin.Array<Add, Storage, Canisters>): ManageStorageRequest()
        class null(val null: configure_storage): ManageStorageRequest()

        class Add, Storage, Canisters(
        val icpPrincipalApiModel: ICPPrincipalApiModel,
        val natValue: BigInteger,
        val null: TODO()
        )

    }
    */
/**
     * type ManageStorageResponse = variant {
     *   add_storage_canisters : record { nat; nat };
     *   configure_storage : record { nat; nat };
     * };
     *//*

    sealed class ManageStorageResponse {
        class add_storage_canisters(
            val natValue: BigInteger,
            val natValue: BigInteger
        ): ManageStorageResponse()

        class configure_storage(
            val natValue: BigInteger,
            val natValue: BigInteger
        ): ManageStorageResponse()
    }
    */
/**
     * type ManageStorageResult = variant {
     *   ok : ManageStorageResponse;
     *   err : OrigynError;
     * };
     *//*

    sealed class ManageStorageResult {
        class ok(val ok: ManageStorageResponse): ManageStorageResult()
        class err(val err: OrigynError): ManageStorageResult()

    }
    */
/**
     * type MarketTransferRequest = record {
     *   token_id : text;
     *   sales_config : SalesConfig;
     * };
     *//*

    class MarketTransferRequest(
        val token_id: String,
        val salesConfig: SalesConfig
    )
    */
/**
     * type MarketTransferResult = variant {
     *   ok : MarketTransferRequestReponse;
     *   err : OrigynError;
     * };
     *//*

    sealed class MarketTransferResult {
        class ok(val ok: MarketTransferRequestReponse): MarketTransferResult()
        class err(val err: OrigynError): MarketTransferResult()

    }
    */
/**
     * type MetricsGranularity = variant { hourly; daily };
     *//*

    sealed class MetricsGranularity {
        object hourly: MetricsGranularity()
        object daily: MetricsGranularity()

    }
    */
/**
     * type MinIncreaseType = variant { amount : nat; percentage : float64 };
     *//*

    sealed class MinIncreaseType {
        class Amount(val amount: BigInteger): MinIncreaseType()
        class Percentage(val percentage: Double): MinIncreaseType()

    }
    */
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
     *//*

    class NFTBackupChunk(
        val stableSalesBalances: StableSalesBalances,
        val stableOffers: StableOffers,
        val stableCollectionData: StableCollectionData,
        val stableNftLedger: StableNftLedger,
        val canister: ICPPrincipalApiModel,
        val allocations: kotlin.Array<Allocations>,
        val nft_sales: kotlin.Array<Nft, Sales>,
        val buckets: kotlin.Array<Buckets>,
        val stableEscrowBalances: StableEscrowBalances
    ) {
        class Allocations(
            val null: TODO(),
        val allocationRecordStable: AllocationRecordStable
        )
        ,
        class Nft_sales(
            val textValue: String,
            val saleStatusShared: SaleStatusShared
        )
        ,
        class Buckets(
            val icpPrincipalApiModel: ICPPrincipalApiModel,
            val stableBucketData: StableBucketData
        )

    }
    */
/**
     * type NFTInfoResult = variant { ok : NFTInfoStable; err : OrigynError };
     *//*

    sealed class NFTInfoResult {
        class ok(val ok: NFTInfoStable): NFTInfoResult()
        class err(val err: OrigynError): NFTInfoResult()

    }
    */
/**
     * type NFTInfoStable = record {
     *   metadata : CandyShared;
     *   current_sale : opt SaleStatusShared;
     * };
     *//*

    class NFTInfoStable(
        val candyShared: CandyShared,
        val saleStatusShared: SaleStatusShared?
    )
    */
/**
     * type NFTUpdateRequest = variant {
     *   update : record {
     *     token_id : text;
     *     update : UpdateRequestShared;
     *     app_id : text;
     *   };
     *   replace : record { token_id : text; data : CandyShared };
     * };
     *//*

    sealed class NFTUpdateRequest {
        class update(
            val token_id: String,
            val updateRequestShared: UpdateRequestShared,
            val app_id: String
        ): NFTUpdateRequest()

        class replace(
            val token_id: String,
            val candyShared: CandyShared
        ): NFTUpdateRequest()
    }
    */
/**
     * type NFTUpdateResult = variant { ok : NFTUpdateResponse; err : OrigynError };
     *//*

    sealed class NFTUpdateResult {
        class ok(val ok: NFTUpdateResponse): NFTUpdateResult()
        class err(val err: OrigynError): NFTUpdateResult()

    }
    */
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
     *//*

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
        class Other(val other: String): NftError()

    }
    */
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
     *                   // balance_of_nft_origyn : (Account) -> (BalanceResult) query;
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
     *                   // nft_batch_origyn : (vec text) -> (vec NFTInfoResult) query;
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
     *//*

    getClassDefinition com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeService@161f6623

    */
/**
     * type NumericEntity = record {
     *   avg : nat64;
     *   max : nat64;
     *   min : nat64;
     *   first : nat64;
     *   last : nat64;
     * };
     *//*

    class NumericEntity(
        val avg: ULong,
        val max: ULong,
        val min: ULong,
        val first: ULong,
        val last: ULong
    )
    */
/**
     * type OrigynTextResult = variant { ok : text; err : OrigynError };
     *//*

    sealed class OrigynTextResult {
        class Ok(val ok: String): OrigynTextResult()
        class err(val err: OrigynError): OrigynTextResult()

    }
    */
/**
     * type OwnerOfResponse = variant { Ok : opt principal; Err : NftError };
     *//*

    sealed class OwnerOfResponse {
        class Ok(val ok: ICPPrincipalApiModel?): OwnerOfResponse()
        class Err(val err: NftError): OwnerOfResponse()

    }
    */
/**
     * type OwnerTransferResponse = record {
     *   transaction : TransactionRecord;
     *   assets : vec CandyShared;
     * };
     *//*

    class OwnerTransferResponse(
        val transactionRecord: TransactionRecord,
        val assets: kotlin.Array<CandyShared>
    )
    */
/**
     * type OwnerUpdateResult = variant {
     *   ok : OwnerTransferResponse;
     *   err : OrigynError;
     * };
     *//*

    sealed class OwnerUpdateResult {
        class ok(val ok: OwnerTransferResponse): OwnerUpdateResult()
        class err(val err: OrigynError): OwnerUpdateResult()

    }
    */
/**
     * type PricingConfigShared = variant {
     *   ask : AskConfigShared;
     *   extensible : CandyShared;
     *   instant : InstantConfigShared;
     *   auction : AuctionConfig;
     * };
     *//*

    sealed class PricingConfigShared {
        class ask(val ask: AskConfigShared): PricingConfigShared()
        class extensible(val extensible: CandyShared): PricingConfigShared()
        class instant(val instant: InstantConfigShared): PricingConfigShared()
        class auction(val auction: AuctionConfig): PricingConfigShared()

    }
    */
/**
     * type PricingConfigShared__1 = variant {
     *   ask : AskConfigShared;
     *   extensible : CandyShared;
     *   instant : InstantConfigShared;
     *   auction : AuctionConfig;
     * };
     *//*

    sealed class PricingConfigShared__1 {
        class ask(val ask: AskConfigShared): PricingConfigShared__1()
        class extensible(val extensible: CandyShared): PricingConfigShared__1()
        class instant(val instant: InstantConfigShared): PricingConfigShared__1()
        class auction(val auction: AuctionConfig): PricingConfigShared__1()

    }
    */
/**
     * type RecognizeEscrowResponse = record {
     *   balance : nat;
     *   receipt : EscrowReceipt;
     *   transaction : opt TransactionRecord;
     * };
     *//*

    class RecognizeEscrowResponse(
        val balance: BigInteger,
        val escrowReceipt: EscrowReceipt,
        val transactionRecord: TransactionRecord?
    )
    */
/**
     * type RejectDescription = record {
     *   token : TokenSpec__1;
     *   token_id : text;
     *   seller : Account;
     *   buyer : Account;
     * };
     *//*

    class RejectDescription(
        val tokenSpec__1: TokenSpec__1,
        val token_id: String,
        val account: Account,
        val account: Account
    )
    */
/**
     * type Result = variant { ok : ManageSaleResponse; err : OrigynError };
     *//*

    sealed class Result {
        class ok(val ok: ManageSaleResponse): Result()
        class err(val err: OrigynError): Result()

    }
    */
/**
     * type SaleInfoResult = variant { ok : SaleInfoResponse; err : OrigynError };
     *//*

    sealed class SaleInfoResult {
        class ok(val ok: SaleInfoResponse): SaleInfoResult()
        class err(val err: OrigynError): SaleInfoResult()

    }
    */
/**
     * type SaleStatusShared = record {
     *   token_id : text;
     *   sale_type : variant { auction : AuctionStateShared };
     *   broker_id : opt principal;
     *   original_broker_id : opt principal;
     *   sale_id : text;
     * };
     *//*

    class SaleStatusShared(
        val token_id: String,
        val null: sale_type,
        val broker_id: ICPPrincipalApiModel?,
        val original_broker_id: ICPPrincipalApiModel?,
        val sale_id: String
    )
    */
/**
     * type SalesConfig = record {
     *   broker_id : opt Account__1;
     *   pricing : PricingConfigShared;
     *   escrow_receipt : opt EscrowReceipt__1;
     * };
     *//*

    class SalesConfig(
        val account__1: Account__1?,
        val pricingConfigShared: PricingConfigShared,
        val escrowReceipt__1: EscrowReceipt__1?
    )
    */
/**
     * type ShareWalletRequest = record {
     *   to : Account;
     *   token_id : text;
     *   from : Account;
     * };
     *//*

    class ShareWalletRequest(
        val account: Account,
        val token_id: String,
        val account: Account
    )
    */
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
     *//*

    class StableCollectionData(
        val active_bucket: ICPPrincipalApiModel?,
        val managers: kotlin.Array<ICPPrincipalApiModel>,
        val owner: ICPPrincipalApiModel,
        val candyShared: CandyShared?,
        val logo: String?,
        val name: String?,
        val network: ICPPrincipalApiModel?,
        val available_space: BigInteger,
        val symbol: String?,
        val allocated_storage: BigInteger
    )
    */
/**
     * type StageChunkArg = record {
     *   content : vec nat8;
     *   token_id : text;
     *   chunk : nat;
     *   filedata : CandyShared;
     *   library_id : text;
     * };
     *//*

    class StageChunkArg(
        val content: kotlin.Array<UByte>,
        val token_id: String,
        val chunk: BigInteger,
        val candyShared: CandyShared,
        val library_id: String
    )
    */
/**
     * type StageLibraryResponse = record { canister : principal };
     *//*

    class StageLibraryResponse(
        val canister: ICPPrincipalApiModel
    )
    */
/**
     * type StageLibraryResult = variant {
     *   ok : StageLibraryResponse;
     *   err : OrigynError;
     * };
     *//*

    sealed class StageLibraryResult {
        class ok(val ok: StageLibraryResponse): StageLibraryResult()
        class err(val err: OrigynError): StageLibraryResult()

    }
    */
/**
     * type StakeRecord = record { staker : Account; token_id : text; amount : nat };
     *//*

    class StakeRecord(
        val account: Account,
        val token_id: String,
        val amount: BigInteger
    )
    */
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
     *//*

    class StateSize(
        val sales_balances: BigInteger,
        val offers: BigInteger,
        val nft_ledgers: BigInteger,
        val allocations: BigInteger,
        val nft_sales: BigInteger,
        val buckets: BigInteger,
        val escrow_balances: BigInteger
    )
    */
/**
     * type StorageMetrics = record {
     *   gateway : principal;
     *   available_space : nat;
     *   allocations : vec AllocationRecordStable;
     *   allocated_storage : nat;
     * };
     *//*

    class StorageMetrics(
        val gateway: ICPPrincipalApiModel,
        val available_space: BigInteger,
        val allocations: kotlin.Array<AllocationRecordStable>,
        val allocated_storage: BigInteger
    )
    */
/**
     * type StorageMetricsResult = variant { ok : StorageMetrics; err : OrigynError };
     *//*

    sealed class StorageMetricsResult {
        class ok(val ok: StorageMetrics): StorageMetricsResult()
        class err(val err: OrigynError): StorageMetricsResult()

    }
    */
/**
     * type StreamingCallbackResponse = record {
     *   token : opt StreamingCallbackToken;
     *   body : vec nat8;
     * };
     *//*

    class StreamingCallbackResponse(
        val streamingCallbackToken: StreamingCallbackToken?,
        val body: kotlin.Array<UByte>
    )
    */
/**
     * type StreamingCallbackToken = record {
     *   key : text;
     *   index : nat;
     *   content_encoding : text;
     * };
     *//*

    class StreamingCallbackToken(
        val key: String,
        val index: BigInteger,
        val content_encoding: String
    )
    */
/**
     * type SubAccountInfo = record {
     *   account_id : vec nat8;
     *   "principal" : principal;
     *   account_id_text : text;
     *   account : record { "principal" : principal; sub_account : vec nat8 };
     * };
     *//*

    class SubAccountInfo(
        val account_id: kotlin.Array<UByte>,
        val "principal": ICPPrincipalApiModel,
    val account_id_text: String,
    val null: account
    )
    */
/**
     * type SupportedStandard = record { url : text; name : text };
     *//*

    class SupportedStandard(
        val url: String,
        val name: String
    )
    */
/**
     * type Tip = record {
     *   last_block_index : vec nat8;
     *   hash_tree : vec nat8;
     *   last_block_hash : vec nat8;
     * };
     *//*

    class Tip(
        val last_block_index: kotlin.Array<UByte>,
        val hash_tree: kotlin.Array<UByte>,
        val last_block_hash: kotlin.Array<UByte>
    )
    */
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
     *//*

    class TokenIDFilter(
        val null: filter_type,
        val token_id: String,
        val tokens: kotlin.Array<Tokens>
    ) {
        class Tokens(
            val tokenSpec__1: TokenSpec__1,
            val min_amount: BigInteger?,
            val max_amount: BigInteger?
        )

    }
    */
/**
     * type TokenSpec = variant { ic : ICTokenSpec; extensible : CandyShared };
     *//*

    sealed class TokenSpec {
        class ic(val ic: ICTokenSpec): TokenSpec()
        class extensible(val extensible: CandyShared): TokenSpec()

    }
    */
/**
     * type TokenSpecFilter = record {
     *   token : TokenSpec__1;
     *   filter_type : variant { allow; block };
     * };
     *//*

    class TokenSpecFilter(
        val tokenSpec__1: TokenSpec__1,
        val null: filter_type
    )
    */
/**
     * type TokenSpec__1 = variant { ic : ICTokenSpec__1; extensible : CandyShared };
     *//*

    sealed class TokenSpec__1 {
        class ic(val ic: ICTokenSpec__1): TokenSpec__1()
        class extensible(val extensible: CandyShared): TokenSpec__1()

    }
    */
/**
     * type TokenSpec__2 = variant { ic : ICTokenSpec; extensible : CandyShared };
     *//*

    sealed class TokenSpec__2 {
        class ic(val ic: ICTokenSpec): TokenSpec__2()
        class extensible(val extensible: CandyShared): TokenSpec__2()

    }
    */
/**
     * type TransactionID = variant {
     *   "nat" : nat;
     *   "text" : text;
     *   extensible : CandyShared;
     * };
     *//*

    sealed class TransactionID {
        class "nat"(val "nat": BigInteger): TransactionID()
        class "text"(val "text": String): TransactionID()
        class extensible(val extensible: CandyShared): TransactionID()

    }
    */
/**
     * type TransactionID__1 = variant {
     *   "nat" : nat;
     *   "text" : text;
     *   extensible : CandyShared;
     * };
     *//*

    sealed class TransactionID__1 {
        class "nat"(val "nat": BigInteger): TransactionID__1()
        class "text"(val "text": String): TransactionID__1()
        class extensible(val extensible: CandyShared): TransactionID__1()

    }
    */
/**
     * type TransactionRange = record { start : nat; length : nat };
     *//*

    class TransactionRange(
        val start: BigInteger,
        val length: BigInteger
    )
    */
/**
     * type TransactionRange__1 = record { start : nat; length : nat };
     *//*

    class TransactionRange__1(
        val start: BigInteger,
        val length: BigInteger
    )
    */
/**
     * type TransferArgs = record {
     *   to : Account__3;
     *   token_id : nat;
     *   memo : opt vec nat8;
     *   from_subaccount : opt vec nat8;
     *   created_at_time : opt nat64;
     * };
     *//*

    class TransferArgs(
        val account__3: Account__3,
        val token_id: BigInteger,
        val memo: kotlin.Array<UByte>?,
        val from_subaccount: kotlin.Array<UByte>?,
        val created_at_time: ULong?
    )
    */
/**
     * type TransferError = variant {
     *   GenericError : record { message : text; error_code : nat };
     *   Duplicate : record { duplicate_of : nat };
     *   NonExistingTokenId;
     *   Unauthorized;
     *   CreatedInFuture : record { ledger_time : nat64 };
     *   TooOld;
     * };
     *//*

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
    */
/**
     * type TransferResultItem = record {
     *   token_id : nat;
     *   transfer_result : variant { Ok : nat; Err : TransferError };
     * };
     *//*

    class TransferResultItem(
        val token_id: BigInteger,
        val null: transfer_result
    )
    */
/**
     * type UpdateModeShared = variant {
     *   Set : CandyShared;
     *   Lock : CandyShared;
     *   Next : vec UpdateShared;
     * };
     *//*

    sealed class UpdateModeShared {
        class Set(val set: CandyShared): UpdateModeShared()
        class Lock(val lock: CandyShared): UpdateModeShared()
        class Next(val next: kotlin.Array<UpdateShared>): UpdateModeShared()

    }
    */
/**
     * type UpdateRequestShared = record { id : text; update : vec UpdateShared };
     *//*

    class UpdateRequestShared(
        val id: String,
        val update: kotlin.Array<UpdateShared>
    )
    */
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
     *//*

    sealed class UpdateSetting {
        class MaxRecordsToArchive(val maxRecordsToArchive: BigInteger): UpdateSetting()
        class archiveIndexType(val archiveIndexType: IndexType): UpdateSetting()
        class MaxArchivePages(val maxArchivePages: BigInteger): UpdateSetting()
        class SettleToRecords(val settleToRecords: BigInteger): UpdateSetting()
        class ArchiveCycles(val archiveCycles: BigInteger): UpdateSetting()
        class MaxActiveRecords(val maxActiveRecords: BigInteger): UpdateSetting()
        class MaxRecordsInArchiveInstance(val maxRecordsInArchiveInstance: BigInteger): UpdateSetting()
        class ArchiveControllers(val archiveControllers: kotlin.Array<ICPPrincipalApiModel>): UpdateSetting()

    }
    */
/**
     * type UpdateShared = record { mode : UpdateModeShared; name : text };
     *//*

    class UpdateShared(
        val updateModeShared: UpdateModeShared,
        val name: String
    )
    */
/**
     * type Value = variant {
     *   Int : int;
     *   Map : vec record { text; Value };
     *   Nat : nat;
     *   Blob : vec nat8;
     *   Text : text;
     *   Array : vec Value;
     * };
     *//*

    sealed class Value {
        class Int(val int: BigInteger): Value()
        class Map(val map: kotlin.Array<MapClass>): Value()
        class Nat(val nat: BigInteger): Value()
        class Blob(val blob: kotlin.Array<UByte>): Value()
        class Text(val text: String): Value()
        class Array(val array: kotlin.Array<Value>): Value()

        class MapClass(
            val textValue: String,
            val value: Value
        )

    }
    */
/**
     * type Value__1 = variant {
     *   Int : int;
     *   Map : vec record { text; Value__1 };
     *   Nat : nat;
     *   Blob : vec nat8;
     *   Text : text;
     *   Array : vec Value__1;
     * };
     *//*

    sealed class Value__1 {
        class Int(val int: BigInteger): Value__1()
        class Map(val map: kotlin.Array<MapClass>): Value__1()
        class Nat(val nat: BigInteger): Value__1()
        class Blob(val blob: kotlin.Array<UByte>): Value__1()
        class Text(val text: String): Value__1()
        class Array(val array: kotlin.Array<Value__1>): Value__1()

        class MapClass(
            val textValue: String,
            val value__1: Value__1
        )

    }
    */
/**
     * type WaitForQuietType = record { max : nat; fade : float64; extension : nat64 };
     *//*

    class WaitForQuietType(
        val max: BigInteger,
        val fade: Double,
        val extension: ULong
    )
    */
/**
     * type WithdrawDescription = record {
     *   token : TokenSpec__1;
     *   token_id : text;
     *   seller : Account;
     *   withdraw_to : Account;
     *   buyer : Account;
     *   amount : nat;
     * };
     *//*

    class WithdrawDescription(
        val tokenSpec__1: TokenSpec__1,
        val token_id: String,
        val account: Account,
        val account: Account,
        val account: Account,
        val amount: BigInteger
    )
    */
/**
     * type WithdrawRequest = variant {
     *   reject : RejectDescription;
     *   fee_deposit : FeeDepositWithdrawDescription;
     *   sale : WithdrawDescription;
     *   deposit : DepositWithdrawDescription;
     *   escrow : WithdrawDescription;
     * };
     *//*

    sealed class WithdrawRequest {
        class reject(val reject: RejectDescription): WithdrawRequest()
        class fee_deposit(val fee_deposit: FeeDepositWithdrawDescription): WithdrawRequest()
        class sale(val sale: WithdrawDescription): WithdrawRequest()
        class deposit(val deposit: DepositWithdrawDescription): WithdrawRequest()
        class escrow(val escrow: WithdrawDescription): WithdrawRequest()

    }
    */
/**
     * type canister_status = record {
     *   status : variant { stopped; stopping; running };
     *   memory_size : nat;
     *   cycles : nat;
     *   settings : definite_canister_settings;
     *   module_hash : opt vec nat8;
     * };
     *//*

    class canister_status(
        val null: status,
        val memory_size: BigInteger,
        val cycles: BigInteger,
        val definite_canister_settings: definite_canister_settings,
        val module_hash: kotlin.Array<UByte>?
    )
    */
/**
     * type definite_canister_settings = record {
     *   freezing_threshold : nat;
     *   controllers : opt vec principal;
     *   memory_allocation : nat;
     *   compute_allocation : nat;
     * };
     *//*

    class definite_canister_settings(
        val freezing_threshold: BigInteger,
        val controllers: kotlin.Array<ICPPrincipalApiModel>?,
        val memory_allocation: BigInteger,
        val compute_allocation: BigInteger
    )
}*/
