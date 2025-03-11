package com.bity.icp_kotlin_kit.domain.use_case.token

import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.repository.TokenCachedRepository
import java.math.BigInteger

class FetchTokenTransactionFee internal constructor(
    private val tokenRepository: TokenCachedRepository
) {

    suspend operator fun invoke(token: ICPToken): BigInteger =
        tokenRepository.fee(token)

}