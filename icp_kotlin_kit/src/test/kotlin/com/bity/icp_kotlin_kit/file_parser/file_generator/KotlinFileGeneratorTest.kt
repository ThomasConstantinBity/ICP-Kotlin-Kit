package com.bity.icp_kotlin_kit.file_parser.file_generator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File

class KotlinFileGeneratorTest {

    @Test
    fun `parse file`() {
        val fileName = "Tokens"
        val filePath = "src/test/resources/candid_file/$fileName.did"
        // val outputFilePath = "src/test/resources/generated_candid_file/EXT.kt"
        println(
            KotlinFileGenerator(
                fileName = fileName,
                packageName = "com.bity.icp_kotlin_kit.domain.generated_file",
                didFileContent = File(filePath).readText()
            ).generateKotlinFile()
        )
    }

    @Test
    fun `parse all files`() {
        val folder = File("src/test/resources/candid_file")
        folder.listFiles()?.forEach {
            val filePath = it.path
            val fileName = it.name.replace(".did", "")
            val outputFilePath = "src/test/resources/generated_candid_file/${fileName}.kt"
            TODO()
        }
    }

    companion object {

        @JvmStatic
        @BeforeAll
        fun `clear generated_candid_file folder`() {
            // val folder = File("src/test/resources/generated_candid_file")
            // folder.listFiles()?.forEach { it.deleteRecursively() }
        }
    }
}