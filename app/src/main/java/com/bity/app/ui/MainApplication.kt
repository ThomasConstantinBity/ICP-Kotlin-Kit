package com.bity.app.ui

import android.app.Application
import com.bity.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}