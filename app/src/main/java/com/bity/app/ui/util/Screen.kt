package com.bity.app.ui.util

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {

    @Serializable data object FeatureList: Screen("features")
    @Serializable data object ICPTokens: Screen("icp_tokens")
    @Serializable data object ICPNFTs: Screen("icp_nfts")
    @Serializable data object NFTHoldings: Screen("nft_holdings")
    @Serializable data object AccountBalance: Screen("account_balance")

    @Serializable
    data class NFTCollectionDetails(val canisterString: String) : Screen("nft/collection_details")
}