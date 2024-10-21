package com.bity.icp_kotlin_kit.domain.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.domain.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.request.PollingValues
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification

/**
 * File generated using ICP Kotlin Kit Plugin
 */
object NNSICPIndexCanister {

    class Account(
        val owner: ICPPrincipalApiModel,
        val subaccount: Array<UByte>?
    )

    class GetAccountIdentifierTransactionsArgs(
        val max_results: ULong,
        val start: ULong?,
        val account_identifier: String
    )

    class GetAccountTransactionsArgs(
        // The txid of the last transaction seen by the client.
        // If None then the results will start from the most recent
        // txid.
        val account: Account,
        // Maximum number of transactions to fetch.
        val start: BigInteger?,
        val max_results: BigInteger
    )

    class GetAccountIdentifierTransactionsError(
        val message: String
    )

    class GetAccountIdentifierTransactionsResponse(
        val balance: ULong,
        val transactions: Array<TransactionWithId>,
        val oldest_tx_id: ULong?
    )

    class GetBlocksRequest(
        val start: BigInteger,
        val length: BigInteger
    )

    class GetBlocksResponse(
        val blocks: kotlin.Array<Array<UByte>>,
        val chain_length: ULong
    )

    class HttpRequest(
        val url: String,
        val method: String,
        val body: kotlin.Array<UByte>,
        val headers: kotlin.Array<HttpRequestHeaders>
    ) {
        class HttpRequestHeaders(
            val string_1: String,
            val string_2: String
        )
    }

    class HttpResponse(
        val body: kotlin.Array<UByte>,
        val headers: kotlin.Array<HttpResponseHeaders>,
        val status_code: UShort
    ) {
        class HttpResponseHeaders(
            val string_1: String,
            val string_2: String
        )
    }

    class InitArg(
        val ledger_id: ICPPrincipalApiModel
    )

    sealed class Operation {

        class Approve(
            val fee: Tokens,
            val from: String,
            val allowance: Tokens,
            val expires_at: TimeStamp?,
            val spender: String,
            val expected_allowance: Tokens?
        ): Operation()

        class Burn(
            val from: String,
            val amount: Tokens,
            val spender: String?
        ): Operation()

        class Mint(
            val to: String,
            val amount: Tokens
        ): Operation()

        class Transfer(
            val to: String,
            val fee: Tokens,
            val from: String,
            val amount: Tokens,
            val spender: String?
        ): Operation()
    }
    sealed class GetAccountIdentifierTransactionsResult {
        class Ok(
            val getAccountIdentifierTransactionsResponse: GetAccountIdentifierTransactionsResponse
        ): GetAccountIdentifierTransactionsResult()
        class Err(
            val getAccountIdentifierTransactionsError: GetAccountIdentifierTransactionsError
        ): GetAccountIdentifierTransactionsResult()
    }

    class Status(
        val num_blocks_synced: ULong
    )

    class TimeStamp(
        val timestamp_nanos: ULong
    )

    class Tokens(
        val e8s: ULong
    )

    /**
     * type Transaction = record {
     *   memo : nat64;
     *   icrc1_memo : opt vec nat8;
     *   operation : Operation;
     *   created_at_time : opt TimeStamp;
     *   timestamp : opt TimeStamp;
     * };
     */
    class Transaction(
        val memo: ULong,
        val icrc1_memo: Array<UByte>?,
        val operation: Operation,
        val created_at_time: TimeStamp?,
        val timestamp: TimeStamp?
    )

    class TransactionWithId(
        val id: ULong,
        val transaction: Transaction
    )

    class NNSICPIndexCanisterService(
        private val canister: ICPPrincipal
    ) {
        suspend fun get_account_identifier_balance (
            string: String,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): ULong {
            val icpQuery = ICPQuery(
                methodName = "get_account_identifier_balance",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(string),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decodeNotNull(result)
        }

        suspend fun get_account_identifier_transactions (
            getAccountIdentifierTransactionsArgs: GetAccountIdentifierTransactionsArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetAccountIdentifierTransactionsResult {
            val icpQuery = ICPQuery(
                methodName = "get_account_identifier_transactions",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(getAccountIdentifierTransactionsArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decodeNotNull(result)
        }

        suspend fun get_account_transactions (
            getAccountTransactionsArgs: GetAccountTransactionsArgs,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetAccountIdentifierTransactionsResult {
            val icpQuery = ICPQuery(
                methodName = "get_account_transactions",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(getAccountTransactionsArgs),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decodeNotNull(result)
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
            val result = icpQuery(
                args = listOf(getBlocksRequest),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decodeNotNull(result)
        }

        suspend fun http_request (
            httpRequest: HttpRequest,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): HttpResponse {
            val icpQuery = ICPQuery(
                methodName = "http_request",
                canister = canister
            )
            val result = icpQuery(
                args = listOf(httpRequest),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decodeNotNull(result)
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
            val result = icpQuery(
                args = null,
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decodeNotNull(result)
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
            val result = icpQuery(
                args = null,
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decodeNotNull(result)
        }

        suspend fun icrc1_balance_of (
            account: Account,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): ULong {
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
            return com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder.decodeNotNull(result)
        }
    }
}

