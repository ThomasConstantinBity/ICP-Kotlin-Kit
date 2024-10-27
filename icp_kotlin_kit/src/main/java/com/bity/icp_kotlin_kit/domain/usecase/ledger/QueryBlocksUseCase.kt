package com.bity.icp_kotlin_kit.domain.usecase.ledger

import com.bity.icp_kotlin_kit.di.ledgerCanisterRepository
import com.bity.icp_kotlin_kit.domain.model.error.QueryBlockError
import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlock
import com.bity.icp_kotlin_kit.domain.repository.LedgerCanisterRepository

class QueryBlocksUseCase internal constructor(
    private val repository: LedgerCanisterRepository
) {

    constructor(): this(ledgerCanisterRepository)

    suspend operator fun invoke(
        startIndex: ULong,
        length: ULong
    ): List<ICPBlock> =
        repository.queryBlocks(
            start = startIndex,
            length = length
        )

    suspend fun queryBlock(index: ULong): ICPBlock =
        this(
            startIndex = index,
            length = 1UL
        ).firstOrNull()
            ?: throw QueryBlockError.BlockNotFound(index)
}