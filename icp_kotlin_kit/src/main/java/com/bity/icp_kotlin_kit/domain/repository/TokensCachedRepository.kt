package com.bity.icp_kotlin_kit.domain.repository

import com.bity.icp_kotlin_kit.domain.model.ICPToken

internal interface TokensCachedRepository {
    suspend fun fetchAllTokens(): List<ICPToken>
}