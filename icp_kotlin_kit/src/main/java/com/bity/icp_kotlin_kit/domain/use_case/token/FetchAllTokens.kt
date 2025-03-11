package com.bity.icp_kotlin_kit.domain.use_case.token

import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.repository.TokenCachedRepository

class FetchAllTokens internal constructor(
    private val tokenRepository: TokenCachedRepository
) {

    suspend operator fun invoke(): List<ICPToken> =
        tokenRepository.fetchAllTokens()

}