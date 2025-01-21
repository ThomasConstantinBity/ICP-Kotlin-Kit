package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.service.token.DIP20TokenService
import com.bity.icp_kotlin_kit.domain.service.ICPTokenService
import com.bity.icp_kotlin_kit.data.service.token.ICRC1TokenService
import com.bity.icp_kotlin_kit.domain.factory.TokenServiceFactory
import com.bity.icp_kotlin_kit.domain.generated_file.DIP20
import com.bity.icp_kotlin_kit.domain.generated_file.ICRC1
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard

internal class TokenServiceFactoryImpl: TokenServiceFactory {

    override fun createActor(
        standard: ICPTokenStandard,
        canister: ICPPrincipal
    ): ICPTokenService? =
        when(standard) {
            ICPTokenStandard.DIP20 -> DIP20TokenService(
                service = DIP20.DIP20Service(
                    canister = canister
                )
            )
            ICPTokenStandard.ICP,
            ICPTokenStandard.ICRC1,
            ICPTokenStandard.ICRC2 -> ICRC1TokenService(
                service = ICRC1.ICRC1Service(
                    canister = canister
                )
            )
            ICPTokenStandard.XTC,
            ICPTokenStandard.WICP,
            ICPTokenStandard.EXT,
            ICPTokenStandard.ROSETTA,
            ICPTokenStandard.DRC20 -> null
        }
}