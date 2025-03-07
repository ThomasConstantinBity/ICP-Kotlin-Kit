package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.ICPToken

internal interface TokensCachedService {
    suspend fun fetchAllTokens(): List<ICPToken>
}