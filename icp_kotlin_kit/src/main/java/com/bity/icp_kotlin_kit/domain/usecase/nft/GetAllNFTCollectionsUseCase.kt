package com.bity.icp_kotlin_kit.domain.usecase.nft

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import com.bity.icp_kotlin_kit.provideNFTRepository

class GetAllNFTCollectionsUseCase internal constructor(
    private val nftRepository: NFTRepository
){

    constructor(): this(provideNFTRepository())

    suspend operator fun invoke(): List<ICPNftCollection> =
        nftRepository.getAllNFTsCollections()
}