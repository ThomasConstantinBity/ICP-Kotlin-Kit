package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.datasource.api.model.toDomainModel
import com.bity.icp_kotlin_kit.data.service.transaction.ICPICRC1IndexTransactionRepository
import com.bity.icp_kotlin_kit.data.service.transaction.ICPIndexTransactionRepository
import com.bity.icp_kotlin_kit.data.repository.url.ICPExplorerURLRepository
import com.bity.icp_kotlin_kit.data.repository.url.ICPTokenExplorerURLRepository
import com.bity.icp_kotlin_kit.domain.factory.TransactionProviderFactory
import com.bity.icp_kotlin_kit.domain.generated_file.NNSICPIndexCanister
import com.bity.icp_kotlin_kit.domain.repository.ICPTransactionRepository
import com.bity.icp_kotlin_kit.domain.generated_file.NNS_SNS_W
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.repository.ExplorerURLRepository
import com.bity.icp_kotlin_kit.domain.repository.SNSCachedRepository

internal class TransactionProviderFactoryImpl(
    private val snsService: SNSCachedRepository,
    private val indexService: NNSICPIndexCanister.NNSICPIndexCanisterService
): TransactionProviderFactory {

    override suspend fun getTransactionProvider(token: ICPToken): ICPTransactionRepository? {
        // TODO: Support DIP20 tokens
        if(token.canister == ICPSystemCanisters.Ledger.icpPrincipal)
            return ICPIndexTransactionRepository(
                icpToken = token,
                indexCanister = indexService
            )
        val index = findSNS(token.canister)?.index_canister_id
            ?.toDomainModel()
            ?: return null
        return ICPICRC1IndexTransactionRepository(
            icpToken = token,
            indexCanister = index
        )
    }

    override suspend fun getExplorerURLProvider(token: ICPToken): ExplorerURLRepository? {
        if(token.canister == ICPSystemCanisters.Ledger.icpPrincipal)
            return ICPExplorerURLRepository()
        val rootCanisterId = findSNS(token.canister)?.root_canister_id
            ?: return null
        return ICPTokenExplorerURLRepository(rootCanisterId.toDomainModel())
    }

    private suspend fun findSNS(tokenCanister: ICPPrincipal): NNS_SNS_W.DeployedSns? {
        return snsService.deployedSNSes().firstOrNull {
            it.root_canister_id?.toDomainModel() == tokenCanister
                    || it.governance_canister_id?.toDomainModel() == tokenCanister
                    || it.index_canister_id?.toDomainModel() == tokenCanister
                    || it.swap_canister_id?.toDomainModel() == tokenCanister
                    || it.ledger_canister_id?.toDomainModel() == tokenCanister
        }
    }
}