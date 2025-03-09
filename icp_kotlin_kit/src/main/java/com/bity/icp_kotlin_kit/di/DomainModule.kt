package com.bity.icp_kotlin_kit.di

import com.bity.icp_kotlin_kit.domain.repository.ICPTransactionRepository
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchAllNFTCollections
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchNFTCollection
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchNFTCollectionTokenOwner
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchNFTCollectionTokens
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchUserNFTTokensHolding
import com.bity.icp_kotlin_kit.domain.use_case.token.FetchAllTokens
import com.bity.icp_kotlin_kit.domain.use_case.token.FetchTokensBalance
import com.bity.icp_kotlin_kit.domain.use_case.transaction.FetchTokenTransactions
import com.bity.icp_kotlin_kit.domain.use_case.transaction.GetTransactionExplorerUrl

/**
 * NFT Use Cases
 */
val fetchAllNFTCollections by lazy {
    FetchAllNFTCollections(
        nftCachedRepository = nftCachedRepository
    )
}

val fetchNFTCollection by lazy {
    FetchNFTCollection(
        nftCachedRepository = nftCachedRepository
    )
}

val fetchNFTCollectionTokens by lazy {
    FetchNFTCollectionTokens(
        nftCachedRepository = nftCachedRepository
    )
}

val fetchNFTCollectionTokenOwner by lazy {
    FetchNFTCollectionTokenOwner(
        nftCachedRepository = nftCachedRepository
    )
}

val fetchUserNFTTokensHolding by lazy {
    FetchUserNFTTokensHolding(
        nftCachedRepository = nftCachedRepository
    )
}

/**
 * Tokens Use Cases
 */
val fetchTokensBalance by lazy {
    FetchTokensBalance(
        tokenRepository = tokenCachedRepository
    )
}

val fetchAllTokens by lazy {
    FetchAllTokens(
        tokenRepository = tokenCachedRepository
    )
}

/**
 * Transaction Use Cases
 */
val getTransactionExplorerUrl by lazy {
    GetTransactionExplorerUrl(
        tokenRepository = tokenCachedRepository,
        transactionRepository = icpTransactionRepository
    )
}

val fetchTokenTransactions by lazy {
    FetchTokenTransactions(
        tokenRepository = tokenCachedRepository,
        transactionRepository = icpTransactionRepository
    )
}