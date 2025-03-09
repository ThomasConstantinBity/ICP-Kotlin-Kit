package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.exception.ICPKitException
import com.bity.icp_kotlin_kit.domain.generated_file.CommonError
import com.bity.icp_kotlin_kit.domain.generated_file.EXTService
import com.bity.icp_kotlin_kit.domain.generated_file.Result_1
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTEXTMetadata
import com.bity.icp_kotlin_kit.domain.service.NFTCollectionIdService
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import java.math.BigInteger

open class EXTNFTRepository(
    private val canister: ICPPrincipal,
    private val service: EXTService,
    private val idService: NFTCollectionIdService
): NFTRepository {

    override suspend fun fetchUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> =
        service.tokens_ext(principal.string)
            .toDataModel(canister)

    override suspend fun fetchIds(
        prev: BigInteger?,
        take: BigInteger?
    ): List<BigInteger> {
        val tokens = service.getTokens()
        return tokens.map { BigInteger("${it.tokenIndex}") }
    }

    override suspend fun fetchNFTs(
        collectionPrincipal: ICPPrincipal,
    ): List<ICPNFTCollectionItem> {
        val collectionIds = fetchIds()
        return collectionIds
            .map {
                val nftId = idService.getNFTCollectionItemId(
                    canisterBytes = canister.bytes,
                    tokenIndex = it
                )
                ICPNFTCollectionItem(
                    id = it,
                    nftId = nftId,
                    metadata = getNFTMetadata(nftId)
                )
            }
    }

    override suspend fun fetchNFT(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ) : ICPNFTCollectionItem {
        val nfts = fetchNFTs(collectionPrincipal)
        return nfts.find { it.id == nftId }
            ?: throw ICPKitException.NFTNotFound(
                collectionPrincipal = collectionPrincipal,
                nftId = nftId
            )
    }

    override suspend fun fetchOwner(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ): ICPPrincipal? = null

    private fun getNFTMetadata(nftId: String): ICPNFTEXTMetadata =
        ICPNFTEXTMetadata(
            nftImageUrl = getNFTCollectionItemImageURL(nftId),
            thumbnailUrl = getNFTCollectionItemThumbnailURL(nftId)
        )

    /**
     * [NFT_CANISTERS.WRAPPED_PUNKS]: `https://${NFT_CANISTERS.IC_PUNKS}.raw.icp0.io/Token/${index}`,
     * [NFT_CANISTERS.WRAPPED_DRIP]: `https://${NFT_CANISTERS.IC_DRIP}.raw.icp0.io?tokenId=${index}`,
     */
    private fun getNFTCollectionItemImageURL(nftId: String): String =
        when(canister.string) {
            else -> "https://${canister.string}.raw.icp0.io/?tokenid=${nftId}"
        }


    /**
     * [NFT_CANISTERS.WRAPPED_PUNKS]: `https://${NFT_CANISTERS.IC_PUNKS}.raw.icp0.io/Token/${index}`,
     * [NFT_CANISTERS.WRAPPED_DRIP]: `https://${NFT_CANISTERS.IC_DRIP}.raw.icp0.io?tokenId=${index}`,
     */
    private fun getNFTCollectionItemThumbnailURL(nftId: String): String =
        when(canister.string) {
            else -> "https://${canister.string}.raw.icp0.io/?type=thumbnail&tokenid=${nftId}"
        }

    private fun Result_1.ok._ArrayClass.toDataModel(canister: ICPPrincipal): ICPNFTDetails {
        val nftId = idService.getNFTCollectionItemId(
            canisterBytes = canister.bytes,
            tokenIndex = BigInteger(tokenIndex.toString())
        )
        return ICPNFTDetails(
            name = "#$tokenIndex",
            standard = ICPNftStandard.EXT,
            canister = canister,
            metadata = getNFTMetadata(nftId)
        )
    }

    private fun Result_1.toDataModel(canister: ICPPrincipal): List<ICPNFTDetails> =
        when(this) {
            is Result_1.err -> throw commonError.toDataModel()
            is Result_1.ok -> values.map { it.toDataModel(canister) }
        }

    private fun CommonError.toDataModel(): RemoteClientError =
        when(this) {
            is CommonError.InvalidToken -> RemoteClientError.InvalidToken(tokenIdentifier__1)
            is CommonError.Other -> RemoteClientError.GetUserNFTHoldingsGenericError(string)
        }

}