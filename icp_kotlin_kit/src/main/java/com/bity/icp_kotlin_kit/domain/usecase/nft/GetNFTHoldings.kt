package com.bity.icp_kotlin_kit.domain.usecase.nft

import com.bity.icp_kotlin_kit.di.nftRepository
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository

class GetNFTHoldings internal constructor(
    private val repository: NFTRepository
) {

    constructor(): this(nftRepository)

    suspend operator fun invoke(icpPrincipal: ICPPrincipal): List<*> =
        repository.getNFTHoldings(icpPrincipal)
}