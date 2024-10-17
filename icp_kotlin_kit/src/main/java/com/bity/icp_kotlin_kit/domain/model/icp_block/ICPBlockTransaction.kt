package com.bity.icp_kotlin_kit.domain.model.icp_block

class ICPBlockTransaction(
    val memo: ULong,
    val createdNanos: ULong,
    val operation: ICPBlockTransactionOperation
)