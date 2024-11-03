package com.bity.icp_kotlin_kit.domain.generated_file

import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.domain.request.PollingValues
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification

/**
 * File generated using ICP Kotlin Kit Plugin
 */
private typealias Subaccount = ByteArray

class Account(
    val owner: ICPPrincipalApiModel,
    val subaccount: Subaccount?
)

sealed class Value {

    class Blob(val byteArray: ByteArray) : Value()
    class Text(val string: String) : Value()
    class Nat(val bigInteger: BigInteger) : Value()
    class Int(val bigInteger: BigInteger) : Value()
    class Array(val values: kotlin.Array<Value>) : Value()

    class Map(val values: kotlin.Array<_ArrayClass>) : Value() {
        class _ArrayClass(
            val string: String,
            val value: Value
        )
    }
}

class TransferArg(
    // The subaccount to transfer the token from
    val from_subaccount: ByteArray?,
    val to: Account,
    val token_id: BigInteger,
    val memo: ByteArray?,
    val created_at_time: ULong?
)

sealed class TransferResult {
    class Ok(val bigInteger: BigInteger) : TransferResult()
    class Err(val transferError: TransferError) : TransferResult()
}

sealed class TransferError {
    data object NonExistingTokenId : TransferError()
    data object InvalidRecipient : TransferError()
    data object Unauthorized : TransferError()
    data object TooOld : TransferError()
    class CreatedInFuture(val ledger_time: ULong) : TransferError()
    class Duplicate(val duplicate_of: BigInteger) : TransferError()
    class GenericError(
        val error_code: BigInteger,
        val message: String
    ) : TransferError()
    class GenericBatchError(
        val error_code: BigInteger,
        val message: String
    ) : TransferError()
}

class DBANFTService(
    private val canister: ICPPrincipal
) {
    suspend fun icrc7_collection_metadata(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Array<UnnamedClass0> {

        val icpQuery = ICPQuery(
            methodName = "icrc7_collection_metadata",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    class UnnamedClass0(
        val string: String,
        val value: Value
    )

    suspend fun icrc7_symbol(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): String {

        val icpQuery = ICPQuery(
            methodName = "icrc7_symbol",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun icrc7_name(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): String {

        val icpQuery = ICPQuery(
            methodName = "icrc7_name",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun icrc7_description(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): String? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_description",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_logo(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): String? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_logo",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_total_supply(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger {

        val icpQuery = ICPQuery(
            methodName = "icrc7_total_supply",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun icrc7_supply_cap(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_supply_cap",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_max_query_batch_size(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_max_query_batch_size",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_max_update_batch_size(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_max_update_batch_size",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_default_take_value(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_default_take_value",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_max_take_value(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_max_take_value",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_max_memo_size(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_max_memo_size",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_atomic_batch_transfers(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Boolean? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_atomic_batch_transfers",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_tx_window(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_tx_window",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_permitted_drift(
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger? {

        val icpQuery = ICPQuery(
            methodName = "icrc7_permitted_drift",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun icrc7_token_metadata(
        token_ids: Array<BigInteger>,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Array<UnnamedClass1> {

        val icpQuery = ICPQuery(
            methodName = "icrc7_token_metadata",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = token_ids,
                    expectedClass = Array::class,
                    arrayType = BigInteger::class,
                    expectedClassNullable = false
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    class UnnamedClass1(
        val bigInteger: BigInteger,
        val unnamedClass2: UnnamedClass2?
    ) {

        class UnnamedClass2(
            val string: String,
            val value: Value
        )

    }

    suspend fun icrc7_owner_of(
        token_ids: Array<BigInteger>,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Array<Account> {

        val icpQuery = ICPQuery(
            methodName = "icrc7_owner_of",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = token_ids,
                    expectedClass = Array::class,
                    expectedClassNullable = false
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun icrc7_balance_of(
        account: Array<Account>,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Array<BigInteger> {

        val icpQuery = ICPQuery(
            methodName = "icrc7_balance_of",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = account,
                    expectedClass = Array::class,
                    expectedClassNullable = false
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun icrc7_tokens(
        prev: BigInteger?,
        take: BigInteger?,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Array<BigInteger> {

        val icpQuery = ICPQuery(
            methodName = "icrc7_tokens",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = prev,
                    expectedClass = BigInteger::class,
                    expectedClassNullable = true
                ),
                ValueToEncode(
                    arg = take,
                    expectedClass = BigInteger::class,
                    expectedClassNullable = true
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun icrc7_tokens_of(
        account: Account,
        prev: BigInteger?,
        take: BigInteger?,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Array<BigInteger> {

        val icpQuery = ICPQuery(
            methodName = "icrc7_tokens_of",
            canister = canister
        )

        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = account,
                    expectedClass = Account::class,
                    expectedClassNullable = false
                ),
                ValueToEncode(
                    arg = prev,
                    expectedClass = BigInteger::class,
                    expectedClassNullable = true
                ),
                ValueToEncode(
                    arg = take,
                    expectedClass = BigInteger::class,
                    expectedClassNullable = true
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun icrc7_transfer(
        transferArg: Array<TransferArg>,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Array<TransferResult> {

        val icpQuery = ICPQuery(
            methodName = "icrc7_transfer", canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(
                ValueToEncode(
                    arg = transferArg,
                    expectedClass = Array::class,
                    expectedClassNullable = false
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }
}