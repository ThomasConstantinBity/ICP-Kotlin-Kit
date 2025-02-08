package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.data.factory.CandidFileParserServiceFactory.provideKotlinFileGeneratorService
import org.junit.jupiter.api.Test
import java.io.File

class KotlinFileGeneratorServiceTest {

    private val kotlinFileGeneratorService = provideKotlinFileGeneratorService()

    @Test
    fun `parse file`() {
        val fileName = "OrigynNFT"
        val filePath = "src/test/resources/candid_file/$fileName.did"
        val candidFileText = File(filePath).readText()
        val generatedKotlinFile = kotlinFileGeneratorService.parseAndGetKotlinFile(
            candidFileText = candidFileText,
            packageName = "com.example.test"
        )
        println(generatedKotlinFile)
    }

    @Test
    fun `parse all files`() {
        val folder = File("src/test/resources/candid_file")
        folder.listFiles()?.forEach {
            val candidFileText = it.readText()
            val generatedKotlinFile = kotlinFileGeneratorService.parseAndGetKotlinFile(
                candidFileText = candidFileText,
                packageName = "com.example.test"
            )
            println(generatedKotlinFile)
        }
    }

}