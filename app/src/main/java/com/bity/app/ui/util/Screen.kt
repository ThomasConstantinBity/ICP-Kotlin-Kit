package com.bity.app.ui.util

sealed class Screen(val route: String) {
    data object MainScreen: Screen("main_screen")
    data object TokensBalance: Screen("tokens_balance")
}