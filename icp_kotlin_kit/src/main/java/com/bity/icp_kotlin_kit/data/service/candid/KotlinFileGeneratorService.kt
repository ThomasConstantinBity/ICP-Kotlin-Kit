package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.domain.model.candid_file.CandidParsedFile

object KotlinFileGeneratorService {

    private val imports = """
        import java.math.BigInteger
        import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
        import com.bity.icp_kotlin_kit.data.model.ValueToEncode
        import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
        import com.bity.icp_kotlin_kit.data.repository.ICPQuery
        import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
        import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
        import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
        import com.bity.icp_kotlin_kit.domain.request.PollingValues
    """.trimIndent()
    private val importSize = imports.split("\n").size

    fun parseAndGetKotlinFile(
        candidFileText: String,
        fileName: String,
        packageName: String
    ): String {
        val kotlinFile = StringBuilder()
        kotlinFile.appendLine("package $packageName")
        kotlinFile.appendLine()
        kotlinFile.appendLine(imports)
        kotlinFile.appendLine()

        val candidParsedFile = CandidFileParserService.parseCandidFile(candidFileText)
        kotlinFile.appendLine(candidParsedFile.getTypealiasesDefinition())
        kotlinFile.appendLine()
        writeKotlinObject(
            objectName = fileName,
            stringBuilder = kotlinFile,
            candidParsedFile = candidParsedFile
        )
        return normalizeKotlinFileDefinition(kotlinFile.toString())
    }

    private fun writeKotlinObject(
        objectName: String,
        stringBuilder: StringBuilder,
        candidParsedFile: CandidParsedFile
    ) {
        stringBuilder.appendLine("object $objectName {")
        stringBuilder.appendLine()

        stringBuilder.appendLine(candidParsedFile.getClassesDefinition())
        stringBuilder.appendLine(candidParsedFile.getServiceDefinition())

        stringBuilder.appendLine()
        stringBuilder.appendLine("}")
    }

    private fun normalizeKotlinFileDefinition(kotlinFile: String): String {
        val normalizedKotlinFile = StringBuilder()
        val lines = kotlinFile.replace("""\n{3,}""".toRegex(), "\n") .split("\n")
        lines.take(importSize + 3).forEach {
            normalizedKotlinFile.appendLine(it)
        }

        var indent = 0
        lines.takeLast(lines.size - importSize - 3).forEach {

            when {
                indent == 0 -> { }
                it.endsWith(")") -> indent--
                it.endsWith("}") -> indent--
                else -> { }
            }

            val kotlinLine = when {
                it.startsWith("*") -> "${("\t").repeat(indent)} ${it.trim()}"
                else -> "${("\t").repeat(indent)} ${it.trim()}"
            }
            normalizedKotlinFile.appendLine(kotlinLine)

            when {
                it.endsWith("{") -> indent++
                it.endsWith("(") -> indent++
                else -> { }
            }
        }
        return normalizedKotlinFile.toString()
    }

}