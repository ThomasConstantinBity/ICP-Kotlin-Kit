package com.bity.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bity.app.ui.screen.account_information.AccountBalance
import com.bity.app.ui.screen.app_feature.AppFeatures
import com.bity.app.ui.screen.icp_nfts.ICPNFTsScreen
import com.bity.app.ui.screen.icp_tokens.ICPTokensScreen
import com.bity.app.ui.screen.nft_collection_details.NFTCollectionDetails
import com.bity.app.ui.screen.nft_collection_details.NFTDetailsViewModel
import com.bity.app.ui.theme.ICPKotlinKitTheme
import com.bity.app.ui.util.Screen
import com.bity.app.ui.util.Screen.NFTDetails
import org.koin.core.component.KoinComponent
import org.koin
    .core.component.get
import com.bity.app.ui.screen.nft_details.NFTDetailsPage
import com.bity.app.ui.screen.nft_details.NFTDetailsPageViewModel
import com.bity.icp_kotlin_kit.di.nftRepository
import org.koin.androidx.viewmodel.ext.android.viewModel

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
                                navController.navigate(Screen.NFTCollectionDetails(it))
                            }
                        )
                    }
                    composable<NFTDetails> {
                        val route: NFTDetails = it.toRoute()
                        val viewModel = NFTDetailsPageViewModel(
                            collectionCanister = route.collectionPrincipal,
                            nftId = route.nftId,
                            nftRepository = get()
                        )
                        NFTDetailsPage(
                            viewModel = viewModel
                        )
                    }
                    composable(route = Screen.AccountBalance.route) {
                        AccountBalance()
                    }
                    composable<Screen.NFTCollectionDetails> {
                        val sendRoute: Screen.NFTCollectionDetails = it.toRoute()
                        val viewModel = NFTDetailsViewModel(
                            nftCanisterString = sendRoute.canisterString,
                            nftRepository = get()
                        )
                        NFTCollectionDetails(
                            viewModel = viewModel,
                            onNftClick = {
                                val route = Screen.NFTDetails(
                                    collectionPrincipal = sendRoute.canisterString,
                                    nftId = it.toString()
                                )
                                navController.navigate(route)
                            }
                        )
                    }
                }
            }
        }
    }
}