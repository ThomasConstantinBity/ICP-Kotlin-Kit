package com.bity.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bity.app.ui.screen.main_screen.MainScreen
import com.bity.app.ui.screen.tokens_balance.TokensBalance
import com.bity.app.ui.theme.ICPKotlinKitTheme
import com.bity.app.ui.util.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ICPKotlinKitTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.MainScreen.route
                ) {
                    composable(route = Screen.MainScreen.route) {
                        MainScreen(navController)
                    }
                    composable(route = Screen.TokensBalance.route) {
                        TokensBalance()
                    }
                }
            }
        }
    }
}