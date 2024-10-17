package com.bity.icp_kotlin_kit.domain.model.error

import com.bity.icp_kotlin_kit.domain.generated_file.LedgerCanister.Tokens

sealed class LedgerCanisterError(msg: String? = null): Error(msg)

sealed class TransferError(msg: String? = null): LedgerCanisterError(msg) {
    data class TxTooOld(val allowedWindowNanos: ULong): TransferError()
    data class BadFee(val expectedFee: Tokens): TransferError()
    data class TxDuplicate(val duplicateOf: ULong): TransferError()
    data object TxCreatedInFuture : TransferError()
    data class InsufficientFunds(val balance: Tokens): TransferError()
}