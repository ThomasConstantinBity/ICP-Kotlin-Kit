package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.generated_file.NNS_SNS_W

interface SNSCachedService {
    suspend fun deployedSNSes(): List<NNS_SNS_W.DeployedSns>
}