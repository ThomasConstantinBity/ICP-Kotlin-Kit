package com.bity.icp_kotlin_kit.domain.usecase.token

import com.bity.icp_kotlin_kit.di.tokenRepository
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository

class TransferTokenUseCase private constructor(
    private val repository: TokenRepository
) {
    constructor(): this(tokenRepository)

    suspend operator fun invoke(transferArgs: ICPTokenTransferArgs): ICPTokenTransfer =
        repository.send(transferArgs)
}