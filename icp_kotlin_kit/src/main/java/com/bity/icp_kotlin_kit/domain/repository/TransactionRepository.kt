package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.token_transaction.ICPTokenTransaction

internal interface TransactionRepository {
    suspend fun fetchAllTransactions(account: ICPAccount): List<ICPTokenTransaction>
}