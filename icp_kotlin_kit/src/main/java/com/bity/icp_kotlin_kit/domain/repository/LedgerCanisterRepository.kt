package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.icp_block.ICPBlock
import com.bity.icp_kotlin_kit.domain.request.TransferICPRequest
import java.math.BigInteger

internal interface LedgerCanisterRepository {
    suspend fun transferICP(request: TransferICPRequest): BigInteger
    suspend fun queryBlocks(start: ULong, length: ULong): List<ICPBlock>
}