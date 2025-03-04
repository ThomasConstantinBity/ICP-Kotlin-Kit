package com.bity.app.ui

import android.app.Application
import android.util.Log
import com.bity.app.di.appModule
import com.bity.icp_kotlin_kit.data.service.nft.custom.chain_fusion_toonis.ChainFusionToonisNFTService
import com.bity.icp_kotlin_kit.domain.generated_file.ChainFusionToonis
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogHandler
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import com.bity.icp_kotlin_kit.util.nft_service.NFTServiceUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLogger()
        initCustoICPNFTServices()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    private fun initCustoICPNFTServices() {

    }

    private fun initLogger() {
        ICPKitLogger.setLogger(object : ICPKitLogHandler {
            override fun logError(message: String?, throwable: Throwable) {
                Log.d("ICPKotlinKit", message, throwable)
            }
        })
    }

}