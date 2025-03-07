package com.bity.icp_kotlin_kit.data.repository.token

import com.bity.icp_kotlin_kit.domain.generated_file.DIP20
import com.bity.icp_kotlin_kit.domain.generated_file.DIP20.TxError
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPTokenMetadata
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.model.error.TransferException
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.model.toDomainModel
import com.bity.icp_kotlin_kit.domain.repository.ICPTokenRepository
import java.math.BigInteger

internal class DIP20TokenRepository(
    private val canister: DIP20.DIP20Service
): ICPTokenRepository {

    override suspend fun fetchBalance(principal: ICPPrincipal): BigInteger =
        this@DIP20TokenRepository.canister.balanceOf(principal.toDataModel())

    override suspend fun fee(): BigInteger =
        fetchMetadata().fee

    override suspend fun fetchMetadata(): ICPTokenMetadata {
        val metadata = this@DIP20TokenRepository.canister.getMetadata()
        return metadata.toDomainModel()
    }

    override suspend fun transfer(args: ICPTokenTransferArgs): ICPTokenTransfer {
        val txReceipt = this@DIP20TokenRepository.canister.transfer(
            to = args.to.principal.toDataModel(),
            value = args.amount,
            sender = args.sender
        )
        val blockIndex = when(txReceipt) {
            is DIP20.TxReceipt.Ok -> txReceipt.bigInteger
            is DIP20.TxReceipt.Err -> throw txReceipt.txError.toDataModel()
        }
        return ICPTokenTransfer.Height(blockIndex)
    }
}

internal fun TxError.toDataModel(): TransferException =
    when(this) {
        TxError.AmountTooSmall -> TransferException.AmountTooSmall
        TxError.BlockUsed -> TransferException.BlockUsed
        TxError.ErrorOperationStyle -> TransferException.ErrorOperationStyle
        TxError.ErrorTo -> TransferException.ErrorTo
        TxError.InsufficientAllowance -> TransferException.InsufficientAllowance
        TxError.InsufficientBalance -> TransferException.InsufficientBalance
        TxError.LedgerTrap -> TransferException.LedgerTrap
        is TxError.Other -> TransferException.Other(string)
        TxError.Unauthorized -> TransferException.Unauthorized
    }