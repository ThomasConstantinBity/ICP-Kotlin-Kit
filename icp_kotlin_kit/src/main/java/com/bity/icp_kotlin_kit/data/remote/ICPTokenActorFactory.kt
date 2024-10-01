package com.bity.icp_kotlin_kit.data.remote

import com.bity.icp_kotlin_kit.data.remote.token_actor.DIP20TokenActor
import com.bity.icp_kotlin_kit.data.remote.token_actor.ICPTokenActor
import com.bity.icp_kotlin_kit.data.remote.token_actor.ICRC1TokenActor
import com.bity.icp_kotlin_kit.domain.generated_file.DIP20
import com.bity.icp_kotlin_kit.domain.generated_file.ICRC1
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard

internal object ICPTokenActorFactory {

    fun createActor(
        standard: ICPTokenStandard,
        canister: ICPPrincipal
    ): ICPTokenActor? =
        when(standard) {
            ICPTokenStandard.DIP20 -> DIP20TokenActor(
                service = DIP20.DIP20Service(
                    canister = canister
                )
            )
            ICPTokenStandard.ICRC1,
            ICPTokenStandard.ICRC2 -> ICRC1TokenActor(
                service = ICRC1.ICRC1Service(
                    canister = canister
                )
            )
            ICPTokenStandard.XTC,
            ICPTokenStandard.WICP,
            ICPTokenStandard.EXT,
            ICPTokenStandard.ICP,
            ICPTokenStandard.ROSETTA,
            ICPTokenStandard.DRC20 -> null
        }
}