package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.domain.generated_file.LedgerCanister
import com.bity.icp_kotlin_kit.domain.generated_file.LedgerCanister.ArchivedBlocksRange
import com.bity.icp_kotlin_kit.domain.generated_file.LedgerCanister.GetBlocksArgs
import com.bity.icp_kotlin_kit.domain.model.error.QueryBlockError
import com.bity.icp_kotlin_kit.domain.model.error.TransferError
import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlock
import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlockTransaction
import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlockTransactionOperation
import com.bity.icp_kotlin_kit.domain.repository.LedgerCanisterRepository
import com.bity.icp_kotlin_kit.domain.request.TransferICPRequest
import com.bity.icp_kotlin_kit.util.ext_function.timestampNanosToDate
import com.bity.icp_kotlin_kit.util.icpTimestampNow
import java.math.BigInteger

internal class LedgerCanisterRepositoryImpl(
    private val ledgerCanisterService: LedgerCanister.LedgerCanisterService
): LedgerCanisterRepository {

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun transferICP(request: TransferICPRequest): BigInteger {
        val transferArgs = LedgerCanister.TransferArgs(
            to = request.receivingAddress.hexToByteArray(),
            fee = LedgerCanister.Tokens(request.fee.toLong().toULong()),
            memo = request.memo,
            from_subaccount = request.sendingAccount.subAccountId,
            created_at_time = LedgerCanister.TimeStamp(icpTimestampNow().toULong()),
            amount = LedgerCanister.Tokens(request.amount.toLong().toULong())
        )

        val result = ledgerCanisterService.transfer(
            transferArgs = transferArgs,
            sender = request.signingPrincipal
        )

        when(result) {
            is LedgerCanister.Result_6.Err -> throw result.transferError_1.toDataModel()
            is LedgerCanister.Result_6.Ok -> return BigInteger(result.uLong.toString())
        }
    }

    override suspend fun queryBlocks(start: ULong, length: ULong): List<ICPBlock> {
        val getBlocksArgs = GetBlocksArgs(
            start = start,
            length = length
        )
        val response = ledgerCanisterService.query_blocks(getBlocksArgs)
        return when {

            response.blocks.isNotEmpty() -> response.blocks.mapNotNull { it.toDomainModel() }

            // TODO, can use MapAsync to improve performance
            response.archived_blocks.isNotEmpty() -> response.archived_blocks
                .map { handleCallback(it) }
                .flatten()
                .mapNotNull { it.toDomainModel() }

            else -> throw QueryBlockError.BlockNotFound(start)
        }
    }

    private suspend fun handleCallback(
        archivedBlocksRange: ArchivedBlocksRange
    ): List<LedgerCanister.CandidBlock> {

        val getBlocksArgs = GetBlocksArgs(
            start = archivedBlocksRange.start,
            length = archivedBlocksRange.length
        )

        when(val callbackResult = archivedBlocksRange.callback(getBlocksArgs)) {
            is LedgerCanister.Result_4.Err ->
                throw callbackResult.toDomainModel()
            is LedgerCanister.Result_4.Ok ->
                return callbackResult.blockRange.blocks.toList()
        }

    }
}

private fun LedgerCanister.TransferError_1.toDataModel(): TransferError =
    when(this) {
        is LedgerCanister.TransferError_1.BadFee ->
            TransferError.BadFee(expected_fee)
        is LedgerCanister.TransferError_1.InsufficientFunds ->
            TransferError.InsufficientFunds(balance)
        LedgerCanister.TransferError_1.TxCreatedInFuture ->
            TransferError.TxCreatedInFuture
        is LedgerCanister.TransferError_1.TxDuplicate ->
            TransferError.TxDuplicate(duplicate_of)
        is LedgerCanister.TransferError_1.TxTooOld ->
            TransferError.TxTooOld(allowed_window_nanos)
    }

private fun LedgerCanister.CandidBlock.toDomainModel(): ICPBlock? {
    transaction.operation ?: return null
    val icpBlockTransaction = transaction.toDomainModel()
        ?: return null
    return ICPBlock(
        parentHash = parent_hash,
        timestampNanos = timestamp.timestamp_nanos,
        transaction = icpBlockTransaction
    )
}

private fun LedgerCanister.CandidTransaction.toDomainModel(): ICPBlockTransaction? {
    val icpBlockTransactionOperation = operation?.toDomainModel()
        ?: return null
    return ICPBlockTransaction(
        memo = memo,
        operation = icpBlockTransactionOperation,
        createdNanos = created_at_time.timestamp_nanos
    )
}

private fun LedgerCanister.CandidOperation.toDomainModel(): ICPBlockTransactionOperation =
    when(this) {

        is LedgerCanister.CandidOperation.Approve ->
            ICPBlockTransactionOperation.Approve(
                from = from,
                allowance = allowance_e8s,
                expectedAllowance = expected_allowance?.e8s?.let { BigInteger(it.toString()) },
                fee = BigInteger(fee.e8s.toString()),
                expiresAtNanos = expires_at?.timestamp_nanos,
                spender = spender
            )

        is LedgerCanister.CandidOperation.Burn ->
            ICPBlockTransactionOperation.Burn(
                from = from,
                amount = BigInteger(amount.e8s.toString()),
                spender = spender
            )

        is LedgerCanister.CandidOperation.Mint ->
            ICPBlockTransactionOperation.Mint(
                to = to,
                amount = BigInteger(amount.e8s.toString())
            )

        is LedgerCanister.CandidOperation.Transfer ->
            ICPBlockTransactionOperation.Transfer(
                from = from,
                to = to,
                amount = BigInteger(amount.e8s.toString()),
                fee = BigInteger(fee.e8s.toString()),
                spender = spender
            )
    }

private fun LedgerCanister.Result_4.Err.toDomainModel(): QueryBlockError =
    when(getBlocksError) {
        is LedgerCanister.GetBlocksError.BadFirstBlockIndex ->
            QueryBlockError.BadFirstBlockIndex(
                requestedIndex = getBlocksError.requested_index,
                firstValidIndex = getBlocksError.first_valid_index
            )
        is LedgerCanister.GetBlocksError.Other ->
            QueryBlockError.Other(
                errorMessage = getBlocksError.error_message,
                errorCode = getBlocksError.error_code
            )
    }
