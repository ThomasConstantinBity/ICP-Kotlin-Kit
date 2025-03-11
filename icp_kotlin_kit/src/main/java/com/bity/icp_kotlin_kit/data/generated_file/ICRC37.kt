package com.bity.icp_kotlin_kit.data.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.request.PollingValues
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification

/**
 * File generated using ICP Kotlin Kit Plugin
 */

typealias ICRC37Subaccount = ByteArray
typealias CollectionApproval = ICRC37.ApprovalInfo

object ICRC37 {

    class Account(
        val owner: ICPPrincipalApiModel,
        val subaccount: ICRC37Subaccount?
    )

    class ApprovalInfo(
        // Approval is given to an ICRC Account
        val spender: Account,
        // The subaccount the token can be transferred out from with the approval
        val from_subaccount: ByteArray?,
        val expires_at: ULong?,
        val memo: ByteArray?,
        val created_at_time: ULong
    )

    class ApproveTokenArg(
        val token_id: BigInteger,
        val approval_info: ApprovalInfo
    )

    sealed class ApproveTokenResult {
        class Ok(
            val bigInteger: BigInteger
        ): ApproveTokenResult()
        class Err(
            val approveTokenError: ApproveTokenError
        ): ApproveTokenResult()
    }

    sealed class ApproveTokenError {
        data object InvalidSpender : ApproveTokenError()
        data object Unauthorized : ApproveTokenError()
        data object NonExistingTokenId : ApproveTokenError()
        data object TooOld : ApproveTokenError()
        class CreatedInFuture(val ledger_time: ULong): ApproveTokenError()

        class GenericError(
            val error_code: BigInteger,
            val message: String
        ): ApproveTokenError()

        class GenericBatchError(
            val error_code: BigInteger,
            val message: String
        ): ApproveTokenError()
    }

    class ApproveCollectionArg(
        val approval_info: ApprovalInfo
    )

    sealed class ApproveCollectionResult {
        class Ok(val bigInteger: BigInteger): ApproveCollectionResult()
        class Err(val approveCollectionError: ApproveCollectionError): ApproveCollectionResult()
    }

    sealed class ApproveCollectionError {
        data object InvalidSpender : ApproveCollectionError()
        data object TooOld : ApproveCollectionError()
        class CreatedInFuture(val ledger_time: ULong): ApproveCollectionError()

        class GenericError(
            val error_code: BigInteger,
            val message: String
        ): ApproveCollectionError()

        class GenericBatchError(
            val error_code: BigInteger,
            val message: String
        ): ApproveCollectionError()
    }

    class RevokeTokenApprovalArg(
        // null revokes matching approvals for all spenders
        val spender: Account?,
        // null refers to the default subaccount
        val from_subaccount: ByteArray?,
        val token_id: BigInteger,
        val memo: ByteArray?,
        val created_at_time: ULong?
    )

    sealed class RevokeTokenApprovalResponse {
        class Ok(val bigInteger: BigInteger): RevokeTokenApprovalResponse()
        class Err(val revokeTokenApprovalError: RevokeTokenApprovalError): RevokeTokenApprovalResponse()
    }
    sealed class RevokeTokenApprovalError {
        data object ApprovalDoesNotExist : RevokeTokenApprovalError()
        data object Unauthorized : RevokeTokenApprovalError()
        data object NonExistingTokenId : RevokeTokenApprovalError()
        data object TooOld : RevokeTokenApprovalError()
        class CreatedInFuture(val ledger_time: ULong): RevokeTokenApprovalError()

        class GenericError(
            val error_code: BigInteger,
            val message: String
        ): RevokeTokenApprovalError()

        class GenericBatchError(
            val error_code: BigInteger,
            val message: String
        ): RevokeTokenApprovalError()
    }

    class RevokeCollectionApprovalArg(
        // null revokes approvals for all spenders that match the remaining parameters
        val spender: Account?,
        // null refers to the default subaccount
        val from_subaccount: ByteArray?,
        val memo: ByteArray?,
        val created_at_time: ULong?
    )

    sealed class RevokeCollectionApprovalResult {
        class Ok(val bigInteger: BigInteger): RevokeCollectionApprovalResult()
        class Err(val revokeCollectionApprovalError: RevokeCollectionApprovalError): RevokeCollectionApprovalResult()
    }

    sealed class RevokeCollectionApprovalError {
        data object ApprovalDoesNotExist : RevokeCollectionApprovalError()
        data object TooOld : RevokeCollectionApprovalError()
        class CreatedInFuture(val ledger_time: ULong): RevokeCollectionApprovalError()
        class GenericError(
            val error_code: BigInteger,
            val message: String
        ): RevokeCollectionApprovalError()
        class GenericBatchError(
            val error_code: BigInteger,
            val message: String
        ): RevokeCollectionApprovalError()
    }

