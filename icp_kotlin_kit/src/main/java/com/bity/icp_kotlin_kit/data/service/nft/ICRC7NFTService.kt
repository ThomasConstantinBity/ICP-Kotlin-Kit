package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.domain.generated_file.Account
import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.service.NFTService
import java.math.BigInteger

internal class ICRC7NFTService(
    private val canister: ICPPrincipal,
    private val service: DBANFTService,
): NFTService {

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
                name = "#${tokenId}",
                standard = ICPNftStandard.ICRC7,
                canister = canister
            )
        }
    }

    override suspend fun getNFTCollectionIds(
        prev: BigInteger?,
        take: BigInteger?
    ): List<BigInteger> {
        return service.icrc7_tokens(
            prev = prev,
            take = take
        ).toList()
    }

    override suspend fun fetchCollectionNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem> {
        TODO("Not yet implemented")
    }
}