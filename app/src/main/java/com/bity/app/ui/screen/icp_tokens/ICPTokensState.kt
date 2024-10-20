package com.bity.app.ui.screen.icp_tokens

import com.bity.icp_kotlin_kit.domain.model.ICPToken

data class ICPTokensState(
    val isLoading: Boolean = true,
    val tokens: List<ICPToken> = emptyList()
)