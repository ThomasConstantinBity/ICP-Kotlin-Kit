package com.bity.icp_kotlin_kit.data.service.url

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.service.ExplorerURLService

class ICPTokenExplorerURLService(
    private val rootCanisterId: ICPPrincipal
): ExplorerURLService {
    override fun getExplorerURL(transactionId: String): String =
        "https://dashboard.internetcomputer.org/sns/${rootCanisterId.string}/transaction/$transactionId"
}