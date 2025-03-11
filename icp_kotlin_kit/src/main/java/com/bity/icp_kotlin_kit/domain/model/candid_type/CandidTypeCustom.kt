package com.bity.icp_kotlin_kit.domain.model.candid_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeCustom(
    override val typeId: String? = null,
    val customTypeDefinition: String,
    override val optionalType: OptionalType = OptionalType.None,
    override val variableName: String? = null
): CandidType() {

    override val isTypeAlias: Boolean = true
    override fun getKotlinType(variableName: String?): String = customTypeDefinition

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        return when {

            /**
             * type CandyShared = variant {
             *     Option : opt CandyShared;
             * }
             */
            variableName != null -> {
                val variableDefinition = "val $variableName: ${getKotlinVariableType()}"
                val className = variableName
                "class $className($variableDefinition): $parentClassname()"
            }

            typeId != null -> {
                val variableDefinition = "val ${typeId.replaceFirstChar { it.lowercase() }}: ${getKotlinVariableType()}"
                "class $typeId($variableDefinition): $parentClassname()"
            }

            else -> "object $customTypeDefinition: $parentClassname()"
        }
    }

    companion object : ParserNodeDeclaration<CandidTypeCustom> by reflective()
}