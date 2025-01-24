package com.bity.icp_kotlin_kit.data.model.error

import com.bity.icp_kotlin_kit.data.datasource.api.response.model.enum.RejectCodeApiModel

sealed class PollingError(errorMessage: String? = null): Error(errorMessage) {
    class Timeout: PollingError()
    class RequestIsDone: PollingError()
    class ParsingError(errorMessage: String? = null): PollingError(errorMessage)
    class RequestRejected(rejectCode: RejectCodeApiModel, rejectMessage: String?):
        Error("Request has been rejected with error code ${rejectCode.name}: ${rejectMessage ?: ""}")
}