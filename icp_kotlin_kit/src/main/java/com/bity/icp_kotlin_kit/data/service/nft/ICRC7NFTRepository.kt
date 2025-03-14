package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.data.generated_file.Account
import com.bity.icp_kotlin_kit.data.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.data.generated_file.Value
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTICRC7Metadata
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

open class ICRC7NFTRepository(
    private val canister: ICPPrincipal,
    private val service: DBANFTService,
): NFTRepository {

    open val urlMetadataKeys = listOf("icrc7:metadata:uri:image", "image")

    override suspend fun fetchUserHoldings(
        principal: ICPPrincipal
    ): List<ICPNFTDetails> = coroutineScope {

        val tokenHoldings = service.icrc7_tokens_of(
            account = Account(
                owner = principal.toDataModel(),
                subaccount = null
            ),
            prev = null,
            take = null
        )

        return@coroutineScope tokenHoldings.map { tokenId ->
            async {
                val metadata = fetchNFTMetadata(tokenId)
                ICPNFTDetails(
                    name = "#${tokenId}",
                    standard = ICPNftStandard.ICRC7,
                    canister = canister,
                    metadata = metadata
                )
            }
        }.awaitAll()

    }

    override suspend fun fetchNFT(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ) : ICPNFTCollectionItem {
        TODO("Not yet implemented")
    }

    override suspend fun fetchOwner(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ): ICPPrincipal? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchIds(
        prev: BigInteger?,
        take: BigInteger?
    ): List<BigInteger> {
        return service.icrc7_tokens(
            prev = prev,
            take = take
        ).toList()
    }

    override suspend fun fetchNFTs(
        collectionPrincipal: ICPPrincipal,
    ): List<ICPNFTCollectionItem> = coroutineScope {
        val collectionIds = fetchIds().toTypedArray()
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
    open suspend fun fetchNFTMetadata(nftId: BigInteger) : ICPNFTICRC7Metadata? {
        val metadata = try {
            service.icrc7_token_metadata(arrayOf(nftId))
        } catch (t: Throwable) {
            ICPKitLogger.logError("Error while getting metadata for NFT id $nftId from ${canister.string}", t)
            return null
        }
        val uriMetadata = metadata.firstOrNull { innerArray ->
            innerArray?.find { urlMetadataKeys.contains(it.text) } != null
        }?.find { urlMetadataKeys.contains(it.text) }
        if(uriMetadata == null) {
            ICPKitLogger.logDebug("No metadata found for NFT id $nftId from ${canister.string}")
            return null
        }
        val textValue = (uriMetadata.value as? Value.Text)
        if(textValue == null) {
            ICPKitLogger.logDebug("Wrong NFT metadata found for NFT id $nftId from ${canister.string}")
            return null
        }
        return ICPNFTICRC7Metadata(
            thumbnailUrl = textValue.string
        )

    }

}