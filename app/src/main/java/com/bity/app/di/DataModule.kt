package com.bity.app.di

import com.bity.icp_kotlin_kit.di.nftRepository
import org.koin.dsl.module

val dataModule = module {
    single { nftRepository }
}