package com.bity.icp_kotlin_kit.domain.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.domain.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
import com.bity.icp_kotlin_kit.domain.request.PollingValues
import java.math.BigInteger

/**
 * File generated using ICP Kotlin Kit Plugin
 */
object LedgerCanister {

    /**
     * type Account = record { owner : principal; subaccount : opt blob };
     */
    class Account(
        val owner: ICPPrincipalApiModel,
        val subaccount: ByteArray?
    )

    /**
     * type AccountBalanceArgs = record { account : text };
     */
    class AccountBalanceArgs(
        val account: String
    )

    /**
     * type Allowance = record { allowance : nat; expires_at : opt nat64 };
     */
    class Allowance(
        val allowance: BigInteger,
        val expires_at: ULong?
    )

    /**
     * type AllowanceArgs = record { account : Account; spender : Account };
     */
    class AllowanceArgs(
        val account: Account,
        val spender: Account
    )

    /**
     * type ApproveArgs = record {
     *     fee : opt nat;
     *     memo : opt blob;
     *     from_subaccount : opt blob;
     *     created_at_time : opt nat64;
     *     amount : nat;
     *     expected_allowance : opt nat;
     *     expires_at : opt nat64;
     *     spender : Account;
     * };
     */
    class ApproveArgs(
        val fee: BigInteger?,
        val memo: ByteArray?,
        val from_subaccount: ByteArray?,
        val created_at_time: ULong?,
        val amount: BigInteger,
        val expected_allowance: BigInteger?,
        val expires_at: ULong?,
        val spender: Account
    )

    /**
     * type ApproveError = variant {
     *     GenericError : record { message : text; error_code : nat };
     *     TemporarilyUnavailable;
     *     Duplicate : record { duplicate_of : nat };
     *     BadFee : record { expected_fee : nat };
     *     AllowanceChanged : record { current_allowance : nat };
     *     CreatedInFuture : record { ledger_time : nat64 };
     *     TooOld;
     *     Expired : record { ledger_time : nat64 };
     *     InsufficientFunds : record { balance : nat };
     * };
     */
    sealed class ApproveError {
        class GenericError(
            val message: String,
            val error_code: BigInteger
        ): ApproveError()
        data object TemporarilyUnavailable : ApproveError()
        class Duplicate(val duplicate_of: BigInteger): ApproveError()
        class BadFee(val expected_fee: BigInteger): ApproveError()
        class AllowanceChanged(val current_allowance: BigInteger): ApproveError()
        class CreatedInFuture(val ledger_time: ULong): ApproveError()
        data object TooOld : ApproveError()
        class Expired(val ledger_time: ULong): ApproveError()
        class InsufficientFunds(val balance: BigInteger): ApproveError()
    }

    /**
     * type ArchiveInfo = record { canister_id : principal };
     */
    class ArchiveInfo(
        val canister_id: ICPPrincipalApiModel
    )

    /**
     * type ArchiveOptions = record {
     *     num_blocks_to_archive : nat64;
     *     max_transactions_per_response : opt nat64;
     *     trigger_threshold : nat64;
     *     more_controller_ids : opt vec principal;
     *     max_message_size_bytes : opt nat64;
     *     cycles_for_archive_creation : opt nat64;
     *     node_max_memory_size_bytes : opt nat64;
     *     controller_id : principal;
     * };
     */
    class ArchiveOptions(
        val num_blocks_to_archive: ULong,
        val max_transactions_per_response: ULong?,
        val trigger_threshold: ULong,
        val more_controller_ids: kotlin.Array<ICPPrincipalApiModel>?,
        val max_message_size_bytes: ULong?,
        val cycles_for_archive_creation: ULong?,
        val node_max_memory_size_bytes: ULong?,
        val controller_id: ICPPrincipalApiModel
    )

