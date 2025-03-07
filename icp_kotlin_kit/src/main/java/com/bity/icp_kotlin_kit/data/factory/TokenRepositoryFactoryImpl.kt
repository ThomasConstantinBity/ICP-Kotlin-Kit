package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.repository.token.DIP20TokenRepository
import com.bity.icp_kotlin_kit.domain.repository.ICPTokenRepository
import com.bity.icp_kotlin_kit.data.repository.token.ICRC1TokenRepository
import com.bity.icp_kotlin_kit.domain.exception.TokenRepositoryException
import com.bity.icp_kotlin_kit.domain.factory.TokenRepositoryFactory
import com.bity.icp_kotlin_kit.domain.generated_file.DIP20
import com.bity.icp_kotlin_kit.domain.generated_file.ICRC1
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard

internal class TokenRepositoryFactoryImpl: TokenRepositoryFactory {

    override fun createRepository(
        standard: ICPTokenStandard,
        canister: ICPPrincipal
    ): ICPTokenRepository =
        when(standard) {
            ICPTokenStandard.DIP20 -> DIP20TokenRepository(
                canister = DIP20.DIP20Service(
                    canister = canister
                )
            )
            ICPTokenStandard.ICP,
            ICPTokenStandard.ICRC1,
            ICPTokenStandard.ICRC2 -> ICRC1TokenRepository(
                canister = ICRC1.ICRC1Service(
                    canister = canister
                )
            )
            ICPTokenStandard.XTC,
            ICPTokenStandard.WICP,
            ICPTokenStandard.EXT,
            ICPTokenStandard.ROSETTA,
            ICPTokenStandard.DRC20 ->
                throw TokenRepositoryException.NoTokenServiceFound(
                    standard = standard,
                    canister = canister
                )
        }
}