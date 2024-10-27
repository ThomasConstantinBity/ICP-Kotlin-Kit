package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.remote.token_actor.DIP20TokenActor
import com.bity.icp_kotlin_kit.domain.provider.ICPTokenActor
import com.bity.icp_kotlin_kit.data.remote.token_actor.ICRC1TokenActor
import com.bity.icp_kotlin_kit.domain.factory.TokenActorFactory
import com.bity.icp_kotlin_kit.domain.generated_file.DIP20
import com.bity.icp_kotlin_kit.domain.generated_file.ICRC1
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard

internal class TokenActorFactoryImpl: TokenActorFactory {

    override fun createActor(
        standard: ICPTokenStandard,
        canister: ICPPrincipal
    ): ICPTokenActor? =
        when(standard) {
            ICPTokenStandard.DIP20 -> DIP20TokenActor(
                service = DIP20.DIP20Service(
                    canister = canister
                )
            )
            ICPTokenStandard.ICP,
            ICPTokenStandard.ICRC1,
            ICPTokenStandard.ICRC2 -> ICRC1TokenActor(
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