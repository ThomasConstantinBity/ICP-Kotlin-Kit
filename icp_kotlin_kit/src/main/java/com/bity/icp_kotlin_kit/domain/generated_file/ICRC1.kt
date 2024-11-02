package com.bity.icp_kotlin_kit.domain.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
import com.bity.icp_kotlin_kit.domain.request.PollingValues
import java.math.BigInteger

/**
 * File generated using ICP Kotlin Kit Plugin
 */

/**
 * type Subaccount = blob;
 */
typealias ICRC1Subaccount = ByteArray

object ICRC1 {

    /**
     * type Account = record { owner : principal; subaccount : opt Subaccount; };
     */
    class Account(
        val owner: ICPPrincipalApiModel,
        val subaccount: ICRC1Subaccount?
    )

    /**
     * type Value = variant {
     *     Nat : nat;
     *     Int : int;
     *     Text : text;
     *     Blob : blob;
     * };
     */
    sealed class Value {
        class Nat(val bigInteger: BigInteger): Value()
        class Int(val bigInteger: BigInteger): Value()
        class Text(val string: String): Value()
        class Blob(val byteArray: ByteArray): Value()
    }

    /**
     * type TransferArgs = record {
     *     from_subaccount : opt Subaccount;
     *     to : Account;
     *     amount : nat;
     *     fee : opt nat;
     *     memo : opt blob;
     *     created_at_time : opt nat64;
     * };
     */
    class TransferArgs(
        val from_subaccount: ICRC1Subaccount?,
        val to: Account,
        val amount: BigInteger,
        val fee: BigInteger?,
        val memo: ByteArray?,
        val created_at_time: ULong?
    )

    /**
     * type TransferError = variant {
     *     BadFee : record { expected_fee : nat };
     *     BadBurn : record { min_burn_amount : nat };
     *     InsufficientFunds : record { balance : nat };
     *     TooOld;
     *     CreatedInFuture : record { ledger_time: nat64 };
     *     Duplicate : record { duplicate_of : nat };
     *     TemporarilyUnavailable;
     *     GenericError : record { error_code : nat; message : text };
     * };
     */
    sealed class TransferError {
        class BadFee(val expected_fee: BigInteger): TransferError()
        class BadBurn(val min_burn_amount: BigInteger): TransferError()
        class InsufficientFunds(val balance: BigInteger): TransferError()
        data object TooOld : TransferError()
        class CreatedInFuture(val ledger_time: ULong): TransferError()
        class Duplicate(val duplicate_of: BigInteger): TransferError()
        data object TemporarilyUnavailable : TransferError()
        class GenericError(
            val error_code: BigInteger,
            val message: String
        ): TransferError()
    }

    /**
     * type TransferResult = variant { Ok: nat; Err: TransferError; };
     */
    sealed class TransferResult {
        class Ok(val bigInteger: BigInteger): TransferResult()
        class Err(val transferError: TransferError): TransferResult()
    }

    /**
     * type MetadataField = record { text; Value };
     */
    data class MetadataField(
        val string: String,
        val value: Value
    )

    /**
     * type SupportedStandard = record { name : text; url : text };
     */
    class SupportedStandard(
        val name: String,
        val url: String
    )

    class ICRC1Service(
        private val canister: ICPPrincipal
    ) {
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
            val result = icpQuery.invoke(
                values = listOf(),
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
            val result = icpQuery.invoke(
                values = listOf(),
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
            val result = icpQuery.invoke(
                values = listOf(),
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
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_metadata : () -> (vec MetadataField) query;
         */
        suspend fun icrc1_metadata (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<MetadataField> {
            val icpQuery = ICPQuery(
                methodName = "icrc1_metadata",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
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
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

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
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decode(result)
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
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(account)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_transfer : (TransferArgs) -> (TransferResult);
         */
        suspend fun icrc1_transfer (
            transferArgs: TransferArgs,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): TransferResult {
            val icpQuery = ICPQuery(
                methodName = "icrc1_transfer",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(transferArgs)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }

        /**
         * icrc1_supported_standards : () -> (vec SupportedStandard) query;
         */
        suspend fun icrc1_supported_standards (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<SupportedStandard> {
            val icpQuery = ICPQuery(
                methodName = "icrc1_supported_standards",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result)
        }
    }
}