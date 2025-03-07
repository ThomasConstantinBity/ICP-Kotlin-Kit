package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import java.math.BigInteger

interface TokenRepository {
    suspend fun fetchAllTokens(): List<ICPToken>
    suspend fun fetchTokenBalance(
        standard: ICPTokenStandard,
        canister: ICPPrincipal,
        principal: ICPPrincipal
    ): BigInteger?
    suspend fun fee(token: ICPToken): BigInteger
    suspend fun send(transferArgs: ICPTokenTransferArgs): ICPTokenTransfer
}