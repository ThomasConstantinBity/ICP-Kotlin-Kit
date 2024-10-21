package com.bity.icp_kotlin_kit.data.repository

import com.bity.icp_kotlin_kit.data.datasource.api.model.toDomainModel
import com.bity.icp_kotlin_kit.domain.generated_file.DABNFT
import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository

internal class NFTRepositoryImpl(
    private val nftService: DABNFT.DABNFTService
): NFTRepository {

    private var cachedCollections: List<ICPNftCollection> = emptyList()

    override suspend fun getAllNFTsCollections(): List<ICPNftCollection> {
        if(cachedCollections.isEmpty()) {
            val collections = nftService.get_all()
                .mapNotNull { it.toDomainModel() }
            cachedCollections = collections
        }
        return cachedCollections
    }
}

private fun DABNFT.nft_canister.toDomainModel(): ICPNftCollection? {
    val nftStandard = standard ?: return null
    return ICPNftCollection(
        standard = nftStandard,
        name = name,
        description = description,
        iconURL = thumbnail,
        canister = principal_id.toDomainModel()
    )
}

private val DABNFT.nft_canister.standard: ICPNftStandard?
    get() {
        val standardValue = details.firstOrNull { it.string == "standard" }?.detail_value
            ?: return null
        if(standardValue !is DABNFT.detail_value.Text) return null
        return when (standardValue.string.uppercase()) {
            "EXT" -> ICPNftStandard.EXT
            "ICRC7" -> ICPNftStandard.ICRC7
            "NFTORIGYN" -> ICPNftStandard.ORIGYN_NFT
            "ICPUNKS" -> ICPNftStandard.ICPUNKS
            "DEPARTURELABS" -> ICPNftStandard.DEPARTURESLABS
            "C3" -> ICPNftStandard.C3
            "DIP721" -> ICPNftStandard.DIP721
            "DIP721V2" -> ICPNftStandard.DIP721V2
            "ERC721" -> ICPNftStandard.ERC721
            else -> null
        }
    }