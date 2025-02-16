package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.service.NFTService

internal interface NFTServiceFactory {
    fun createNFTService(collection: ICPNftCollection): NFTService?
}