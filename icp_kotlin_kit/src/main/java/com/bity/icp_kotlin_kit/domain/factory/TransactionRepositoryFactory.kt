package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.repository.ExplorerURLRepository
import com.bity.icp_kotlin_kit.domain.repository.TransactionRepository

internal interface TransactionRepositoryFactory {
    suspend fun getTransactionRepository(token: ICPToken): TransactionRepository?
    suspend fun getExplorerURLRepository(token: ICPToken): ExplorerURLRepository?
}