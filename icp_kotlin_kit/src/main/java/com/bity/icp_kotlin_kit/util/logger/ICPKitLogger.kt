package com.bity.icp_kotlin_kit.util.logger

object ICPKitLogger {

    private var logger: ICPKitLogHandler? = null

    fun setLogger(logger: ICPKitLogHandler?) {
        this.logger = logger
    }

    internal fun logError(message: String? = null, throwable: Throwable) {
        logger?.logError(message, throwable)
    }

    internal fun logInfo(message: String) {
        logger?.logInfo(message)
    }

    internal fun logWarning(message: String) {
        logger?.logWarning(message)
    }

    internal fun logDebug(message: String) {
        logger?.logDebug(message)
    }

    internal fun logVerbose(message: String) {
        logger?.logVerbose(message)
    }

}