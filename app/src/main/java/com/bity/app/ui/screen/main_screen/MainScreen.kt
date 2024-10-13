package com.bity.app.ui.screen.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bity.app.ui.util.Screen

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainButton(text = "Tokens Balance") {
            navController.navigate(Screen.TokensBalance.route)
        }
    }
}

@Composable
private fun MainButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() }
    ) {
        Text(text = text)
    }
}