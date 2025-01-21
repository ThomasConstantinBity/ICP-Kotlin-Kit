package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransaction

internal interface ICPTransactionService {
    suspend fun getAllTransactions(account: ICPAccount): List<ICPTokenTransaction>
}