    /**
     * type ArchivedBlocksRange = record {
     *     callback : func (GetBlocksArgs) -> (Result_4) query;
     *     start : nat64;
     *     length : nat64;
     * };
     */
    class ArchivedBlocksRange(
        val callback: ArchivedEncodedBlocksRangeCallback,
        val start: ULong,
        val length: ULong
    ) {
        class ArchivedEncodedBlocksRangeCallback(
            methodName: String,
            canister: ICPPrincipal
        ) : ICPQuery (
            methodName = methodName,
            canister = canister
        ) {
            suspend operator fun invoke(
                getBlocksArgs: GetBlocksArgs,
                certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
                sender: ICPSigningPrincipal? = null,
                pollingValues: PollingValues = PollingValues()
            ): Result_4 {
                val result = this(
                    args = listOf(getBlocksArgs),
                    certification = certification,
                    sender = sender,
                    pollingValues = pollingValues
                ).getOrThrow()
                return CandidDecoder.decodeNotNull(result)
            }
        }
    }

    /**
     * type ArchivedEncodedBlocksRange = record {
     *     callback : func (GetBlocksArgs) -> (Result_5) query;
     *     start : nat64;
     *     length : nat64;
     * };
     */
    class ArchivedEncodedBlocksRange(
        val callback: ArchivedEncodedBlocksRangeCallback,
        val start: ULong,
        val length: ULong
    ) {
        class ArchivedEncodedBlocksRangeCallback(
            methodName: String,
            canister: ICPPrincipal
        ) : ICPQuery (
            methodName = methodName,
            canister = canister
        ) {
            suspend operator fun invoke(
                getBlocksArgs: GetBlocksArgs,
                certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
                sender: ICPSigningPrincipal? = null,
                pollingValues: PollingValues = PollingValues()
            ): Result_5 {
                val result = this(
                    args = listOf(getBlocksArgs),
                    certification = certification,
                    sender = sender,
                    pollingValues = pollingValues
                ).getOrThrow()
                return CandidDecoder.decodeNotNull(result)
            }
        }
    }

    /**
     * type Archives = record { archives : vec ArchiveInfo };
     */
    class Archives(
        val archives: kotlin.Array<ArchiveInfo>
    )

    /**
     * type BinaryAccountBalanceArgs = record { account : blob };
     */
    class BinaryAccountBalanceArgs(
        val account: ByteArray
    )

    /**
     * type BlockRange = record { blocks : vec CandidBlock };
     */
    class BlockRange(
        val blocks: kotlin.Array<CandidBlock>
    )

    /**
     * type CandidBlock = record {
     *     transaction : CandidTransaction;
     *     timestamp : TimeStamp;
     *     parent_hash : opt blob;
     * };
     */
    class CandidBlock(
        val transaction: CandidTransaction,
        val timestamp: TimeStamp,
        val parent_hash: ByteArray?
    )

    /**
     * type CandidOperation = variant {
     *     Approve : record {
     *         fee : Tokens;
     *         from : blob;
     *         allowance_e8s : int;
     *         allowance : Tokens;
     *         expected_allowance : opt Tokens;
     *         expires_at : opt TimeStamp;
     *         spender : blob;
     *     };
     *     Burn : record { from : blob; amount : Tokens; spender : opt blob };
     *     Mint : record { to : blob; amount : Tokens };
     *     Transfer : record {
     *         to : blob;
     *         fee : Tokens;
     *         from : blob;
     *         amount : Tokens;
     *         spender : opt blob;
     *     };
     * };
     */
    sealed class CandidOperation {
        class Approve(
            val fee: Tokens,
            val from: ByteArray,
            val allowance_e8s: BigInteger,
            val allowance: Tokens,
            val expected_allowance: Tokens?,
            val expires_at: TimeStamp?,
            val spender: ByteArray
        ): CandidOperation()
        class Burn(
            val from: ByteArray,
            val amount: Tokens,
            val spender: ByteArray?
        ): CandidOperation()
        class Mint(
            val to: ByteArray,
            val amount: Tokens
        ): CandidOperation()
        class Transfer(
            val to: ByteArray,
            val fee: Tokens,
            val from: ByteArray,
            val amount: Tokens,
            val spender: ByteArray?
        ): CandidOperation()
    }

    /**
     * type CandidTransaction = record {
     *     memo : nat64;
     *     icrc1_memo : opt blob;
     *     operation : opt CandidOperation;
     *     created_at_time : TimeStamp;
     * };
     */
    class CandidTransaction(
        val memo: ULong,
        val icrc1_memo: ByteArray?,
        val operation: CandidOperation?,
        val created_at_time: TimeStamp
    )

