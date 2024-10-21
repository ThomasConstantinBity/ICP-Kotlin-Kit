package com.bity.icp_kotlin_kit.data.model.error

sealed class RemoteClientError(errorMessage: String? = null): Error(errorMessage) {
    class HttpError(errorCode: Int, errorMessage: String?): RemoteClientError("$errorCode - ${errorMessage ?: ""}")
    class MissingBody: RemoteClientError()
    class CanisterError(
        val rejectCode: String?,
        val rejectMessage: String?,
        val errorCode: String?,
        val errorBody: String?
    ): RemoteClientError(
        """
            Canister error: 
            rejectCode: ${rejectCode ?: ""}, rejectMessage: ${rejectMessage ?: ""}
            errorCode: ${errorCode ?: ""}, errorBody: ${errorBody ?: ""}
        """.trimIndent()
    )
    @OptIn(ExperimentalStdlibApi::class)
    class ParsingError(arg: ByteArray): RemoteClientError("Unable to parse arg: ${arg.toHexString()}")

    class RosettaParsingError(errorMessage: String? = null): RemoteClientError(errorMessage)
}