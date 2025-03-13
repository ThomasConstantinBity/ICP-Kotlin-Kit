package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.util.logger.ICPKitLogHandler
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class KotlinFileGeneratorServiceTest {

    @BeforeEach
    fun setup() {
        ICPKitLogger.setLogger(object : ICPKitLogHandler {
            override fun logError(message: String?, throwable: Throwable) {
                throwable.printStackTrace()
                println(message)
            }
        })
    }

    @Test
    fun `parse file`() {
        val fileName = "ICRC1Oracle"
        val filePath = "src/test/resources/candid_file/$fileName.did"
        val candidFileText = File(filePath).readText()
        val generatedKotlinFile = KotlinFileGeneratorService.parseAndGetKotlinFile(
            candidFileText = candidFileText,
            fileName = fileName,
            packageName = "com.bity.icp_kotlin_kit.domain.generated_file"
        )
        println(generatedKotlinFile)
    }

    @Test
    fun `parse all files`() {
        val folder = File("src/test/resources/candid_file")
        folder.listFiles()?.forEach {
            val candidFileText = it.readText()
            val generatedKotlinFile = KotlinFileGeneratorService.parseAndGetKotlinFile(
                candidFileText = candidFileText,
                fileName = it.name.split(".").first(),
                packageName = "com.bity.icp_kotlin_kit.domain.generated_file"
            )
            println(generatedKotlinFile)
        }
    }

}