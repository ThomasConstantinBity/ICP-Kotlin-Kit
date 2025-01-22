package com.bity.icp_kotlin_kit.file_parser.file_generator

import com.bity.icp_kotlin_kit.file_parser.candid_parser.CandidFileParser
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassParameter
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.IDLFileDeclaration
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLType
import com.bity.icp_kotlin_kit.file_parser.file_generator.helper.IDLTypeHelper
import com.bity.icp_kotlin_kit.file_parser.file_generator.helper.UnnamedClassHelper

class KotlinFileGenerator(
    private val fileName: String,
    private val packageName: String,
    didFileContent: String,
) {

    private val kotlinFileText = StringBuilder()
    private val idlFileDeclaration: IDLFileDeclaration = CandidFileParser.parseFile(didFileContent)

    fun generateKotlinFile(): String {
        writeFileHeader()
        writeTypeAliases()
        kotlinFileText.appendLine("object $fileName {")
        kotlinFileText.appendLine()
        writeKotlinClasses()
        writeServiceClass()
        kotlinFileText.append("}")
        return kotlinFileText.toString()
    }

    private fun writeFileHeader() {
        kotlinFileText.appendLine(
            """
                package $packageName
                
                import java.math.BigInteger
                import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
                import com.bity.icp_kotlin_kit.data.repository.ICPQuery
                import com.bity.icp_kotlin_kit.data.model.ValueToEncode
                import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
                import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
                import com.bity.icp_kotlin_kit.domain.request.PollingValues
                import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
                import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
            """.trimIndent()
        )
        kotlinFileText.appendLine("\n/**\n * File generated using ICP Kotlin Kit Library\n */")
    }

    private fun writeTypeAliases() {


    }

    private fun writeKotlinClasses() {
        idlFileDeclaration.candidParsedTypes
            // TODO, filter
            /* .filter {
                it.candidTypeDefinition.candidType !is CandidTypeVariant
                        || it.candidTypeDefinition.candidType !is CandidTypeRecord
            } */
            .forEach { candidParsedType ->
                writeCandidDefinition(candidParsedType.candidDefinition)
                val kotlinClassDefinition = candidParsedType.candidTypeDefinition
                    .candidType.getKotlinDefinition(candidParsedType.candidTypeDefinition.id)
                kotlinClassDefinition.split("\n")
                    .forEach { line ->
                        kotlinFileText.appendLine("\t$line")
                    }
            }
    }

    private fun writeServiceClass() {
        idlFileDeclaration.service ?: return
        val serviceDefinition = idlFileDeclaration.service
            .getKotlinClassDefinition(fileName)
        serviceDefinition.split("\n").forEach {
            kotlinFileText.appendLine("\t$it")
        }
    }

    private fun writeCandidDefinition(candidDefinition: String) {
        kotlinFileText.appendLine("\t/**")
        candidDefinition.split("\n").forEach {
            kotlinFileText.appendLine("\t * $it")
        }
        kotlinFileText.appendLine("\t */")
    }

    private fun generateFunctionParams(
        icpQuery: KotlinClassDefinition.ICPQuery,
        idlTypes: List<IDLType>
    ): List<KotlinClassParameter> {
        return idlTypes
            /* .filter { it !is IDLTypeNull } */
            .map { idlType ->
            var className: String? = null
            val innerType = IDLTypeHelper.getInnerTypeToDeclare(idlType)
            if(innerType != null) {
                className = UnnamedClassHelper.getUnnamedClassName()
                val innerClass = IDLTypeHelper.kotlinDefinition(
                    idlType = innerType,
                    className = className
                )
                icpQuery.innerClasses.add(innerClass)
            }
            idlType.getKotlinClassParameter(className)
        }
    }

    private fun formatKotlinCode(input: StringBuilder): String {
        val indentedCode = StringBuilder()
        var indentLevel = 0
        var imporst = input.lines()
            .count { it.trim().startsWith("import ") }

        input.lines()
            .filter { it.isNotBlank() }
            .forEachIndexed { index, line ->

                if ((line.trim().startsWith("}") || line.trim().startsWith(")")) && indentLevel > 0)
                    indentLevel--

                val indentedLine = if(line.trim().startsWith("*")) " ${line.trim()}" else "\t".repeat(indentLevel) + line.trim()
                indentedCode.appendLine(indentedLine)

                // Separate package form import statements
                if(index == 0 || line.trim().startsWith("}") || line.trim().startsWith(")"))
                    indentedCode.appendLine()
                if(line.trim().startsWith("import "))
                    imporst--
                if(imporst == 0) {
                    imporst--
                    indentedCode.appendLine()
                }

                if (line.trim().endsWith("{") || line.trim().endsWith("("))
                    indentLevel++
            }

        return indentedCode.toString()
    }
}