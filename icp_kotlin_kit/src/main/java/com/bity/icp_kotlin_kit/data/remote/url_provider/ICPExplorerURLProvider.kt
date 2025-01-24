package com.bity.icp_kotlin_kit.data.remote.url_provider

import com.bity.icp_kotlin_kit.domain.provider.ExplorerURLProvider

class ICPExplorerURLProvider: ExplorerURLProvider {
    override fun getExplorerURL(transactionId: String): String =
        "https://dashboard.internetcomputer.org/transaction/$transactionId"
}