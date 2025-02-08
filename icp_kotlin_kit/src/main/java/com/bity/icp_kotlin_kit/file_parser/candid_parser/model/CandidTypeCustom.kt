package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeCustom(
    override val typeId: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val customTypeDefinition: String,
    override val variableName: String = customTypeDefinition.replaceFirstChar { it.lowercase() }
): CandidType() {

    override val isTypeAlias: Boolean = true
    override fun getKotlinType(variableName: String?): String = customTypeDefinition

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        return when {
            typeId != null -> {
                val variableDefinition = "val $customTypeDefinition: ${getKotlinVariableType()}"
                "class $typeId($variableDefinition): $parentClassname()"
            }
            else -> "object $customTypeDefinition: $parentClassname()"
        }
    }

    // override fun shouldDeclareInnerClass(): Boolean = false

    // override fun getKotlinClassName(candidTypeDefinitionId: String?): String = TODO() // customTypeDefinition

    /*override fun getKotlinDefinition(candidTypeDefinitionId: String): String {
        TODO()
        *//*val typealiasDefinition = "typealias $candidTypeDefinitionId = "
        return when(optionalType) {
            OptionalType.None -> "$typealiasDefinition $customTypeDefinition"
            OptionalType.Optional -> "$typealiasDefinition $customTypeDefinition?"
            OptionalType.DoubleOptional -> TODO()
        }*//*
    }*/
    /*override fun getKotlinVariableType(): String =
        when(optionalType) {
            OptionalType.None -> customTypeDefinition
            OptionalType.Optional -> "$customTypeDefinition?"
            OptionalType.DoubleOptional -> "List<List<$customTypeDefinition?>>"
        }*/

    // override fun getKotlinDefinitionForSealedClass(className: String): String = TODO()

    companion object : ParserNodeDeclaration<CandidTypeCustom> by reflective()
}