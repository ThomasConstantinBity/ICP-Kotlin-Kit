package com.bity.icp_kotlin_kit.data.service.nft.custom.chain_fusion_toonis

import com.bity.icp_kotlin_kit.domain.generated_file.ChainFusionToonis
import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.exception.NFTServiceException
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTEXTMetadata
import com.bity.icp_kotlin_kit.domain.model.nft.metadata.ICPNFTMetadata
import com.bity.icp_kotlin_kit.domain.service.NFTService
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.math.BigInteger

class ChainFusionToonisNFTService(
    private val canister: ICPPrincipal,
    private val service: ChainFusionToonis.CFTService,
) : NFTService {

    private val objectMapper  = ObjectMapper().registerKotlinModule()

    override suspend fun fetchIds(
        prev: BigInteger?,
        take: BigInteger?,
    ): List<BigInteger> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem> {
        val collectionTokens = service.getTokens()
        return collectionTokens.map { it.toICPNFTCollectionItem() }
    }

    override suspend fun fetchUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> {
        val accountIdentifier = ICPAccount.mainAccount(principal).address
        val result = service.tokens_ext(accountIdentifier)
        return when(result) {
            is ChainFusionToonis.Result_12.err -> {
                onUserHoldingError(result.err)
                emptyList()
            }
            is ChainFusionToonis.Result_12.ok -> TODO()
        }
    }

    private fun onUserHoldingError(err: ChainFusionToonis.CommonError__2) {
        when(err) {
            is ChainFusionToonis.CommonError__2.InvalidToken -> {
                val exception = NFTServiceException.InvalidToken(err.InvalidToken)
                ICPKitLogger.logError("Received InvalidToken response from ChainFusionToonis canister", exception)
            }
            is ChainFusionToonis.CommonError__2.Other -> { }
        }
    }

    override suspend fun fetchNFT(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ) : ICPNFTCollectionItem {
        val args = arrayOf("$nftId".toUInt())
        val token = service.getTokensByIds(args)
        return ICPNFTCollectionItem(
            id = nftId,
            nftId = nftId.toString(),
            metadata = token.firstOrNull()?.toICPNFTCollectionItem()?.metadata,
        )
    }

    override suspend fun fetchOwner(
        collectionPrincipal: ICPPrincipal,
        nftId: BigInteger,
    ): ICPPrincipal? = null

    private fun ChainFusionToonis.CFTService.ArrayClass.toICPNFTCollectionItem() : ICPNFTCollectionItem {
        val id = BigInteger("$tokenIndex__1")
        return ICPNFTCollectionItem(
            id = id,
            nftId = id.toString(),
            metadata = metadata__1.getMetadata(),
        )
    }

    private fun ChainFusionToonis.Metadata__1.getMetadata() : ICPNFTMetadata? {
        return when (this) {
            is ChainFusionToonis.Metadata__1.fungible -> TODO()
            is ChainFusionToonis.Metadata__1.nonfungible -> getICPNFTEXTMetadata()
        }
    }

    private fun ChainFusionToonis.Metadata__1.getICPNFTEXTMetadata() : ICPNFTEXTMetadata? {
        val metadata = (this as? ChainFusionToonis.Metadata__1.nonfungible)?.metadata ?: return null
        val jsonContent = metadata.toString(Charsets.UTF_8)
        try {
            val nonFungibleMetadata = objectMapper.readValue(jsonContent, ChainFusionToonisNonFungibleMetadata::class.java)
            return ICPNFTEXTMetadata(
                nftImageUrl = nonFungibleMetadata.url,
                thumbnailUrl = nonFungibleMetadata.thumb
            )
        } catch (t: Throwable) {
            ICPKitLogger.logError("Error parsing non fungible metadata into ChainFusionToonisNonFungibleMetadata", t)
            return null
        }
    }
}