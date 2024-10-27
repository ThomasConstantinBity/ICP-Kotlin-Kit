package com.bity.app.ui.screen.icp_tokens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.usecase.token.GetAllTokensUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ICPTokensViewModel(
    private val getAllTokens: GetAllTokensUseCase
) : ViewModel() {

    var state: ICPTokensState by mutableStateOf(ICPTokensState())
        private set

    init {
        viewModelScope.launch {
            val tokens = withContext(Dispatchers.IO) { getAllTokens() }
            state = ICPTokensState(
                isLoading = false,
                tokens = tokens.sortedBy { it.name }
            )
        }
    }
}