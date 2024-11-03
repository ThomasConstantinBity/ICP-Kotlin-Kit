package com.bity.icp_kotlin_kit.domain.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.request.PollingValues
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification

/**
 * File generated using ICP Kotlin Kit Plugin
 */

object DIP20 {

    /**
     * type Metadata = record {
     *     logo : text; // base64 encoded logo or logo url
     *     name : text; // token name
     *     symbol : text; // token symbol
     *     decimals : nat8; // token decimal
     *     totalSupply : nat; // token total supply
     *     owner : principal; // token owner
     *     fee : nat; // fee for update calls
     * };
     */
    class Metadata(
        // base64 encoded logo or logo url
        val logo: String,

        // token name
        val name: String,

        // token symbol
        val symbol: String,

        // token decimal
        val decimals: UByte,

        // token total supply
        val totalSupply: BigInteger,

        // token owner
        val owner: ICPPrincipalApiModel,

        // fee for update calls
        val fee: BigInteger
    )

    /**
     * type TxError = variant {
     *     InsufficientAllowance;
     *     InsufficientBalance;
     *     ErrorOperationStyle;
     *     Unauthorized;
     *     LedgerTrap;
     *     ErrorTo;
     *     Other: text;
     *     BlockUsed;
     *     AmountTooSmall;
     * };
     */
    sealed class TxError {
        data object InsufficientAllowance : TxError()
        data object InsufficientBalance : TxError()
        data object ErrorOperationStyle : TxError()
        data object Unauthorized : TxError()
        data object LedgerTrap : TxError()
        data object ErrorTo : TxError()

        class Other(
            val string: String
        ): TxError()

        data object BlockUsed : TxError()
        data object AmountTooSmall : TxError()
    }

    /**
     * type TxReceipt = variant {
     *     Ok: nat;
     *     Err: TxError;
     * };
     */
    sealed class TxReceipt {

        class Ok(
            val bigInteger: BigInteger
        ): TxReceipt()

        class Err(
            val txError: TxError
        ): TxReceipt()
    }

    /**
     * type Operation = variant {
     *     approve;
     *     mint;
     *     transfer;
     *     transferFrom;
     * };
     */
    sealed class Operation {
        data object approve : Operation()
        data object mint : Operation()
        data object transfer : Operation()
        data object transferFrom : Operation()
    }

    /**
     * type TransactionStatus = variant {
     *     succeeded;
     *     failed;
     * };
     */
    sealed class TransactionStatus {
        data object succeeded : TransactionStatus()
        data object failed : TransactionStatus()
    }

    /**
     * type TimeStamp = record {
     *     timestamp_nanos: nat64;
     * };
     */
    // Timestamps are represented as nanoseconds from the UNIX epoch in UTC timezone
    class TimeStamp(
        val timestamp_nanos: ULong
    )

    /**
     * type TxRecord = record {
     *     caller: opt principal;
     *     op: Operation; // operation type
     *     index: nat; // transaction index
     *     from: principal;
     *     to: principal;
     *     amount: nat;
     *     fee: nat;
     *     timestamp: TimeStamp;
     *     status: TransactionStatus;
     * };
     */
    class TxRecord(
        val caller: ICPPrincipalApiModel?,

        // operation type
        val op: Operation,

        // transaction index
        val index: BigInteger,
        val from: ICPPrincipalApiModel,
        val to: ICPPrincipalApiModel,
        val amount: BigInteger,
        val fee: BigInteger,
        val timestamp: TimeStamp,
        val status: TransactionStatus
    )

