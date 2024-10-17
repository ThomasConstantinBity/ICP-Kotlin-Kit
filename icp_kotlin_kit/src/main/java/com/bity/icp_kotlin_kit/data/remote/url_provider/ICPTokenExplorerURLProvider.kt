package com.bity.icp_kotlin_kit.data.remote.url_provider

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.provider.ExplorerURLProvider

class ICPTokenExplorerURLProvider(
    private val rootCanisterId: ICPPrincipal
): ExplorerURLProvider {
    override fun getExplorerURL(transactionId: String): String =
        "https://dashboard.internetcomputer.org/sns/${rootCanisterId.string}/transaction/$transactionId"
}