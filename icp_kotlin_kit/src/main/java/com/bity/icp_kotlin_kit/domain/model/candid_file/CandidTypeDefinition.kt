package com.bity.icp_kotlin_kit.domain.model.candid_file

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidType
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeDefinition(
    val id: String,
    val candidType: CandidType
) {

    fun getKotlinDefinition(): String = candidType.getKotlinDefinition(id)

    fun getKotlinClassDefinition(): String = candidType.getKotlinDefinition(id)

    companion object : ParserNodeDeclaration<CandidTypeDefinition> by reflective()
}