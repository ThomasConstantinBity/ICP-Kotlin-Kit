package com.bity.icp_kotlin_kit.data.service.nft

import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.domain.generated_file.CommonError
import com.bity.icp_kotlin_kit.domain.generated_file.EXTService
import com.bity.icp_kotlin_kit.domain.generated_file.Result_1
import com.bity.icp_kotlin_kit.domain.model.ICPNFTDetails
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.service.NFTService

internal class EXTNFTService(
    private val service: EXTService
): NFTService {

    override suspend fun getUserHoldings(principal: ICPPrincipal): List<ICPNFTDetails> =
        service.tokens_ext(principal.string)
            .toDataModel()
}

private fun Result_1.toDataModel(): List<ICPNFTDetails> =
    when(this) {
        is Result_1.err -> throw commonError.toDataModel()
        is Result_1.ok -> values.map { it.toDataModel() }
    }

private fun Result_1.ok._ArrayClass.toDataModel(): ICPNFTDetails =
    ICPNFTDetails(
        name = "#$tokenIndex"
    )

private fun CommonError.toDataModel(): RemoteClientError =
    when(this) {
        is CommonError.InvalidToken -> RemoteClientError.InvalidToken(tokenIdentifier__1)
        is CommonError.Other -> RemoteClientError.GetUserNFTHoldingsGenericError(string)
    }