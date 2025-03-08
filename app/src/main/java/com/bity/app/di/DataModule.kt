package com.bity.app.di

import com.bity.icp_kotlin_kit.di.*
import org.koin.dsl.module

val dataModule = module {
    single { fetchAllNFTCollections }
    single { fetchNFTCollection }
    single { fetchNFTCollectionTokens }
    single { fetchNFTCollectionTokenOwner }
    single { fetchUserNFTTokensHolding }
}