    /**
     * type ConsentInfo = record {
     *     metadata : ConsentMessageMetadata;
     *     consent_message : ConsentMessage;
     * };
     */
    class ConsentInfo(
        val metadata: ConsentMessageMetadata,
        val consent_message: ConsentMessage
    )

    /**
     * type ConsentMessage = variant {
     *     LineDisplayMessage : record { pages : vec LineDisplayPage };
     *     GenericDisplayMessage : text;
     * };
     */
    sealed class ConsentMessage {
        class LineDisplayMessage(val pages: Array<LineDisplayPage>): ConsentMessage()
        class GenericDisplayMessage(val string: String): ConsentMessage()
    }

    /**
     * type ConsentMessageMetadata = record {
     *     utc_offset_minutes : opt int16;
     *     language : text;
     * };
     */
    class ConsentMessageMetadata(
        val utc_offset_minutes: Short?,
        val language: String
    )

    /**
     * type ConsentMessageRequest = record {
     *     arg : blob;
     *     method : text;
     *     user_preferences : ConsentMessageSpec;
     * };
     */
    class ConsentMessageRequest(
        val arg: ByteArray,
        val method: String,
        val user_preferences: ConsentMessageSpec
    )

    /**
     * type ConsentMessageSpec = record {
     *     metadata : ConsentMessageMetadata;
     *     device_spec : opt DisplayMessageType;
     * };
     */
    class ConsentMessageSpec(
        val metadata: ConsentMessageMetadata,
        val device_spec: DisplayMessageType?
    )

    /**
     * type Decimals = record { decimals : nat32 };
     */
    class Decimals(
        val decimals: UInt
    )

    /**
     * type DisplayMessageType = variant {
     *     GenericDisplay;
     *     LineDisplay : record { characters_per_line : nat16; lines_per_page : nat16 };
     * };
     */
    sealed class DisplayMessageType {
        data object GenericDisplay : DisplayMessageType()
        class LineDisplay(
            val characters_per_line: UShort,
            val lines_per_page: UShort
        ): DisplayMessageType()
    }

    /**
     * type Duration = record { secs : nat64; nanos : nat32 };
     */
    class Duration(
        val secs: ULong,
        val nanos: UInt
    )

    /**
     * type ErrorInfo = record { description : text };
     */
    class ErrorInfo(
        val description: String
    )

    /**
     * type FeatureFlags = record { icrc2 : bool };
     */
    class FeatureFlags(
        val icrc2: Boolean
    )

    /**
     * type GetBlocksArgs = record { start : nat64; length : nat64 };
     */
    class GetBlocksArgs(
        val start: ULong,
        val length: ULong
    )

    /**
     * type GetBlocksError = variant {
     *     BadFirstBlockIndex : record {
     *         requested_index : nat64;
     *         first_valid_index : nat64;
     *     };
     *     Other : record { error_message : text; error_code : nat64 };
     * };
     */
    sealed class GetBlocksError {
        class BadFirstBlockIndex(
            val requested_index: ULong,
            val first_valid_index: ULong
        ): GetBlocksError()
        class Other(
            val error_message: String,
            val error_code: ULong
        ): GetBlocksError()
    }

    /**
     * type Icrc21Error = variant {
     *     GenericError : record { description : text; error_code : nat };
     *     InsufficientPayment : ErrorInfo;
     *     UnsupportedCanisterCall : ErrorInfo;
     *     ConsentMessageUnavailable : ErrorInfo;
     * };
     */
    sealed class Icrc21Error {
        class GenericError(
            val description: String,
            val error_code: BigInteger
        ): Icrc21Error()
        class InsufficientPayment(val errorInfo: ErrorInfo): Icrc21Error()
        class UnsupportedCanisterCall(val errorInfo: ErrorInfo): Icrc21Error()
        class ConsentMessageUnavailable(val errorInfo: ErrorInfo): Icrc21Error()
    }