    class IsApprovedArg(
        val spender: Account,
        val from_subaccount: ByteArray?,
        val token_id: BigInteger
    )

    class TokenApproval(
        val token_id: BigInteger,
        val approval_info: ApprovalInfo
    )

    class TransferFromArg(
        // The subaccount of the caller (used to identify the spender)
        val spender_subaccount: ByteArray?,
        val from: Account,
        val to: Account,
        val token_id: BigInteger,
        val memo: ByteArray?,
        val created_at_time: ULong?
    )

    sealed class TransferFromResult {
        class Ok(val bigInteger: BigInteger): TransferFromResult()
        class Err(val transferFromError: TransferFromError): TransferFromResult()
    }

    sealed class TransferFromError {
        data object InvalidRecipient : TransferFromError()
        data object Unauthorized : TransferFromError()
        data object NonExistingTokenId : TransferFromError()
        data object TooOld : TransferFromError()
        class CreatedInFuture(val ledger_time: ULong): TransferFromError()
        class Duplicate(val duplicate_of: BigInteger): TransferFromError()
        class GenericError(
            val error_code: BigInteger,
            val message: String
        ): TransferFromError()
        class GenericBatchError(
            val error_code: BigInteger,
            val message: String
        ): TransferFromError()
    }

    class ICRC37Service(
        private val canister: ICPPrincipal
    ) {
        suspend fun icrc37_max_approvals_per_token_or_collection (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger? {
            val icpQuery = ICPQuery(
                methodName = "icrc37_max_approvals_per_token_or_collection",
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

        suspend fun icrc37_max_revoke_approvals (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): BigInteger? {
            val icpQuery = ICPQuery(
                methodName = "icrc37_max_revoke_approvals",
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

        suspend fun icrc37_approve_tokens (
            approveTokenArg: Array<ApproveTokenArg>,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<ApproveTokenResult> {
            val icpQuery = ICPQuery(
                methodName = "icrc37_approve_tokens",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = approveTokenArg,
                        expectedClass = Array::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun icrc37_approve_collection (
            approveCollectionArg: Array<ApproveCollectionArg>,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<ApproveCollectionError> {
            val icpQuery = ICPQuery(
                methodName = "icrc37_approve_collection",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = approveCollectionArg,
                        expectedClass = Array::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun icrc37_revoke_token_approvals (
            revokeTokenApprovalArg: Array<RevokeTokenApprovalArg>,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<RevokeTokenApprovalResponse> {
            val icpQuery = ICPQuery(
                methodName = "icrc37_revoke_token_approvals",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = revokeTokenApprovalArg,
                        expectedClass = Array::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun icrc37_revoke_collection_approvals (
            revokeCollectionApprovalArg: Array<RevokeCollectionApprovalArg>,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<RevokeCollectionApprovalResult> {
            val icpQuery = ICPQuery(
                methodName = "icrc37_revoke_collection_approvals",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = revokeCollectionApprovalArg,
                        expectedClass = Array::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun icrc37_is_approved (
            isApprovedArg: Array<IsApprovedArg>,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<Boolean> {
            val icpQuery = ICPQuery(
                methodName = "icrc37_is_approved",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = isApprovedArg,
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

        suspend fun icrc37_get_token_approvals (
            token_id: BigInteger,
            prev: TokenApproval?,
            take: BigInteger?,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<TokenApproval> {
            val icpQuery = ICPQuery(
                methodName = "icrc37_get_token_approvals",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = token_id,
                        expectedClass = BigInteger::class,
                        expectedClassNullable = false
                    ),
                    ValueToEncode(
                        arg = prev,
                        expectedClass = TokenApproval::class,
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

        suspend fun icrc37_get_collection_approvals (
            owner: Account,
            prev: CollectionApproval?,
            take: BigInteger?,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<CollectionApproval> {
            val icpQuery = ICPQuery(
                methodName = "icrc37_get_collection_approvals",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = owner,
                        expectedClass = Account::class,
                        expectedClassNullable = false
                    ),
                    ValueToEncode(
                        arg = prev,
                        expectedClass = CollectionApproval::class,
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

        suspend fun icrc37_transfer_from (
            transferFromArg: Array<TransferFromArg>,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): Array<TransferFromResult> {
            val icpQuery = ICPQuery(
                methodName = "icrc37_transfer_from",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = transferFromArg,
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
}