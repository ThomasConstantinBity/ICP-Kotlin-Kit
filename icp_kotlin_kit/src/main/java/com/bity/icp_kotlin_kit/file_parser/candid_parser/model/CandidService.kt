package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidService(
    val functions: List<CandidServiceFunction>
) {

    fun getKotlinClassDefinition(): String {
        return """
            class Service(private val canister: ICPPrincipal) {
                TODO()
                ${functions.joinToString { it.functionName }}
            }
        """.trimIndent()
    }

    companion object : ParserNodeDeclaration<CandidService> by reflective()
}