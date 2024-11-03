package com.bity.icp_kotlin_kit.data.remote.nft_actor

import com.bity.icp_kotlin_kit.domain.generated_file.Account
import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.provider.NFTActor
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class ICRC7NFTActor(
    private val service: DBANFTService,
): NFTActor {

    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> {

        val tokenHoldings = service.icrc7_tokens_of(
            account = Account(
                owner = principal.toDataModel(),
                subaccount = null
            ),
            prev = null,
            take = null
        )

        val tokenMetadata = service.icrc7_token_metadata(tokenHoldings)
        return tokenHoldings.zip(tokenMetadata) { tokenId, tokenMetadata ->
            ICPNFTDetails(
                name = "#${tokenId}"
            )
        }
    }
}