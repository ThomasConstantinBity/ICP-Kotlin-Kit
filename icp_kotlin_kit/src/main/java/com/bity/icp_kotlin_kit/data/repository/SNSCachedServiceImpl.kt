package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.domain.generated_file.NNS_SNS_W
import com.bity.icp_kotlin_kit.domain.repository.SNSCachedService

internal class SNSCachedServiceImpl (
    private val service: NNS_SNS_W.nns_sns_wService
): SNSCachedService {

    private var cachedSNSes: List<NNS_SNS_W.DeployedSns> = emptyList()

    override suspend fun deployedSNSes(): List<NNS_SNS_W.DeployedSns> {
        if(cachedSNSes.isNotEmpty()) return cachedSNSes
        cachedSNSes = service.list_deployed_snses().instances.toList()
        return cachedSNSes
    }
}