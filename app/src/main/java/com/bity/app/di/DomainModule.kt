package com.bity.app.di

import com.bity.icp_kotlin_kit.di.*
import org.koin.dsl.module

val domainModule = module {
    single { fetchAllNFTCollections }
    single { fetchNFTCollection }
    single { fetchNFTCollectionTokens }
    single { fetchNFTCollectionTokenOwner }
    single { fetchUserNFTTokensHolding }
    single { fetchTokensBalance }
    single { fetchAllTokens }
    single { getTransactionExplorerUrl }
    single { fetchTokenTransactions }
}