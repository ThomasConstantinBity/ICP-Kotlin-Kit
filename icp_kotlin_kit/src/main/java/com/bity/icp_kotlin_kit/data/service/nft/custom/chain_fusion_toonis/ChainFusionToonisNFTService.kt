package com.bity.icp_kotlin_kit.data.service.nft.custom.chain_fusion_toonis

import com.bity.icp_kotlin_kit.domain.generated_file.ChainFusionToonis
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
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

    override suspend fun fetchNFTCollectionIds(
        prev: BigInteger?,
        take: BigInteger?,
    ): List<BigInteger> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCollectionNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem> {
        val collectionTokens = service.getTokens()
        return collectionTokens.map { it.toICPNFTCollectionItem() }
    }

    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> {
        TODO("Not yet implemented")
    }

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