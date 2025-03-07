package com.bity.app.di

import com.bity.icp_kotlin_kit.di.nftService
import org.koin.dsl.module

val dataModule = module {
    single { nftService }
}