package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.ICPTokenBalance
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import java.math.BigInteger

interface TokenRepository {
    suspend fun fetchAllTokens(): List<ICPToken>
    suspend fun fetchTokensBalance(principal: ICPPrincipal): List<ICPTokenBalance>
    suspend fun fee(token: ICPToken): BigInteger
    suspend fun send(transferArgs: ICPTokenTransferArgs): ICPTokenTransfer
}