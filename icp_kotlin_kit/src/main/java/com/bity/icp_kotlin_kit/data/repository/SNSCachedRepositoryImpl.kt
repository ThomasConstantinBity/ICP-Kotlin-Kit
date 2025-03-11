package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.generated_file.NNS_SNS_W
import com.bity.icp_kotlin_kit.domain.repository.SNSCachedRepository

internal class SNSCachedRepositoryImpl (
    private val canister: NNS_SNS_W.nns_sns_wService
): SNSCachedRepository {

    private var cachedSNSes: List<NNS_SNS_W.DeployedSns> = emptyList()

    override suspend fun deployedSNSes(): List<NNS_SNS_W.DeployedSns> {
        if(cachedSNSes.isNotEmpty()) return cachedSNSes
        cachedSNSes = this@SNSCachedRepositoryImpl.canister.list_deployed_snses().instances.toList()
        return cachedSNSes
    }
}