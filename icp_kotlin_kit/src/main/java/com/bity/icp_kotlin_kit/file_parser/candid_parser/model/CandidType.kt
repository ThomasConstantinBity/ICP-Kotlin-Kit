package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.dsl.subtype

internal sealed class CandidType {

    abstract val typeId: String?

    // TODO: remove null
    abstract val variableName: String?
    abstract val optionalType: OptionalType

    open fun getClassNameForInnerClassDefinition(baseName: String? = null): String = TODO("Not implemented for $this")

    // TODO: can we remove variable name?
    abstract fun  getKotlinType(variableName: String? = null): String

    // TODO, make abstract
    open val isTypeAlias: Boolean = false
    // TODO, make abstract
    open val shouldDeclareInnerClass: Boolean = false

    // TODO, make abstract
    open fun getClassDefinition(): String = TODO("Not implemented for $this")

    open fun getServiceDefinition(serviceName: String? = null): String = TODO("Not implemented for $this")

    // TODO, make abstract
    open fun getInnerClassDefinition(className: String): String = TODO("Not implemented for $this")

    open fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val ${variableName}: ${getKotlinVariableType()}"
        return "class $variableName($variableDefinition): $parentClassname()"
    }
    fun getTypealiasDefinition(): String {
        require(isTypeAlias)
        requireNotNull(typeId)
        val typealiasDefinition = StringBuilder("typealias $typeId = ${getKotlinVariableType()}")
        if(shouldDeclareInnerClass) {
            typealiasDefinition.appendLine(getInnerClassDefinition(typeId ?: variableName ?: TODO()))
        }
        return typealiasDefinition.toString()
    }

    fun getKotlinVariableType(variableName: String? = null): String =
        when(optionalType) {
            OptionalType.None -> getKotlinType(variableName)
            OptionalType.Optional -> "${getKotlinType(variableName)}?"
            OptionalType.DoubleOptional -> "List<List<${getKotlinType(variableName)}?>>"
        }

    companion object : ParserNodeDeclaration<CandidType> by subtype()
}