package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.service.candid.CandidFileParserServiceImpl
import com.bity.icp_kotlin_kit.data.service.candid.CandidTypeParserServiceImpl
import com.bity.icp_kotlin_kit.data.service.candid.KotlinFileGeneratorServiceImpl
import com.bity.icp_kotlin_kit.domain.service.CandidFileParserService
import com.bity.icp_kotlin_kit.domain.service.CandidTypeParserService
import com.bity.icp_kotlin_kit.domain.service.KotlinFileGeneratorService

object CandidFileParserServiceFactory {

    fun provideKotlinFileGeneratorService() : KotlinFileGeneratorService {
        val candidFileParserService = provideCandidFileParserService()
        return KotlinFileGeneratorServiceImpl(
            candidFileParserService = candidFileParserService
        )
    }

    private fun provideCandidFileParserService(): CandidFileParserService {
        val candidTypeParserService = provideCandidTypeParserService()
        return CandidFileParserServiceImpl(
            candidTypeParserService = candidTypeParserService
        )
    }

    private fun provideCandidTypeParserService(): CandidTypeParserService =
        CandidTypeParserServiceImpl()

}