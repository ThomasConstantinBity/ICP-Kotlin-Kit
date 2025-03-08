package com.bity.icp_kotlin_kit.di

import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchAllNFTCollections
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchNFTCollection
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchNFTCollectionTokenOwner
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchNFTCollectionTokens
import com.bity.icp_kotlin_kit.domain.use_case.nft.FetchUserNFTTokensHolding

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