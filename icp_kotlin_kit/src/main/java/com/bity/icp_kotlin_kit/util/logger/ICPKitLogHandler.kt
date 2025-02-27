package com.bity.icp_kotlin_kit.util.logger

interface ICPKitLogHandler {
    fun logError(message: String? = null, throwable: Throwable) { }
    fun logInfo(message: String) { }
    fun logWarning(message: String) { }
    fun logDebug(message: String) { }
    fun logVerbose(message: String) { }
}