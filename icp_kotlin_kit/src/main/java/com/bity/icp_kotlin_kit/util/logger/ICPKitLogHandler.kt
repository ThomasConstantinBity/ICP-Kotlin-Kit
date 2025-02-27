package com.bity.icp_kotlin_kit.util.logger

interface ICPKitLogHandler {
    fun logError(message: String, throwable: Throwable? = null)
    fun logInfo(message: String)
    fun logWarning(message: String, throwable: Throwable? = null)
    fun logDebug(message: String, throwable: Throwable? = null)
    fun logVerbose(message: String, throwable: Throwable? = null)
}