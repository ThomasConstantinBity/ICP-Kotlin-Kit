package com.bity.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bity.app.ui.screen.account_information.AccountBalance
import com.bity.app.ui.screen.app_feature.AppFeatures
import com.bity.app.ui.screen.icp_nfts.ICPNFTsScreen
import com.bity.app.ui.screen.icp_tokens.ICPTokensScreen
import com.bity.app.ui.screen.nft_details.NFTDetails
import com.bity.app.ui.screen.nft_details.NFTDetailsViewModel
import com.bity.app.ui.theme.ICPKotlinKitTheme
import com.bity.app.ui.util.Screen
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ICPKotlinKitTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.FeatureList.route
                ) {
                    composable(route = Screen.FeatureList.route) {
                        AppFeatures(navController = navController)
                    }
                    composable(route = Screen.ICPTokens.route) {
                        ICPTokensScreen()
                    }
                    composable(route = Screen.ICPNFTs.route) {
                        ICPNFTsScreen(
                            onNFTClick = {
                                navController.navigate(Screen.NFTDetails(it))
                            }
                        )
                    }
                    composable(route = Screen.AccountBalance.route) {
                        AccountBalance()
                    }
                    composable<Screen.NFTDetails> {
                        val sendRoute: Screen.NFTDetails = it.toRoute()
                        val viewModel = NFTDetailsViewModel(
                            nftCanisterString = sendRoute.canisterString,
                            getNFTCollection = get()
                        )
                        NFTDetails(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}