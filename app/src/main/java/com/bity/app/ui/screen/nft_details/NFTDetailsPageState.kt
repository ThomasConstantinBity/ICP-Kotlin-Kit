package com.bity.app.ui.screen.nft_details

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTCollectionItem

data class NFTDetailsPageState(
    val isLoading: Boolean = false,
    val item: ICPNFTCollectionItem? = null,
    val nftCollection: ICPNftCollection? = null
)