package com.bity.icp_kotlin_kit.data.repository.url

import com.bity.icp_kotlin_kit.domain.repository.ExplorerURLRepository

class ICPExplorerURLRepository: ExplorerURLRepository {
    override fun getExplorerURL(transactionId: String): String =
        "https://dashboard.internetcomputer.org/transaction/$transactionId"
}