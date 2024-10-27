package com.bity.icp_kotlin_kit.domain.model.icp_block

import java.math.BigInteger
import java.util.Date

sealed class ICPBlockTransactionOperation(
    val amount: BigInteger,
    val fee: BigInteger? = null
) {

    class Approve(
        val from: ByteArray,
        val allowance: BigInteger,
        val expectedAllowance: BigInteger?,
        fee: BigInteger,
        val expiresAtNanos: ULong?,
        val spender: ByteArray
    ): ICPBlockTransactionOperation(
        amount = allowance,
        fee = fee
    )

    class Burn(
        val from: ByteArray,
        amount: BigInteger,
        val spender: ByteArray?
    ): ICPBlockTransactionOperation(
        amount = amount
    )

    class Mint(
        val to: ByteArray,
        amount: BigInteger
    ): ICPBlockTransactionOperation(
        amount = amount
    )

    class Transfer(
        val from: ByteArray,
        val to: ByteArray,
        amount: BigInteger,
        fee: BigInteger,
        val spender: ByteArray?
    ): ICPBlockTransactionOperation(
        amount = amount,
        fee = fee
    )
}