package com.bity.icp_kotlin_kit.domain.service

interface ExplorerURLService {
    fun getExplorerURL(transactionId: String): String
}