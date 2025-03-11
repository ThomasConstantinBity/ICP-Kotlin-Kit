package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.data.generated_file.NNS_SNS_W

internal interface SNSCachedRepository {
    suspend fun deployedSNSes(): List<NNS_SNS_W.DeployedSns>
}