    /**
     * type InitArgs = record {
     *     send_whitelist : vec principal;
     *     token_symbol : opt text;
     *     transfer_fee : opt Tokens;
     *     minting_account : text;
     *     maximum_number_of_accounts : opt nat64;
     *     accounts_overflow_trim_quantity : opt nat64;
     *     transaction_window : opt Duration;
     *     max_message_size_bytes : opt nat64;
     *     icrc1_minting_account : opt Account;
     *     archive_options : opt ArchiveOptions;
     *     initial_values : vec record { text; Tokens };
     *     token_name : opt text;
     *     feature_flags : opt FeatureFlags;
     * };
     */
    class InitArgs(
        val send_whitelist: kotlin.Array<ICPPrincipalApiModel>,
        val token_symbol: String?,
        val transfer_fee: Tokens?,
        val minting_account: String,
        val maximum_number_of_accounts: ULong?,
        val accounts_overflow_trim_quantity: ULong?,
        val transaction_window: Duration?,
        val max_message_size_bytes: ULong?,
        val icrc1_minting_account: Account?,
        val archive_options: ArchiveOptions?,
        val initial_values: Array<_Class1>,
        val token_name: String?,
        val feature_flags: FeatureFlags?
    ) {
        class _Class1(
            val string: String,
            val tokens: Tokens
        )
    }

    /**
     * type LedgerCanisterPayload = variant {
     *     Upgrade : opt UpgradeArgs;
     *     Init : InitArgs;
     * };
     */
    sealed class LedgerCanisterPayload {
        class Upgrade(val upgradeArgs: UpgradeArgs?): LedgerCanisterPayload()
        class Init(val initArgs: InitArgs): LedgerCanisterPayload()
    }

    /**
     * type LineDisplayPage = record { lines : vec text };
     */
    class LineDisplayPage(val lines: Array<String>)

    /**
     * type MetadataValue = variant { Int : int; Nat : nat; Blob : blob; Text : text };
     */
    sealed class MetadataValue {
        class Int(val bigInteger: BigInteger): MetadataValue()
        class Nat(val bigInteger: BigInteger): MetadataValue()
        class Blob(val byteArray: ByteArray): MetadataValue()
        class Text(val string: String): MetadataValue()
    }

    /**
     * type Name = record { name : text };
     */
    class Name(val name: String)

    /**
     * type QueryBlocksResponse = record {
     *     certificate : opt blob;
     *     blocks : vec CandidBlock;
     *     chain_length : nat64;
     *     first_block_index : nat64;
     *     archived_blocks : vec ArchivedBlocksRange;
     * };
     */
    class QueryBlocksResponse(
        val certificate: ByteArray?,
        val blocks: Array<CandidBlock>,
        val chain_length: ULong,
        val first_block_index: ULong,
        val archived_blocks: Array<ArchivedBlocksRange>
    )

    /**
     * type QueryEncodedBlocksResponse = record {
     *     certificate : opt blob;
     *     blocks : vec blob;
     *     chain_length : nat64;
     *     first_block_index : nat64;
     *     archived_blocks : vec ArchivedEncodedBlocksRange;
     * };
     */
    class QueryEncodedBlocksResponse(
        val certificate: ByteArray?,
        val blocks: Array<ByteArray>,
        val chain_length: ULong,
        val first_block_index: ULong,
        val archived_blocks: Array<ArchivedEncodedBlocksRange>
    )

    /**
     * type Result = variant { Ok : nat; Err : TransferError };
     */
    sealed class Result {
        class Ok(val bigInteger: BigInteger): Result()
        class Err(val transferError: TransferError): Result()
    }

    /**
     * type Result_1 = variant { Ok : ConsentInfo; Err : Icrc21Error };
     */
    sealed class Result_1 {
        class Ok(val consentInfo: ConsentInfo): Result_1()
        class Err(val icrc21Error: Icrc21Error): Result_1()
    }

    /**
     * type Result_2 = variant { Ok : nat; Err : ApproveError };
     */
    sealed class Result_2 {
        class Ok(val bigInteger: BigInteger): Result_2()
        class Err(val approveError: ApproveError): Result_2()
    }

    /**
     * type Result_3 = variant { Ok : nat; Err : TransferFromError };
     */
    sealed class Result_3 {
        class Ok(val bigInteger: BigInteger): Result_3()
        class Err(val transferFromError: TransferFromError): Result_3()
    }

    /**
     * type Result_4 = variant { Ok : BlockRange; Err : GetBlocksError };
     */
    sealed class Result_4 {
        class Ok(val blockRange: BlockRange): Result_4()
        class Err(val getBlocksError: GetBlocksError): Result_4()
    }

