package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.provider.ExplorerURLProvider
import com.bity.icp_kotlin_kit.domain.provider.ICPTransactionProvider

internal interface TransactionProviderFactory {
    suspend fun getTransactionProvider(token: ICPToken): ICPTransactionProvider?
    suspend fun getExplorerURLProvider(token: ICPToken): ExplorerURLProvider?
}