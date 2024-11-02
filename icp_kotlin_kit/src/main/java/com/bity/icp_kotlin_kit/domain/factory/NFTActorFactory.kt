package com.bity.icp_kotlin_kit.domain.factory

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.provider.NFTActor

internal interface NFTActorFactory {
    fun createActor(collection: ICPNftCollection): NFTActor?
}