    /**
     * type Result_5 = variant { Ok : vec blob; Err : GetBlocksError };
     */
    sealed class Result_5 {
        class Ok(val values: Array<ByteArray>): Result_5()
        class Err(val getBlocksError: GetBlocksError): Result_5()
    }

    /**
     * type Result_6 = variant { Ok : nat64; Err : TransferError_1 };
     */
    sealed class Result_6 {
        class Ok(val uLong: ULong): Result_6()
        class Err(val transferError_1: TransferError_1): Result_6()
    }

    /**
     * type SendArgs = record {
     *     to : text;
     *     fee : Tokens;
     *     memo : nat64;
     *     from_subaccount : opt blob;
     *     created_at_time : opt TimeStamp;
     *     amount : Tokens;
     * };
     */
    class SendArgs(
        val to: String,
        val fee: Tokens,
        val memo: ULong,
        val from_subaccount: ByteArray?,
        val created_at_time: TimeStamp?,
        val amount: Tokens
    )

    /**
     * type StandardRecord = record { url : text; name : text };
     */
    class StandardRecord(
        val url: String,
        val name: String
    )

    /**
     * type Symbol = record { symbol : text };
     */
    class Symbol(
        val symbol: String
    )

    /**
     * type TimeStamp = record { timestamp_nanos : nat64 };
     */
    class TimeStamp(
        val timestamp_nanos: ULong
    )

    /**
     * type Tokens = record { e8s : nat64 };
     */
    class Tokens(
        val e8s: ULong
    )

    /**
     * type TransferArg = record {
     *     to : Account;
     *     fee : opt nat;
     *     memo : opt blob;
     *     from_subaccount : opt blob;
     *     created_at_time : opt nat64;
     *     amount : nat;
     * };
     */
    class TransferArg(
        val to: Account,
        val fee: BigInteger?,
        val memo: ByteArray?,
        val from_subaccount: ByteArray?,
        val created_at_time: ULong?,
        val amount: BigInteger
    )

    /**
     * type TransferArgs = record {
     *     to : blob;
     *     fee : Tokens;
     *     memo : nat64;
     *     from_subaccount : opt blob;
     *     created_at_time : opt TimeStamp;
     *     amount : Tokens;
     * };
     */
    class TransferArgs(
        val to: ByteArray,
        val fee: Tokens,
        val memo: ULong,
        val from_subaccount: ByteArray?,
        val created_at_time: TimeStamp?,
        val amount: Tokens
    )

    /**
     * type TransferError = variant {
     *     GenericError : record { message : text; error_code : nat };
     *     TemporarilyUnavailable;
     *     BadBurn : record { min_burn_amount : nat };
     *     Duplicate : record { duplicate_of : nat };
     *     BadFee : record { expected_fee : nat };
     *     CreatedInFuture : record { ledger_time : nat64 };
     *     TooOld;
     *     InsufficientFunds : record { balance : nat };
     * };
     */
    sealed class TransferError {
        class GenericError(
            val message: String,
            val error_code: BigInteger
        ): TransferError()
        data object TemporarilyUnavailable : TransferError()
        class BadBurn(val min_burn_amount: BigInteger): TransferError()
        class Duplicate(val duplicate_of: BigInteger): TransferError()
        class BadFee(val expected_fee: BigInteger): TransferError()
        class CreatedInFuture(val ledger_time: ULong): TransferError()
        data object TooOld : TransferError()
        class InsufficientFunds(val balance: BigInteger): TransferError()
    }

    /**
     * type TransferError_1 = variant {
     *     TxTooOld : record { allowed_window_nanos : nat64 };
     *     BadFee : record { expected_fee : Tokens };
     *     TxDuplicate : record { duplicate_of : nat64 };
     *     TxCreatedInFuture;
     *     InsufficientFunds : record { balance : Tokens };
     * };
     */
    sealed class TransferError_1 {
        class TxTooOld(val allowed_window_nanos: ULong): TransferError_1()
        class BadFee(val expected_fee: Tokens): TransferError_1()
        class TxDuplicate(val duplicate_of: ULong): TransferError_1()
        data object TxCreatedInFuture : TransferError_1()
        class InsufficientFunds(val balance: Tokens): TransferError_1()
    }

    /**
     * type TransferFee = record { transfer_fee : Tokens };
     */
    class TransferFee(
        val transfer_fee: Tokens
    )

