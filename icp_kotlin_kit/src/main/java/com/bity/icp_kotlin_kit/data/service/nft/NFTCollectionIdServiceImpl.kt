package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.service.NFTCollectionIdService
import com.bity.icp_kotlin_kit.util.ext_function.to32Bits
import java.math.BigInteger

class NFTCollectionIdServiceImpl : NFTCollectionIdService {

    override fun getNFTCollectionItemId(
        canisterBytes: ByteArray,
        tokenIndex: BigInteger
    ): String {
        val prefix = byteArrayOf(0x0A, 0x74, 0x69, 0x64)
        val bytes = prefix + canisterBytes + tokenIndex.to32Bits()
        val principal = ICPPrincipal(bytes)
        return principal.string
    }

}