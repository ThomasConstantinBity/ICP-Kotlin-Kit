package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeDefinition(
    val id: String,
    val candidType: CandidType
) {

    fun getKotlinClassDefinition(): String = candidType.getKotlinClassDefinition(id)

    companion object : ParserNodeDeclaration<CandidTypeDefinition> by reflective()
}