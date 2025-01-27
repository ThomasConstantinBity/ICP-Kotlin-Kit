package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.dsl.subtype

internal sealed class CandidType {

    abstract val typeId: String?
    abstract val variableName: String?
    abstract val optionalType: OptionalType
    abstract val kotlinType: String

    // TODO, make abstract
    open val isTypeAlias: Boolean = false

    // TODO, make abstract
    open fun isKotlinTypealiasDefinition(): Boolean = variableName == null

    // TODO, make abstract
    open fun getKotlinDefinition(): String =
        TODO("Not implemented for $this")

    // TODO, make abstract
    open fun getClassDefinitionForSealedClass(parentClassname: String): String =
        TODO("Not implemented for $this")

    fun getTypealiasDefinition(): String {
        require(isKotlinTypealiasDefinition())
        requireNotNull(typeId)
        return "typealias $typeId = ${getKotlinVariableType()}"
    }

    fun getKotlinVariableType(): String =
        when(optionalType) {
            OptionalType.None -> kotlinType
            OptionalType.Optional -> "$kotlinType?"
            OptionalType.DoubleOptional -> "List<List<$kotlinType?>>"
        }

    companion object : ParserNodeDeclaration<CandidType> by subtype()
}