    /**
     * type TransferFromArgs = record {
     *     to : Account;
     *     fee : opt nat;
     *     spender_subaccount : opt blob;
     *     from : Account;
     *     memo : opt blob;
     *     created_at_time : opt nat64;
     *     amount : nat;
     * };
     */
    class TransferFromArgs(
        val to: Account,
        val fee: BigInteger?,
        val spender_subaccount: ByteArray?,
        val from: Account,
        val memo: ByteArray?,
        val created_at_time: ULong?,
        val amount: BigInteger
    )

    /**
     * type TransferFromError = variant {
     *     GenericError : record { message : text; error_code : nat };
     *     TemporarilyUnavailable;
     *     InsufficientAllowance : record { allowance : nat };
     *     BadBurn : record { min_burn_amount : nat };
     *     Duplicate : record { duplicate_of : nat };
     *     BadFee : record { expected_fee : nat };
     *     CreatedInFuture : record { ledger_time : nat64 };
     *     TooOld;
     *     InsufficientFunds : record { balance : nat };
     * };
     */
    sealed class TransferFromError {
        class GenericError(
            val message: String,
            val error_code: BigInteger
        ): TransferFromError()
        data object TemporarilyUnavailable : TransferFromError()
        class InsufficientAllowance(val allowance: BigInteger): TransferFromError()
        class BadBurn(val min_burn_amount: BigInteger): TransferFromError()
        class Duplicate(val duplicate_of: BigInteger): TransferFromError()
        class BadFee(val expected_fee: BigInteger): TransferFromError()
        class CreatedInFuture(val ledger_time: ULong): TransferFromError()
        data object TooOld : TransferFromError()
        class InsufficientFunds(val balance: BigInteger): TransferFromError()
    }

    /**
     * type UpgradeArgs = record {
     *     icrc1_minting_account : opt Account;
     *     feature_flags : opt FeatureFlags;
     * };
     */
    class UpgradeArgs(
        val icrc1_minting_account: Account?,
        val feature_flags: FeatureFlags?
    )

