package com.bity.icp_kotlin_kit.domain.use_case.token

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPTokenBalance
import com.bity.icp_kotlin_kit.domain.repository.TokenCachedRepository

class FetchTokensBalance internal constructor(
    private val tokenRepository: TokenCachedRepository
) {

    suspend operator fun invoke(principal: ICPPrincipal): List<ICPTokenBalance> =
        tokenRepository.fetchAccountTokensBalance(principal)

}