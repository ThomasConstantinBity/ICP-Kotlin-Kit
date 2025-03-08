package com.bity.icp_kotlin_kit.domain.use_case.nft

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.nft.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedRepository

class FetchUserNFTTokensHolding internal constructor(
    private val nftCachedRepository: NFTCachedRepository
) {

    suspend operator fun invoke(
        icpPrincipal: ICPPrincipal
    ): List<ICPNFTDetails> =
        nftCachedRepository.fetchUserTokenHoldings(icpPrincipal)

}