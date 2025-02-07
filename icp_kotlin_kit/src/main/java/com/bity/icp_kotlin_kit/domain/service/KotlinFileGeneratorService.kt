package com.bity.icp_kotlin_kit.domain.service

interface KotlinFileGeneratorService {

    fun parseAndGetKotlinFile(
        candidFileText: String,
        packageName: String,
    ): String

}