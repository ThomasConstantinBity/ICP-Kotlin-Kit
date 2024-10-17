package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.request.TransferICPRequest
import java.math.BigInteger

interface LedgerCanisterRepository {
    suspend fun transferICP(request: TransferICPRequest): BigInteger
}