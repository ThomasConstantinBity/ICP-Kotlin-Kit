package com.bity.app.ui.screen.nft_details

import com.bity.icp_kotlin_kit.domain.model.ICPNftCollection

data class NFTDetailsState(
    val isLoading: Boolean = true,
    val nftCollection: ICPNftCollection? = null

)