package com.bity.app.ui.screen.nft_details

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem

data class NFTDetailsState(
    val isLoading: Boolean = true,
    val nftCollection: ICPNftCollection? = null,
    val collectionNFTs: List<ICPNFTCollectionItem> = emptyList()
)