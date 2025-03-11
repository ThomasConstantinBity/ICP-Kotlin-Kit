package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPTokenMetadata
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import java.math.BigInteger

internal interface ICPTokenRepository {
    suspend fun fetchBalance(principal: ICPPrincipal): BigInteger
    suspend fun fetchMetadata(): ICPTokenMetadata
    suspend fun fee(): BigInteger
    suspend fun transfer(args: ICPTokenTransferArgs): ICPTokenTransfer
}