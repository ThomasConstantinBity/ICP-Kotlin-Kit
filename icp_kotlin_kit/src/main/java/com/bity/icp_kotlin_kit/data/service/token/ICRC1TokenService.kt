package com.bity.icp_kotlin_kit.data.service.token

import com.bity.icp_kotlin_kit.domain.generated_file.ICRC1
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPTokenMetadata
import com.bity.icp_kotlin_kit.domain.model.ICPTokenTransfer
import com.bity.icp_kotlin_kit.domain.model.arg.ICPTokenTransferArgs
import com.bity.icp_kotlin_kit.domain.model.error.ICRC1TokenException
import com.bity.icp_kotlin_kit.domain.model.error.TransferException
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.service.ICPTokenService
import com.bity.icp_kotlin_kit.util.ext_function.toICPTimestamp
import java.math.BigInteger

internal class ICRC1TokenService(
    private val service: ICRC1.ICRC1Service
): ICPTokenService {

    override suspend fun getBalance(principal: ICPPrincipal): BigInteger {
        val account = ICRC1.Account(
            owner = principal.toDataModel(),
            subaccount = null
        )
        return service.icrc1_balance_of(account)
    }

    override suspend fun metadata(): ICPTokenMetadata {
        val metadata = service.icrc1_metadata()
        val totalSupply = service.icrc1_total_supply()
        return buildICPTokenMetadata(metadata, totalSupply)
    }

    override suspend fun fee(): BigInteger =
        service.icrc1_fee()

    override suspend fun transfer(args: ICPTokenTransferArgs): ICPTokenTransfer {
        val transferArgs = ICRC1.TransferArgs(
            from_subaccount = args.from.subAccountId,
            to = ICRC1.Account(
                owner = args.to.principal.toDataModel(),
                subaccount = args.to.subAccountId
            ),
            amount = args.amount,
            fee = args.fee,
            memo = args.icrc1Memo,
            created_at_time = args.createdAtMillis?.toICPTimestamp()
        )
        val transferResult = service.icrc1_transfer(
            transferArgs = transferArgs,
            sender = args.sender
        )
        val blockIndex = when(transferResult) {
            is ICRC1.TransferResult.Err -> throw transferResult.transferError.toDataModel()
            is ICRC1.TransferResult.Ok -> transferResult.bigInteger
        }
        return ICPTokenTransfer.Height(blockIndex)
    }

    private fun buildICPTokenMetadata(
        metadata: Array<ICRC1.MetadataField>,
        totalSupply: BigInteger
    ): ICPTokenMetadata {
        return ICPTokenMetadata(
            name = getTextValue("icrc1:name", metadata),
            symbol = getTextValue("icrc1:symbol", metadata),
            decimals = getNatValue("icrc1:decimals", metadata).toInt(),
            totalSupply = totalSupply,
            logoUrl = getLogoUrl(metadata),
            fee = getNatValue("icrc1:fee", metadata),
        )
    }

    private fun getTextValue(
        key: String,
        metadata: Array<ICRC1.MetadataField>
    ): String =
        (metadata.find { it.string == key }?.value as? ICRC1.Value.Text)?.string
            ?: throw ICRC1TokenException.InvalidMetadataField(key)

    private fun getLogoUrl(
        metadata: Array<ICRC1.MetadataField>
    ): String? {
        val value = metadata.find { it.string == "icrc1:logo" }?.value
            ?: return null
        require(value is ICRC1.Value.Text) {
            throw ICRC1TokenException.InvalidMetadataField("icrc1:logo")
        }
        return value.string
    }

    private fun getNatValue(
        key: String,
        metadata: Array<ICRC1.MetadataField>
    ): BigInteger =
        (metadata.find { it.string == key }?.value as? ICRC1.Value.Nat)?.bigInteger
            ?: throw ICRC1TokenException.InvalidMetadataField(key)
}

private fun ICRC1.TransferError.toDataModel(): TransferException =
    when(this) {
        is ICRC1.TransferError.BadBurn -> TransferException.BadBurn(min_burn_amount)
        is ICRC1.TransferError.BadFee -> TransferException.BadFee(expected_fee)
        is ICRC1.TransferError.CreatedInFuture -> TransferException.CreatedInFuture(ledger_time)
        is ICRC1.TransferError.Duplicate -> TransferException.Duplicate(duplicate_of)
        is ICRC1.TransferError.GenericError -> TransferException.GenericError(error_code, message)
        is ICRC1.TransferError.InsufficientFunds -> TransferException.InsufficientFunds(balance)
        ICRC1.TransferError.TemporarilyUnavailable -> TransferException.TemporarilyUnavailable
        ICRC1.TransferError.TooOld -> TransferException.TooOld
    }