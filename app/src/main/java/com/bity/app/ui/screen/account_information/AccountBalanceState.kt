package com.bity.app.ui.screen.account_information

import com.bity.icp_kotlin_kit.domain.model.ICPTokenBalance

data class AccountBalanceState(
    val principalId: String = "",
    val balances: List<ICPTokenBalance> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)