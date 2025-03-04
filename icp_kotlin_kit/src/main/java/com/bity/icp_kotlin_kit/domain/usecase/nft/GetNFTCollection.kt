package com.bity.icp_kotlin_kit.domain.usecase.nft

import com.bity.icp_kotlin_kit.di.nftRepository
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository

class GetNFTCollection internal constructor(
    private val repository: NFTRepository
) {

    constructor(): this(nftRepository)

    suspend operator fun invoke(canister: ICPPrincipal): ICPNftCollection? =
        repository.fetchNFTCollection(canister)

}