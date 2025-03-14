package com.bity.app.ui.screen.account_information

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.error.ICPCryptographyError
import com.bity.icp_kotlin_kit.domain.use_case.token.FetchTokensBalance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountBalanceViewModel(
    private val fetchTokensBalance: FetchTokensBalance
): ViewModel() {

    var accountInformationState by mutableStateOf(AccountBalanceState())
        private set

    fun onSearch() {
        val principalId = try {
            ICPPrincipal(accountInformationState.principalId)
        } catch (_: ICPCryptographyError) {
            accountInformationState = AccountBalanceState(
                error = "Invalid principal id"
            )
            return
        }
        viewModelScope.launch {
            accountInformationState = accountInformationState.copy(
                isLoading = true
            )
            val tokensBalance = withContext(Dispatchers.IO) {
                fetchTokensBalance(principalId)
            }

            accountInformationState = accountInformationState.copy(
                isLoading = false,
                balances = tokensBalance
            )
        }
    }

    fun onPrincipalIdUpdated(principalId: String) {
        accountInformationState = accountInformationState.copy(
            principalId = principalId
        )
    }
}