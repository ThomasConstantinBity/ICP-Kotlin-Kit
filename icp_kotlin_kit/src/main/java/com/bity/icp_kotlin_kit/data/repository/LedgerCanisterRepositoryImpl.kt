package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.domain.generated_file.LedgerCanister
import com.bity.icp_kotlin_kit.domain.model.error.TransferError
import com.bity.icp_kotlin_kit.domain.repository.LedgerCanisterRepository
import com.bity.icp_kotlin_kit.domain.request.TransferICPRequest
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
        when(val result = ledgerCanisterService.transfer(transferArgs)) {
            is LedgerCanister.Result_6.Err -> throw result.transferError_1.toDataModel()
            is LedgerCanister.Result_6.Ok -> return BigInteger(result.uLong.toString())
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