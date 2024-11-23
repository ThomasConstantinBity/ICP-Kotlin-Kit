package com.bity.icp_kotlin_kit.file_parser.file_generator

import com.bity.icp_kotlin_kit.file_parser.candid_parser.CandidFileParser
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassParameter
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.IDLFileDeclaration
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeCustom
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNull
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeVec
import com.bity.icp_kotlin_kit.file_parser.file_generator.helper.IDLTypeHelper
import com.bity.icp_kotlin_kit.file_parser.file_generator.helper.UnnamedClassHelper

class KotlinFileGenerator(
    private val fileName: String,
    packageName: String,
    didFileContent: String,
) {
    private val header = """
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
        /**
         * File generated using ICP Kotlin Kit Plugin
         */
    """

    private val idlFileDeclaration: IDLFileDeclaration =
        CandidFileParser.parseFile(didFileContent)
    private val kotlinFileText = StringBuilder(header)

    fun generateKotlinFile(): String {

        idlFileDeclaration.types.forEach {
            kotlinFileText.appendLine(it.getKotlinClassDefinition().kotlinDefinition())
        }
        writeService()
        return formatKotlinCode(kotlinFileText)
    }

    private fun writeTypeAliases() {
        idlFileDeclaration.types
            .filter {
                it is IDLTypeCustom
                        || it is IDLTypeVec
            }
            .map { type ->
                when(type) {
                    is IDLTypeCustom -> {
                        requireNotNull(type.typeDef)
                        requireNotNull(type.type)
                        KotlinClassDefinition.TypeAlias(
                            typeAliasId = type.typeDef,
                            type = type.type,
                            typeClassName = fileName
                        )
                    }
                    is IDLTypeVec -> {
                        requireNotNull(type.vecDeclaration)
                        KotlinClassDefinition.TypeAlias(
                            typeAliasId = type.vecDeclaration,
                            type = type,
                            typeClassName = fileName
                        )
                    }
                    else -> throw Error("$type can't be a typealias")
                }
            }
            .forEach { kotlinFileText.appendLine(it.kotlinDefinition()) }
    }

    private fun writeClasses() {
        idlFileDeclaration.types
            .filter { it !is IDLTypeCustom && it !is IDLTypeVec }
            .map { it.getKotlinClassDefinition() }
            .forEach { kotlinFileText.appendLine(it.kotlinDefinition()) }
    }

    private fun writeService() {
        val serviceFunctions = idlFileDeclaration.services
            .map {
                requireNotNull(it.id)
                val icpQuery = KotlinClassDefinition.ICPQuery(
                    comment = it.comment,
                    queryName = it.id,
                    funType = it.funType
                )
                val inputParams = generateFunctionParams(
                    icpQuery = icpQuery,
                    idlTypes = it.inputArgs
                )
                val outputParam = generateFunctionParams(
                    icpQuery = icpQuery,
                    idlTypes = it.outputArgs
                )
                icpQuery.inputArgs.addAll(inputParams)
                icpQuery.outputArgs.addAll(outputParam)
                icpQuery
            }
        kotlinFileText.appendLine(
            """
                class ${fileName}Service(
                    private val canister: ICPPrincipal
                ) {
                    ${serviceFunctions.joinToString(
                        prefix = "\n",
                        separator = "\n\n"
                    ) { it.kotlinDefinition() }}
                }
            """.trimIndent()
        )
    }

    private fun generateFunctionParams(
        icpQuery: KotlinClassDefinition.ICPQuery,
        idlTypes: List<IDLType>
    ): List<KotlinClassParameter> {
        return idlTypes
            .filter { it !is IDLTypeNull }
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