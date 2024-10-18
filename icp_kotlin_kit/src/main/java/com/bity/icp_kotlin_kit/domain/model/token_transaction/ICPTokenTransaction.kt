package com.bity.icp_kotlin_kit.domain.model.token_transaction

import com.bity.icp_kotlin_kit.domain.model.ICPToken
import java.math.BigInteger

class ICPTokenTransaction(
    val blockIndex: BigInteger,
    val operation: ICPTokenTransactionOperation,
    val memo: BigInteger?,
    val icrc1Memo: ByteArray?,
    val amount: BigInteger,
    val fee: BigInteger,
    val createdNanos: ULong?,
    val timeStampNanos: ULong?,
    val spender: ICPTokenTransactionDestination?,
    val token: ICPToken
) {
    val from = when(operation) {
        is ICPTokenTransactionOperation.Approve -> operation.from
        is ICPTokenTransactionOperation.Burn -> operation.from
        is ICPTokenTransactionOperation.Mint -> null
        is ICPTokenTransactionOperation.Transfer -> operation.from
    }
    val to = when(operation) {
        is ICPTokenTransactionOperation.Approve,
        is ICPTokenTransactionOperation.Burn -> null
        is ICPTokenTransactionOperation.Mint -> operation.to
        is ICPTokenTransactionOperation.Transfer -> operation.to
    }
}
