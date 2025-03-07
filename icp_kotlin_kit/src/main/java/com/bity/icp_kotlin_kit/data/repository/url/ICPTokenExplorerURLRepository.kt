package com.bity.icp_kotlin_kit.data.repository.url

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.ExplorerURLRepository

class ICPTokenExplorerURLRepository(
    private val rootCanisterId: ICPPrincipal
): ExplorerURLRepository {
    override fun getExplorerURL(transactionId: String): String =
        "https://dashboard.internetcomputer.org/sns/${rootCanisterId.string}/transaction/$transactionId"
}