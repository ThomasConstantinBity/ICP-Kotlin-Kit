package com.bity.icp_kotlin_kit.data.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.request.PollingValues
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification

/**
 * File generated using ICP Kotlin Kit Plugin
 */

typealias ICRC1IndexCanisterTokens = BigInteger
typealias ICRC1IndexCanisterMap = Array<ICRC1IndexCanister>
typealias Block = ICRC1IndexCanister.Value
typealias BlockIndex = BigInteger
private typealias SubAccount = ByteArray

object ICRC1IndexCanister {

    class InitArg(
        val ledger_id: ICPPrincipalApiModel,
        // The interval in seconds in which to retrieve blocks from the ledger. A lower value makes the index more
        // responsive in showing new blocks, but increases the consumption of cycles of both the index and ledger canisters.
        // A higher values means that it takes longer for new blocks to show up in the index.
        val retrieve_blocks_from_ledger_interval_seconds: ULong?
    )

    class UpgradeArg(
        val ledger_id: ICPPrincipalApiModel?,
        // The interval in seconds in which to retrieve blocks from the ledger. A lower value makes the index more
        // responsive in showing new blocks, but increases the consumption of cycles of both the index and ledger canisters.
        // A higher values means that it takes longer for new blocks to show up in the index.
        val retrieve_blocks_from_ledger_interval_seconds: ULong?
    )

    sealed class IndexArg {
        class Init(
            val initArg: InitArg
        ): IndexArg()
        class Upgrade(
            val upgradeArg: UpgradeArg
        ): IndexArg()
    }

    class GetBlocksRequest(
        val start: BigInteger,
        val length: BigInteger
    )

    sealed class Value {

        class Blob(
            val byteArray: ByteArray
        ): Value()

        class Text(
            val string: String
        ): Value()

        class Nat(
            val bigInteger: BigInteger
        ): Value()

        class Nat64(
            val uLong: ULong
        ): Value()

        class Int(
            val bigInteger: BigInteger
        ): Value()

        class Array(
            val values: kotlin.Array<Value>
        ): Value()

        class Map(
            val map: ICRC1IndexCanisterMap
        ): Value()
    }

    class GetBlocksResponse(
        val chain_length: ULong,
        val blocks: Array<Block>
    )

    class Account(
        val owner: ICPPrincipalApiModel,
        val subaccount: SubAccount?
    )

    class Transaction(
        val burn: Burn?,
        val kind: String,
        val mint: Mint?,
        val approve: Approve?,
        val timestamp: ULong,
        val transfer: Transfer?
    )

    class Approve(
        val fee: BigInteger?,
        val from: Account,
        val memo: Array<UByte>?,
        val created_at_time: ULong?,
        val amount: BigInteger,
        val expected_allowance: BigInteger?,
        val expires_at: ULong?,
        val spender: Account
    )

    class Burn(
        val from: Account,
        val memo: Array<UByte>?,
        val created_at_time: ULong?,
        val amount: BigInteger,
        val spender: Account?
    )

    class Mint(
        val to: Account,
        val memo: Array<UByte>?,
        val created_at_time: ULong?,
        val amount: BigInteger
    )

    class Transfer(
        val to: Account,
        val fee: BigInteger?,
        val from: Account,
        val memo: Array<UByte>?,
        val created_at_time: ULong?,
        val amount: BigInteger,
        val spender: Account?
    )

    class GetAccountTransactionsArgs(
        // The txid of the last transaction seen by the client.
        // If None then the results will start from the most recent
        // txid.
        val account: Account,
        // Maximum number of transactions to fetch.
        val start: BlockIndex?,
        val max_results: BigInteger
    )

    class TransactionWithId(
        val id: BlockIndex,
        val transaction: Transaction
    )

    class GetTransactions(
        val balance: ICRC1IndexCanisterTokens,
        val transactions: Array<TransactionWithId>,
        // The txid of the oldest transaction the account has
        val oldest_tx_id: BlockIndex?
    )

    class GetTransactionsErr(
        val message: String
    )

    sealed class GetTransactionsResult {
        class Ok(
            val getTransactions: GetTransactions
        ): GetTransactionsResult()
        class Err(
            val getTransactionsErr: GetTransactionsErr
        ): GetTransactionsResult()
    }

    class ListSubaccountsArgs(
        val owner: ICPPrincipalApiModel,
        val start: SubAccount?
    )

    class Status(
        val num_blocks_synced: BlockIndex
    )

    /**
     * type FeeCollectorRanges = record {
     *     ranges : vec  record { Account; vec record { BlockIndex; BlockIndex } };
     * }
     */
    class FeeCollectorRanges(
        val ranges: Array<FeeCollectorRangesRanges>
    ) {
        class FeeCollectorRangesRanges(
            val account: Account,
            val unnamedClass0: Array<UnnamedClass0>
        ) {
            class UnnamedClass0(
                val blockIndex_1: BlockIndex,
                val blockIndex_2: BlockIndex
            )
        }
    }

    class ICRC1IndexCanisterService(
        private val canister: ICPPrincipal
    ) {
        suspend fun get_account_transactions (
            getAccountTransactionsArgs: GetAccountTransactionsArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetTransactionsResult {
            val icpQuery = ICPQuery(
                methodName = "get_account_transactions",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(getAccountTransactionsArgs)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_blocks (
            getBlocksRequest: GetBlocksRequest,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetBlocksResponse {
            val icpQuery = ICPQuery(
                methodName = "get_blocks",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(getBlocksRequest)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_fee_collectors_ranges (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): FeeCollectorRanges {
            val icpQuery = ICPQuery(
                methodName = "get_fee_collectors_ranges",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = null,
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun icrc1_balance_of (
            account: Account,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): ICRC1IndexCanisterTokens {
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
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun ledger_id (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): ICPPrincipalApiModel {
            val icpQuery = ICPQuery(
                methodName = "ledger_id",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = null,
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun list_subaccounts (
            listSubaccountsArgs: ListSubaccountsArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<SubAccount> {
            val icpQuery = ICPQuery(
                methodName = "list_subaccounts",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(listSubaccountsArgs)
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun status (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Status {
            val icpQuery = ICPQuery(
                methodName = "status",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = null,
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }
    }
}