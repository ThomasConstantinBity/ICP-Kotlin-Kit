package com.bity.icp_kotlin_kit.domain.repository

interface ExplorerURLRepository {
    fun getExplorerURL(transactionId: String): String
}