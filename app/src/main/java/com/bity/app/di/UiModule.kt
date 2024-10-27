package com.bity.app.di

import com.bity.app.ui.screen.icp_nfts.ICPNFTsViewModel
import com.bity.app.ui.screen.icp_tokens.ICPTokensViewModel
import com.bity.app.ui.screen.tokens_balance.TokensBalanceViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::ICPNFTsViewModel)
    viewModelOf(::ICPTokensViewModel)
    viewModelOf(::TokensBalanceViewModel)
}