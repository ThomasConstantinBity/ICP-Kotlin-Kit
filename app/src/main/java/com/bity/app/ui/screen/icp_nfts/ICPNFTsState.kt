package com.bity.app.ui.screen.icp_nfts

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection

data class ICPNFTsState(
    val isLoading: Boolean = true,
    val nftCollections: List<ICPNftCollection> = emptyList()
)