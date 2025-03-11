package com.bity.icp_kotlin_kit.domain.model.error

import com.bity.icp_kotlin_kit.data.generated_file.LedgerCanister.Tokens

sealed class LedgerCanisterError(msg: String? = null): Error(msg)

sealed class TransferError(msg: String? = null): LedgerCanisterError(msg) {
    data class TxTooOld(val allowedWindowNanos: ULong): TransferError()
    data class BadFee(val expectedFee: Tokens): TransferError()
    data class TxDuplicate(val duplicateOf: ULong): TransferError()
    data object TxCreatedInFuture : TransferError()
    data class InsufficientFunds(val balance: Tokens): TransferError()
}

sealed class QueryBlockError(msg: String? = null): LedgerCanisterError(msg) {
    class BlockNotFound(blockIndex: ULong): QueryBlockError("Block $blockIndex not found")
    class BadFirstBlockIndex(
        requestedIndex: ULong,
        firstValidIndex: ULong
    ): QueryBlockError(
        "Requested block index $requestedIndex is out of range, first valid block index is $firstValidIndex"
    )
    class Other(
        errorMessage: String,
        errorCode: ULong
    ): QueryBlockError("[$errorCode]: $errorMessage")
}