package com.bity.icp_kotlin_kit.data.service.nft.custom

import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.service.NFTService
import java.math.BigInteger

class ChainFusionToonisNFTService(
    private val canister: ICPPrincipal,
    private val service: DBANFTService,
) : NFTService {

    override suspend fun getNFTCollectionIds(
        prev: BigInteger?,
        take: BigInteger?,
    ): List<BigInteger> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCollectionNFTs(collectionPrincipal: ICPPrincipal): List<ICPNFTCollectionItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> {
        TODO("Not yet implemented")
    }

}