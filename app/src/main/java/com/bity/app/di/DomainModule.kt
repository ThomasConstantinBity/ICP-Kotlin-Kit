package com.bity.app.di

import com.bity.icp_kotlin_kit.domain.usecase.nft.GetAllNFTCollectionsUseCase
import com.bity.icp_kotlin_kit.domain.usecase.nft.GetNFTCollection
import com.bity.icp_kotlin_kit.domain.usecase.token.GetAllTokensUseCase
import com.bity.icp_kotlin_kit.domain.usecase.token.GetTokenBalanceUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetAllNFTCollectionsUseCase)
    singleOf(::GetAllTokensUseCase)
    singleOf(::GetTokenBalanceUseCase)
    singleOf(::GetNFTCollection)
}