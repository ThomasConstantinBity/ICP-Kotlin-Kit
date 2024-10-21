package com.bity.app.ui.util

sealed class Screen(val route: String) {

    data object FeatureList: Screen("features")
    data object ICPTokens: Screen("icp_tokens")
    data object ICPNFTs: Screen("icp_nfts")
    data object TokensBalance: Screen("tokens_balance")
}