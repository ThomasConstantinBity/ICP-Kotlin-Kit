package com.bity.icp_kotlin_kit.data.factory

import com.bity.icp_kotlin_kit.data.service.candid.CandidFileParserServiceImpl
import com.bity.icp_kotlin_kit.data.service.candid.CandidTypeParserServiceImpl
import com.bity.icp_kotlin_kit.domain.service.CandidFileParserService
import com.bity.icp_kotlin_kit.domain.service.CandidTypeParserService

object CandidFileParserServiceFactory {

    fun provideCandidFileParserService(): CandidFileParserService {
        val candidTypeParserService = provideCandidTypeParserService()
        return CandidFileParserServiceImpl(
            candidTypeParserService = candidTypeParserService
        )
    }

    private fun provideCandidTypeParserService(): CandidTypeParserService =
        CandidTypeParserServiceImpl()

}