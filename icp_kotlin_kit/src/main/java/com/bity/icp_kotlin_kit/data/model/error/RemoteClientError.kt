package com.bity.icp_kotlin_kit.data.model.error

sealed class RemoteClientError(errorMessage: String? = null): Error(errorMessage) {
    class HttpError(errorCode: Int, errorMessage: String?): RemoteClientError("$errorCode - ${errorMessage ?: ""}")
    class MissingBody: RemoteClientError()
    class CanisterError(
        rejectCode: String?,
        rejectMessage: String?,
        errorCode: String?,
        errorBody: String?
    ): RemoteClientError(
        """
            Canister error: 
            rejectCode: ${rejectCode ?: ""}, rejectMessage: ${rejectMessage ?: ""}
            errorCode: ${errorCode ?: ""}, errorBody: ${errorBody ?: ""}
        """.trimIndent()
    )
    class InvalidToken(
        tokenAddress: String
    ): RemoteClientError("Invalid token; $tokenAddress")
    class GetUserNFTHoldingsGenericError(message: String): RemoteClientError(message)
}