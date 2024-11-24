package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidService(
    val functions: List<CandidServiceFunction>
) {

    fun getKotlinClassDefinition(serviceName: String): String {
        val serviceDefinition = StringBuilder("class ${serviceName}Service(private val canister: ICPPrincipal){")
        serviceDefinition.appendLine("\n")

        functions.forEach { function ->
            val functionDefinition = function.getKotlinFunctionDefinition()
            functionDefinition.split("\n").forEach {
                serviceDefinition.appendLine("\t$it")
            }
        }

        serviceDefinition.appendLine("}")
        return serviceDefinition.toString()
    }

    companion object : ParserNodeDeclaration<CandidService> by reflective()
}