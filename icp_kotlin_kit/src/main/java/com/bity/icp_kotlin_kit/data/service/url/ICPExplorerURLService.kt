package com.bity.icp_kotlin_kit.data.service.url

import com.bity.icp_kotlin_kit.domain.service.ExplorerURLService

class ICPExplorerURLService: ExplorerURLService {
    override fun getExplorerURL(transactionId: String): String =
        "https://dashboard.internetcomputer.org/transaction/$transactionId"
}