package com.bity.icp_kotlin_kit.util.logger

object ICPKitLogger {

    private var logger: ICPKitLogHandler? = null

    fun setLogger(logger: ICPKitLogHandler?) {
        this.logger = logger
    }

    fun logError(message: String, throwable: Throwable? = null) {
        logger?.logError(message, throwable)
    }

    fun logInfo(message: String) {
        logger?.logInfo(message)
    }

    fun logWarning(message: String, throwable: Throwable? = null) {
        logger?.logWarning(message, throwable)
    }

    fun logDebug(message: String, throwable: Throwable? = null) {
        logger?.logDebug(message, throwable)
    }

    fun logVerbose(message: String, throwable: Throwable? = null) {
        logger?.logVerbose(message, throwable)
    }

}