package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeCustom(
    override val typeId: String,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val typeDefinition: String
): CandidType() {

    override fun shouldDeclareInnerClass(): Boolean = false

    override fun getKotlinClassName(candidTypeDefinitionId: String?): String = typeDefinition

    override fun getKotlinDefinition(candidTypeDefinitionId: String): String {
        val typealiasDefinition = "typealias $candidTypeDefinitionId = "
        return when(optionalType) {
            OptionalType.None -> "$typealiasDefinition $typeDefinition"
            OptionalType.Optional -> "$typealiasDefinition $typeDefinition?"
            OptionalType.DoubleOptional -> TODO()
        }
    }
    override fun getKotlinVariableType(): String = typeDefinition

    override fun getKotlinDefinitionForSealedClass(className: String): String {
        return when {
            typeId == null -> "object $typeDefinition: $className()"
            else -> "data class $typeId(val ${typeDefinition.replaceFirstChar { it.lowercase() }}: $typeDefinition): $className()"
        }
    }

    companion object : ParserNodeDeclaration<CandidTypeCustom> by reflective()
}