    class DIP20Service(
        private val canister: ICPPrincipal
    ) {

        /**
         * logo: () -> (text) query;
         */
        // Returns the logo of the token.
        suspend fun logo (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): String {
            val icpQuery = ICPQuery(
                methodName = "logo",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * name: () -> (text) query;
         */
        // Returns the name of the token.
        suspend fun name (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): String {
            val icpQuery = ICPQuery(
                methodName = "name",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * symbol: () -> (text) query;
         */
        // Returns the symbol of the token.
        suspend fun symbol (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): String {
            val icpQuery = ICPQuery(
                methodName = "symbol",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * decimals: () -> (nat8) query;
         */
        // Returns the decimals of the token.
        suspend fun decimals (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): UByte {
            val icpQuery = ICPQuery(
                methodName = "decimals",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * totalSupply: () -> (nat) query;
         */
        // Returns the total supply of the token.
        suspend fun totalSupply (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger {
            val icpQuery = ICPQuery(
                methodName = "totalSupply",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * balanceOf: (who: principal) -> (nat) query;
         */
        // Returns the balance of user who.
        suspend fun balanceOf (
            who: ICPPrincipalApiModel,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger {
            val icpQuery = ICPQuery(
                methodName = "balanceOf",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(who)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * allowance: (owner: principal, spender: principal) -> (nat) query;
         */
        // Returns the amount which spender is still allowed to withdraw from owner.
        suspend fun allowance (
            owner: ICPPrincipalApiModel,
            spender: ICPPrincipalApiModel,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger {
            val icpQuery = ICPQuery(
                methodName = "allowance",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(owner),
                    ValueToEncode(spender)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * getMetadata: () -> (Metadata) query;
         */
        // Returns the metadata of the token.
        suspend fun getMetadata (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Metadata {
            val icpQuery = ICPQuery(
                methodName = "getMetadata",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * historySize: () -> (nat) query;
         */
        // Returns the history size.
        suspend fun historySize (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger {
            val icpQuery = ICPQuery(
                methodName = "historySize",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * getTransaction: (index: nat) -> (TxRecord) query;
         */
        // Returns transaction detail of the transaction identified by index.
        // If the index is out of range, the execution traps. Transactions are indexed from zero.
        suspend fun getTransaction (
            index: BigInteger,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): TxRecord {
            val icpQuery = ICPQuery(
                methodName = "getTransaction",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(index)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * getTransactions: (start: nat, limit: nat) -> (vec TxRecord) query;
         */
        // Returns an array of transaction records in the range [start, start + limit).
        // To fend off DoS attacks, this function is allowed to trap, if limit is greater than the limit allowed by the token.
        // This function is also allowed to trap if start + limit > historySize()
        suspend fun getTransactions (
            start: BigInteger,
            limit: BigInteger,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): kotlin.Array<TxRecord> {
            val icpQuery = ICPQuery(
                methodName = "getTransactions",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(start),
                    ValueToEncode(limit)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * getUserTransactions: (who: principal, start: nat, limit: nat) -> (vec TxRecord) query;
         */
        // Returns an array of transaction records in range [start, start + limit) related to user who.
        // Unlike getTransactions function, the range [start, start + limit) for getUserTransactions is not the global range of all transactions.
        // The range [start, start + limit) here pertains to the transactions of user who.
        // Implementations are allowed to return less TxRecords than requested to fend off DoS attacks.
        suspend fun getUserTransactions (
            who: ICPPrincipalApiModel,
            start: BigInteger,
            limit: BigInteger,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): kotlin.Array<TxRecord> {
            val icpQuery = ICPQuery(
                methodName = "getUserTransactions",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(who),
                    ValueToEncode(start),
                    ValueToEncode(limit)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * getUserTransactionAmount: (who: principal) -> (nat) query;
         */
        // Returns total number of transactions related to the user who.
        suspend fun getUserTransactionAmount (
            who: ICPPrincipalApiModel,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger {
            val icpQuery = ICPQuery(
                methodName = "getUserTransactionAmount",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(who)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * transfer: (to: principal, value: nat) -> (TxReceipt);
         */
        // Transfers value amount of tokens to user to, returns a TxReceipt which contains the transaction index or an error message.
        suspend fun transfer (
            to: ICPPrincipalApiModel,
            value: BigInteger,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): TxReceipt {
            val icpQuery = ICPQuery(
                methodName = "transfer",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(to),
                    ValueToEncode(value)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * transferFrom: (from: principal, to: principal, value: nat) -> (TxReceipt);
         */
        // Transfers value amount of tokens from user from to user to,
        // this method allows canister smart contracts to transfer tokens on your behalf,
        // it returns a TxReceipt which contains the transaction index or an error message.
        suspend fun transferFrom (
            from: ICPPrincipalApiModel,
            to: ICPPrincipalApiModel,
            value: BigInteger,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): TxReceipt {
            val icpQuery = ICPQuery(
                methodName = "transferFrom",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(from),
                    ValueToEncode(to),
                    ValueToEncode(value)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        /**
         * approve: (spender: principal, value: nat) -> (TxReceipt);
         */
        // Allows spender to withdraw tokens from your account, up to the value amount.
        // If it is called again it overwrites the current allowance with value.
        // There is no upper limit for value.
        suspend fun approve (
            spender: ICPPrincipalApiModel,
            value: BigInteger,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): TxReceipt {
            val icpQuery = ICPQuery(
                methodName = "approve",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(spender),
                    ValueToEncode(value)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = ICPRequestCertification.Certified
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }
    }
}