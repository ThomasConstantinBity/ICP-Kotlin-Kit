package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.dsl.subtype

internal sealed class CandidType {

    abstract val typeId: String?
    abstract val typeName: String?
    abstract val optionalType: OptionalType

    /**
     * Called from CandidTypeDefinition
     * type <candidTYpeDefinitionId> = <candidTypeDefinition>
     */
    // TODO: make it abstract
    open fun getKotlinDefinition(candidTypeDefinitionId: String): String =
        "typealias $candidTypeDefinitionId = ${getKotlinVariableTypeWithOptionalDefinition()}"

    // TODO: make it abstract
    open fun getKotlinDefinitionForSealedClass(className: String): String =
        TODO("Not implemented for $this")

    // TODO: make it abstract
    open fun shouldDeclareInnerClass(): Boolean = TODO("Not implemented for $this")

    abstract fun getKotlinVariableType(): String

    fun getVariableName(): String =
        typeId ?: throw RuntimeException("Unable to get variable name for $this")

    fun getKotlinVariableTypeWithOptionalDefinition(): String {
        val variableType = getKotlinVariableType()
        return when(optionalType) {
            OptionalType.None -> variableType
            OptionalType.Optional -> "$variableType?"
            OptionalType.DoubleOptional -> "List<List<${variableType}?>>"
        }
    }

    // TODO: throw error if not required for type
    open fun getKotlinClassName(candidTypeDefinitionId: String? = null): String = TODO("Not implemented for $this")




















    open fun getInnerClassesToDeclare(): List<CandidType> = emptyList()

    open fun getTypealiasDefinition(className: String): String {
        TODO("Not implemented for $this")
    }

    fun getKotlinValueDefinition(): String {
        val variableName = typeName ?: getVariableName()
        val variableType = when(optionalType) {
            OptionalType.None -> getKotlinVariableType()
            OptionalType.Optional -> "${getKotlinVariableType()}?"
            OptionalType.DoubleOptional -> "List<List<${getKotlinVariableType()}?>>"
        }
        return "${variableName}: $variableType"
    }

    companion object : ParserNodeDeclaration<CandidType> by subtype()
}