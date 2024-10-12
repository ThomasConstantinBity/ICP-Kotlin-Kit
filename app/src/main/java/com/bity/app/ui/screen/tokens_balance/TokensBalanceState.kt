package com.bity.app.ui.screen.tokens_balance

import com.bity.icp_kotlin_kit.domain.model.ICPTokenBalance

sealed class TokensBalanceState {
    data object Loading: TokensBalanceState()
    data class TokenWithBalance(
        val balances: List<ICPTokenBalance> = emptyList()
    ): TokensBalanceState()
    data class Error(val error: String?): TokensBalanceState()
}