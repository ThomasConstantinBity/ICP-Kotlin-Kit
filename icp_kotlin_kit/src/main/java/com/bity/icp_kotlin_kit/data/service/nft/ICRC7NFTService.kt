package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.domain.generated_file.Account
import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.domain.generated_file.Value
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTICRC7Metadata
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.service.NFTService
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

        TODO()

        /*val tokenMetadata = service.icrc7_token_metadata(tokenHoldings)
        return tokenHoldings.zip(tokenMetadata) { tokenId, tokenMetadata ->
            ICPNFTDetails(
                name = "#${tokenId}",
                standard = ICPNftStandard.ICRC7,
                canister = canister
            )
        }*/
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

    override suspend fun fetchCollectionNFTs(
        collectionPrincipal: ICPPrincipal,
    ): List<ICPNFTCollectionItem> = coroutineScope {
        val collectionIds = getNFTCollectionIds().toTypedArray()
        return@coroutineScope collectionIds.map {
            async {
                return@async ICPNFTCollectionItem(
                    id = it,
                    nftId = it.toString(),
                    metadata = fetchNFTMetadata(it)
                )
            }
        }.awaitAll()
    }

    // TODO: icrc7_token_metadata accepts an array of ids, but there is a max value (ex. 100), is this value the same for all canisters?
    // icrc7:metadata:uri:image
    private suspend fun fetchNFTMetadata(nftId: BigInteger) : ICPNFTICRC7Metadata {
        val metadata = service.icrc7_token_metadata(arrayOf(nftId))
        val item = metadata.firstOrNull { innerArray ->
            innerArray?.find { it.text == "icrc7:metadata:uri:image" } != null
        }?.find { it.text == "icrc7:metadata:uri:image" }
        return ICPNFTICRC7Metadata(
            thumbnailUrl = (item?.value as Value.Text).string
        )

    }

}