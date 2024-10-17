package com.bity.icp_kotlin_kit.domain.model.icp_block

class ICPBlock(
    val parentHash: ByteArray,
    val timestamp: ULong,
    val transaction: ICPBlockTransaction
)