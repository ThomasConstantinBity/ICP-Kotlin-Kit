package com.bity.icp_kotlin_kit.util.logger

object ICPKitLogger {

    private var logger: ICPKitLogHandler? = null

    fun setLogger(logger: ICPKitLogHandler?) {
        this.logger = logger
    }

    fun logError(message: String? = null, throwable: Throwable) {
        logger?.logError(message, throwable)
    }

    fun logInfo(message: String) {
        logger?.logInfo(message)
    }

    fun logWarning(message: String) {
        logger?.logWarning(message)
    }

    fun logDebug(message: String) {
        logger?.logDebug(message)
    }

    fun logVerbose(message: String) {
        logger?.logVerbose(message)
    }

}