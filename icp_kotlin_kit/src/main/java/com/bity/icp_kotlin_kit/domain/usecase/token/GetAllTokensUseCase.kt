package com.bity.icp_kotlin_kit.domain.usecase.token

import com.bity.icp_kotlin_kit.di.tokenRepository
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository

class GetAllTokensUseCase internal constructor(
    private val repository: TokenRepository
) {

    constructor(): this(tokenRepository)

    suspend operator fun invoke(): List<ICPToken> =
        repository.getAllTokens()
}