package com.bity.icp_kotlin_kit.data.remote.nft_actor

import com.bity.icp_kotlin_kit.domain.generated_file.Account
import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.provider.NFTActor
import java.math.BigInteger

internal class ICRC7NFTActor(
    private val service: DBANFTService
): NFTActor {

    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> {
        val result = service.icrc7_tokens_of(
            account = Account(
                owner = principal.toDataModel(),
                subaccount = null
            ),
            prev = BigInteger.ZERO,
            take = BigInteger("1000")
        )
        TODO()
    }
}