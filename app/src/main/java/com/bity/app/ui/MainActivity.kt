package com.bity.app.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bity.app.ui.screen.account_information.AccountBalance
import com.bity.app.ui.screen.account_nft_holding.AccountNFTHolding
import com.bity.app.ui.screen.app_feature.AppFeatures
import com.bity.app.ui.screen.icp_nfts.ICPNFTsScreen
import com.bity.app.ui.screen.icp_tokens.ICPTokensScreen
import com.bity.app.ui.screen.nft_collection_details.NFTCollectionDetails
import com.bity.app.ui.screen.nft_collection_details.NFTDetailsViewModel
import com.bity.app.ui.theme.ICPKotlinKitTheme
import com.bity.app.ui.util.Screen
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

                    composable(route = Screen.AccountBalance.route) {
                        AccountBalance()
                    }

                    composable(route = Screen.ICPNFTs.route) {
                        ICPNFTsScreen(
                            onNFTClick = { }
                        )
                    }

                    composable(route = Screen.NFTHoldings.route) {
                        AccountNFTHolding()
                    }

                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}