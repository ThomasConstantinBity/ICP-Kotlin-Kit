package com.bity.app.ui.screen.account_nft_holding

import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails

sealed class AccountNFTHoldingState {

    object Loading : AccountNFTHoldingState()
    class Error(val errorMessage: String) : AccountNFTHoldingState()
    class Result(
        val nftHoldings: List<ICPNFTDetails> = emptyList()
    ) : AccountNFTHoldingState()

}