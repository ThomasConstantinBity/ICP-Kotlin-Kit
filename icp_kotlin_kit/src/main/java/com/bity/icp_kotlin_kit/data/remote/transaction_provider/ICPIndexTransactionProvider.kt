package com.bity.icp_kotlin_kit.data.remote.transaction_provider

import com.bity.icp_kotlin_kit.domain.generated_file.NNSICPIndexCanister
import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.error.GetAllTransactionsException
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransaction
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransactionDestination
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransactionOperation
import com.bity.icp_kotlin_kit.domain.provider.ICPTransactionProvider
import com.bity.icp_kotlin_kit.icpIndexService
import java.math.BigInteger

internal class ICPIndexTransactionProvider(
    private val icpToken: ICPToken
): ICPTransactionProvider {

    override suspend fun getAllTransactions(account: ICPAccount): List<ICPTokenTransaction> {
        val getAccountTransactionsArgs = NNSICPIndexCanister.GetAccountTransactionsArgs(
            account = NNSICPIndexCanister.Account(
                owner = account.principal,
                subaccount = account.subAccountId.map { it.toUByte() }.toTypedArray()
            ),
            start = null,
            max_results = BigInteger("1000000")
        )
        val transactions = icpIndexService.get_account_transactions(getAccountTransactionsArgs)
        return when(transactions) {
            is NNSICPIndexCanister.GetAccountIdentifierTransactionsResult.Err ->
                throw transactions.getAccountIdentifierTransactionsError.toDataModel()
            is NNSICPIndexCanister.GetAccountIdentifierTransactionsResult.Ok ->
                transactions.getAccountIdentifierTransactionsResponse
                    .transactions
                    .map { it.toDomainModel() }
        }
    }

    private fun NNSICPIndexCanister.TransactionWithId.toDomainModel(): ICPTokenTransaction {

        val timestamp = transaction.timestamp?.timestamp_nanos?.toLong()
        val created = transaction.created_at_time?.timestamp_nanos?.toLong()
        val icrc1_memo = transaction.icrc1_memo
            ?.map { it.toByte() }
            ?.toByteArray()

        val operation: ICPTokenTransactionOperation
        val spender: ICPTokenTransactionDestination?
        val amount: BigInteger
        val fee: BigInteger

        when(val op = this.transaction.operation) {

            is NNSICPIndexCanister.Operation.Approve -> {
                operation = ICPTokenTransactionOperation.Approve(
                    from = ICPTokenTransactionDestination.AccountId(op.from),
                    expectedAllowance = op.expected_allowance?.let { BigInteger(it.e8s.toString()) },
                    expires = op.expires_at?.timestamp_nanos?.toLong()
                )
                amount = BigInteger(op.allowance.e8s.toString())
                fee = BigInteger(op.fee.e8s.toString())
                spender = ICPTokenTransactionDestination.AccountId(op.spender)
            }

            is NNSICPIndexCanister.Operation.Burn -> {
                operation = ICPTokenTransactionOperation.Burn(
                    from = ICPTokenTransactionDestination.AccountId(op.from)
                )
                amount = BigInteger(op.amount.e8s.toString())
                fee = BigInteger.ZERO
                spender = op.spender?.let {
                    ICPTokenTransactionDestination.AccountId(it)
                }
            }

            is NNSICPIndexCanister.Operation.Mint -> {
                operation = ICPTokenTransactionOperation.Mint(
                    to = ICPTokenTransactionDestination.AccountId(op.to)
                )
                amount = BigInteger(op.amount.e8s.toString())
                fee = BigInteger.ZERO
                spender = null
            }

            is NNSICPIndexCanister.Operation.Transfer -> {
                operation = ICPTokenTransactionOperation.Transfer(
                    from = ICPTokenTransactionDestination.AccountId(op.from),
                    to = ICPTokenTransactionDestination.AccountId(op.to)
                )
                amount = BigInteger(op.amount.e8s.toString())
                fee = BigInteger(op.fee.e8s.toString())
                spender = op.spender?.let { ICPTokenTransactionDestination.AccountId(it) }
            }
        }

        return ICPTokenTransaction(
            blockIndex = BigInteger(id.toString()),
            operation = operation,
            icrc1_memo = icrc1_memo,
            memo = null,
            amount = amount,
            fee = fee,
            created = created,
            timeStamp = timestamp,
            spender = spender,
            token = icpToken
        )
    }

    private fun NNSICPIndexCanister.GetAccountIdentifierTransactionsError.toDataModel(): Exception =
        GetAllTransactionsException(
            token = icpToken,
            errorMessage = message
        )
}
