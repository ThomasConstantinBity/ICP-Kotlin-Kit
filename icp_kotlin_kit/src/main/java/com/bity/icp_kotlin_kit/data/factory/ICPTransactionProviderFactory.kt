package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.datasource.api.model.toDomainModel
import com.bity.icp_kotlin_kit.data.remote.transaction_provider.ICPICRC1IndexTransactionProvider
import com.bity.icp_kotlin_kit.data.remote.transaction_provider.ICPIndexTransactionProvider
import com.bity.icp_kotlin_kit.data.remote.url_provider.ICPExplorerURLProvider
import com.bity.icp_kotlin_kit.data.remote.url_provider.ICPTokenExplorerURLProvider
import com.bity.icp_kotlin_kit.domain.provider.ICPTransactionProvider
import com.bity.icp_kotlin_kit.domain.generated_file.NNS_SNS_W
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.provider.ExplorerURLProvider
import com.bity.icp_kotlin_kit.provideNNSSNSWService

internal class ICPTransactionProviderFactory private constructor(
    private val service: NNS_SNS_W.nns_sns_wService
) {

    constructor(): this(provideNNSSNSWService())

    private var cachedSNSes: List<NNS_SNS_W.DeployedSns> = emptyList()

    suspend fun getTransactionProvider(token: ICPToken): ICPTransactionProvider? {
        // TODO: Support DIP20 tokens
        if(token.canister == ICPSystemCanisters.Ledger.icpPrincipal)
            return ICPIndexTransactionProvider(token)
        val index = findSNS(token.canister)?.index_canister_id
            ?.toDomainModel()
            ?: return null
        return ICPICRC1IndexTransactionProvider(
            icpToken = token,
            indexCanister = index
        )
    }

    suspend fun getExplorerURLProvider(token: ICPToken): ExplorerURLProvider? {
        if(token.canister == ICPSystemCanisters.Ledger.icpPrincipal)
            return ICPExplorerURLProvider()
        val rootCanisterId = findSNS(token.canister)?.root_canister_id
            ?: return null
        return ICPTokenExplorerURLProvider(rootCanisterId.toDomainModel())
    }

    private suspend fun findSNS(tokenCanister: ICPPrincipal): NNS_SNS_W.DeployedSns? {
        val deployed = deployedSNSes()
        return deployed.firstOrNull {
            it.root_canister_id?.toDomainModel() == tokenCanister
                    || it.governance_canister_id?.toDomainModel() == tokenCanister
                    || it.index_canister_id?.toDomainModel() == tokenCanister
                    || it.swap_canister_id?.toDomainModel() == tokenCanister
                    || it.ledger_canister_id?.toDomainModel() == tokenCanister
        }
    }

    private suspend fun deployedSNSes(): List<NNS_SNS_W.DeployedSns> {
        if(cachedSNSes.isNotEmpty()) return cachedSNSes
        cachedSNSes = service.list_deployed_snses().instances.toList()
        return cachedSNSes
    }
}