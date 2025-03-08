package com.bity.app.ui.screen.account_nft_holding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchUserNFTTokensHolding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountNFTHoldingViewModel(
    private val fetchUserNFTTokensHolding: FetchUserNFTTokensHolding
) : ViewModel() {

    private val _state = MutableStateFlow<AccountNFTHoldingState>(AccountNFTHoldingState.Result())
    val state: StateFlow<AccountNFTHoldingState> = _state.asStateFlow()

    fun fetchNFTHoldings(principalString: String) {
        viewModelScope.launch {
            try {
                val principal = ICPPrincipal(principalString)
                _state.value = AccountNFTHoldingState.Loading
                val holdings = withContext(Dispatchers.IO) {
                    fetchUserNFTTokensHolding(principal)
                }
                _state.value = AccountNFTHoldingState.Result(holdings)
                Log.d(TAG, "holdings: ${holdings.size}")
            } catch (t: Throwable) {
                Log.e(TAG, "fetchNFTHoldings", t)
                _state.value = AccountNFTHoldingState.Error(t.message ?: "Unknown error")
            }
        }
    }

    companion object {
        private const val TAG = "AccountNFTHoldingViewModel"
    }

}