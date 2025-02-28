package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.generated_file.CommonError
import com.bity.icp_kotlin_kit.domain.generated_file.EXTService
import com.bity.icp_kotlin_kit.domain.generated_file.Result_1
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.service.NFTService
import com.bity.icp_kotlin_kit.util.ext_function.bytes
import com.bity.icp_kotlin_kit.util.ext_function.to32Bits
import java.math.BigInteger
import java.nio.ByteBuffer

internal class EXTNFTService(
    private val canister: ICPPrincipal,
    private val service: EXTService
): NFTService {

    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> =
        service.tokens_ext(principal.string)
            .toDataModel(canister)

    override suspend fun getNFTCollectionIds(
        prev: BigInteger?,
        take: BigInteger?
    ): List<BigInteger> {
        val tokens = service.getTokens()
        return tokens.map { BigInteger("${it.tokenIndex}") }
    }

    override suspend fun fetchCollectionNFTs(
        collectionPrincipal: ICPPrincipal
    ): List<ICPNFTCollectionItem> {
        val collectionIds = getNFTCollectionIds()
        return collectionIds
            .map {
            val nftId = getNFTCollectionItemId(it)
            ICPNFTCollectionItem(
                id = it,
                nftId = nftId,
                nftImageUrl = getNFTCollectionItemImageURL(nftId),
                thumbnail = getNFTCollectionItemThumbnailURL(nftId),
            )
        }
    }

    private fun getNFTCollectionItemId(tokenIndex: BigInteger): String {
        val prefix = byteArrayOf(0x0A, 0x74, 0x69, 0x64)
        val bytes = prefix + canister.bytes + tokenIndex.to32Bits()
        val principal = ICPPrincipal(bytes)
        return principal.string
    }

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

}

private fun Result_1.toDataModel(canister: ICPPrincipal): List<ICPNFTDetails> =
    when(this) {
        is Result_1.err -> throw commonError.toDataModel()
        is Result_1.ok -> values.map { it.toDataModel(canister) }
    }

private fun Result_1.ok._ArrayClass.toDataModel(canister: ICPPrincipal): ICPNFTDetails =
    ICPNFTDetails(
        name = "#$tokenIndex",
        standard = ICPNftStandard.EXT,
        canister = canister
    )

private fun CommonError.toDataModel(): RemoteClientError =
    when(this) {
        is CommonError.InvalidToken -> RemoteClientError.InvalidToken(tokenIdentifier__1)
        is CommonError.Other -> RemoteClientError.GetUserNFTHoldingsGenericError(string)
    }