package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.repository.ExplorerURLRepository
import com.bity.icp_kotlin_kit.domain.service.ICPTransactionService

internal interface TransactionProviderFactory {
    suspend fun getTransactionProvider(token: ICPToken): ICPTransactionService?
    suspend fun getExplorerURLProvider(token: ICPToken): ExplorerURLRepository?
}