package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.domain.generated_file.OrigynNFT
import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.service.NFTService

class OrigynNFTService(
    private val canister: OrigynNFT.Nft_Canister
): NFTService {

    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> {
        val account = OrigynNFT.Account.principal(principal.toDataModel())
        val nftIds = when (val nftBalanceResult = canister.balance_of_nft_origyn(account)) {
            is OrigynNFT.BalanceResult.ok -> nftBalanceResult.ok.nfts
            is OrigynNFT.BalanceResult.err -> TODO()
        }
        val nftInfos = canister.nft_batch_origyn(nftIds)
        val results = nftInfos.filterIsInstance<OrigynNFT.NFTInfoResult.ok>()
        val errors = nftInfos.filterIsInstance<OrigynNFT.NFTInfoResult.err>()

        if(errors.isNotEmpty()) TODO()

        return nftIds.zip(results).map(::getNFTDetails)
    }

    private fun getNFTDetails(pair: Pair<String, OrigynNFT.NFTInfoResult.ok>): ICPNFTDetails {
        val index = pair.first
        val info = pair.second
        val metadata = info.ok.metadata
        TODO()
    }

}