    class LedgerCanisterService(
        private val canister: ICPPrincipal
    ) {
        /**
         * account_balance : (BinaryAccountBalanceArgs) -> (Tokens) query;
         */
        suspend fun account_balance (
            binaryAccountBalanceArgs: BinaryAccountBalanceArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Tokens {
            val icpQuery = ICPQuery(
                methodName = "account_balance",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(binaryAccountBalanceArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * account_balance_dfx : (AccountBalanceArgs) -> (Tokens) query;
         */
        suspend fun account_balance_dfx (
            accountBalanceArgs: AccountBalanceArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Tokens {
            val icpQuery = ICPQuery(
                methodName = "account_balance_dfx",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(accountBalanceArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * account_identifier : (Account) -> (blob) query;
         */
        suspend fun account_identifier (
            account: Account,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): ByteArray {
            val icpQuery = ICPQuery(
                methodName = "account_identifier",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(account),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * archives : () -> (Archives) query;
         */
        suspend fun archives (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Archives {
            val icpQuery = ICPQuery(
                methodName = "archives",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * decimals : () -> (Decimals) query;
         */
        suspend fun decimals (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Decimals {
            val icpQuery = ICPQuery(
                methodName = "decimals",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc10_supported_standards : () -> (vec StandardRecord) query;
         */
        suspend fun icrc10_supported_standards (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): kotlin.Array<StandardRecord> {
            val icpQuery = ICPQuery(
                methodName = "icrc10_supported_standards",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_balance_of : (Account) -> (nat) query;
         */
        suspend fun icrc1_balance_of (
            account: Account,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger {
            val icpQuery = ICPQuery(
                methodName = "icrc1_balance_of",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(account),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_decimals : () -> (nat8) query;
         */
        suspend fun icrc1_decimals (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): UByte {
            val icpQuery = ICPQuery(
                methodName = "icrc1_decimals",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_fee : () -> (nat) query;
         */
        suspend fun icrc1_fee (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger {
            val icpQuery = ICPQuery(
                methodName = "icrc1_fee",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_metadata : () -> (vec record { text; MetadataValue }) query;
         */
        suspend fun icrc1_metadata (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<UnnamedClass0> {
            val icpQuery = ICPQuery(
                methodName = "icrc1_metadata",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        class UnnamedClass0(
            val string: String,
            val metadataValue: MetadataValue
        )

        /**
         * icrc1_minting_account : () -> (opt Account) query;
         */
        suspend fun icrc1_minting_account (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Account? {
            val icpQuery = ICPQuery(
                methodName = "icrc1_minting_account",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decode(result)
        }

        /**
         * icrc1_name : () -> (text) query;
         */
        suspend fun icrc1_name (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): String {
            val icpQuery = ICPQuery(
                methodName = "icrc1_name",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_supported_standards : () -> (vec StandardRecord) query;
         */
        suspend fun icrc1_supported_standards (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): kotlin.Array<StandardRecord> {
            val icpQuery = ICPQuery(
                methodName = "icrc1_supported_standards",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_symbol : () -> (text) query;
         */
        suspend fun icrc1_symbol (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): String {
            val icpQuery = ICPQuery(
                methodName = "icrc1_symbol",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_total_supply : () -> (nat) query;
         */
        suspend fun icrc1_total_supply (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger {
            val icpQuery = ICPQuery(
                methodName = "icrc1_total_supply",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_transfer : (TransferArg) -> (Result);
         */
        suspend fun icrc1_transfer (
            transferArg: TransferArg,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Result {
            val icpQuery = ICPQuery(
                methodName = "icrc1_transfer",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(transferArg),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc21_canister_call_consent_message : (ConsentMessageRequest) -> (Result_1);
         */
        suspend fun icrc21_canister_call_consent_message (
            consentMessageRequest: ConsentMessageRequest,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Result_1 {
            val icpQuery = ICPQuery(
                methodName = "icrc21_canister_call_consent_message",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(consentMessageRequest),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc2_allowance : (AllowanceArgs) -> (Allowance) query;
         */
        suspend fun icrc2_allowance (
            allowanceArgs: AllowanceArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Allowance {
            val icpQuery = ICPQuery(
                methodName = "icrc2_allowance",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(allowanceArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc2_approve : (ApproveArgs) -> (Result_2);
         */
        suspend fun icrc2_approve (
            approveArgs: ApproveArgs,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Result_2 {
            val icpQuery = ICPQuery(
                methodName = "icrc2_approve",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(approveArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc2_transfer_from : (TransferFromArgs) -> (Result_3);
         */
        suspend fun icrc2_transfer_from (
            transferFromArgs: TransferFromArgs,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Result_3 {
            val icpQuery = ICPQuery(
                methodName = "icrc2_transfer_from",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(transferFromArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * name : () -> (Name) query;
         */
        suspend fun name (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Name {
            val icpQuery = ICPQuery(
                methodName = "name",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * query_blocks : (GetBlocksArgs) -> (QueryBlocksResponse) query;
         */
        suspend fun query_blocks (
            getBlocksArgs: GetBlocksArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): QueryBlocksResponse {
            val icpQuery = ICPQuery(
                methodName = "query_blocks",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(getBlocksArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * query_encoded_blocks : (GetBlocksArgs) -> (QueryEncodedBlocksResponse) query;
         */
        suspend fun query_encoded_blocks (
            getBlocksArgs: GetBlocksArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): QueryEncodedBlocksResponse {
            val icpQuery = ICPQuery(
                methodName = "query_encoded_blocks",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(getBlocksArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * send_dfx : (SendArgs) -> (nat64);
         */
        suspend fun send_dfx (
            sendArgs: SendArgs,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): ULong {
            val icpQuery = ICPQuery(
                methodName = "send_dfx",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(sendArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * symbol : () -> (Symbol) query;
         */
        suspend fun symbol (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Symbol {
            val icpQuery = ICPQuery(
                methodName = "symbol",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * transfer : (TransferArgs) -> (Result_6);
         */
        suspend fun transfer (
            transferArgs: TransferArgs,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Result_6 {
            val icpQuery = ICPQuery(
                methodName = "transfer",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(transferArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * transfer_fee : (record {}) -> (TransferFee) query;
         */
        suspend fun transfer_fee (
            unnamedClass1: UnnamedClass1,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): TransferFee {
            val icpQuery = ICPQuery(
                methodName = "transfer_fee",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(unnamedClass1),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        class UnnamedClass1()
    }
}