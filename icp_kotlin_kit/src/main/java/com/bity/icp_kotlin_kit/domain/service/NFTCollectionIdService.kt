package com.bity.icp_kotlin_kit.domain.service

import java.math.BigInteger

interface NFTCollectionIdService {
    fun getNFTCollectionItemId(
        canisterBytes: ByteArray,
        tokenIndex: BigInteger
    ): String
}