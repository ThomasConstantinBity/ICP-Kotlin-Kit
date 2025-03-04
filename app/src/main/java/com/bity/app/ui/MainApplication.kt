package com.bity.app.ui

import android.app.Application
import android.util.Log
import com.bity.app.di.appModule
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogHandler
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLogger()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    private fun initLogger() {
        ICPKitLogger.setLogger(object : ICPKitLogHandler {
            override fun logError(message: String?, throwable: Throwable) {
                Log.d("ICPKotlinKit", message, throwable)
            }
        })
    }

}