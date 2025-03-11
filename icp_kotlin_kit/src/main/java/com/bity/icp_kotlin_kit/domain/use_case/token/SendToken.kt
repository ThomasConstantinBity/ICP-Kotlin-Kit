package com.bity.icp_kotlin_kit.domain.use_case.token

import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.repository.TokenCachedRepository

class SendToken internal constructor(
    private val tokenRepository: TokenCachedRepository
) {

    suspend operator fun invoke(transferArgs: ICPTokenTransferArgs): ICPTokenTransfer =
        tokenRepository.send(transferArgs)

}