package com.bity.app.di

import com.bity.app.ui.screen.tokens_balance.TokensBalanceViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::TokensBalanceViewModel)
}