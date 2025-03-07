package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransaction

interface TransactionService {
    suspend fun fetchTokenTransactions(
        account: ICPAccount,
        token: ICPToken
    ): List<ICPTokenTransaction>
    suspend fun fetchTokenTransactions(
        account: ICPAccount,
        tokenCanister: ICPPrincipal
    ): List<ICPTokenTransaction>
    suspend fun explorerUrl(
        tokenCanister: ICPPrincipal,
        transactionIndex: String
    ): String?
}