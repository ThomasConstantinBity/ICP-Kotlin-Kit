package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeCustom(
    val customTypeDefinition: String,
    override val typeId: String = customTypeDefinition,
    override val optionalType: OptionalType = OptionalType.None,
    override val variableName: String = customTypeDefinition.replaceFirstChar { it.lowercase() }
): CandidType() {

    override val isTypeAlias: Boolean = true
    override fun getKotlinType(variableName: String?): String = customTypeDefinition

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        return when {
            typeId != customTypeDefinition -> {
                val variableDefinition = "val ${typeId.replaceFirstChar { it.lowercase() }}: ${getKotlinVariableType()}"
                "class $typeId($variableDefinition): $parentClassname()"
            }
            else -> "object $customTypeDefinition: $parentClassname()"
        }
    }

    companion object : ParserNodeDeclaration<CandidTypeCustom> by reflective()
}