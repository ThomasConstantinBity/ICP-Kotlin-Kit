package com.bity.icp_kotlin_kit.domain.provider

interface ExplorerURLProvider {
    fun getExplorerURL(transactionId: String): String
}