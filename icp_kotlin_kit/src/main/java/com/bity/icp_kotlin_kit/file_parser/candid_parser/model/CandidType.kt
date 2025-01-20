package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.dsl.subtype

internal sealed class CandidType {

    abstract val typeId: String?
    abstract val typeName: String?
    abstract val optionalType: OptionalType

    open fun getInnerClassesToDeclare(): List<CandidType> = emptyList()
    abstract fun getKotlinVariableType(): String

    open fun getTypealiasDefinition(className: String): String {
        TODO("Not implemented for $this")
    }

    open fun getVariableName(): String {

        return when {
            typeId != null -> typeId!!.replace("\"", "")
                .replaceFirstChar { it.lowercase() }
            else -> getKotlinVariableType().replaceFirstChar { it.lowercase() }
        }
    }

    open fun getKotlinDefinitionForSealedClass(className: String): String {

        requireNotNull(typeId) {
            throw RuntimeException("Unable to define sealed class for $className for $this")
        }
        return "data class ${typeId!!.replace("\"", "")}(val ${getKotlinValueDefinition()}): $className()"
    }

    fun getKotlinClassDefinition(className: String): String {
        return when(this) {

            is CandidTypeVariant -> getKotlinSealedClassDefinition(className)
            is CandidTypeRecord -> getTypeAliasDefinition(className)
            is CandidTypeCustom -> getTypeAliasDefinition(className)
            is CandidTypeVec -> getTypeAliasDefinition(className)
            is CandidTypePrincipal -> getTypealiasDefinition(className)

            else -> TODO("Not defined for $this")
        }
    }

    fun getKotlinClassName(): String =
        when(this) {
            is CandidTypeBool -> TODO()
            is CandidTypeCustom -> typeDefinition
            is CandidTypeFloat -> TODO()
            is CandidTypeFloat64 -> TODO()
            is CandidTypeInt -> TODO()
            is CandidTypeInt16 -> TODO()
            is CandidTypeInt32 -> TODO()
            is CandidTypeInt64 -> TODO()
            is CandidTypeInt8 -> TODO()
            is CandidTypeNat -> TODO()
            is CandidTypeNat16 -> TODO()
            is CandidTypeNat32 -> TODO()
            is CandidTypeNat64 -> TODO()
            is CandidTypeNat8 -> TODO()
            is CandidTypePrincipal -> TODO()
            is CandidTypeRecord -> TODO()
            is CandidTypeText -> TODO()
            is CandidTypeVariant -> TODO()
            is CandidTypeVec -> TODO()
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

private fun CandidTypeVariant.getKotlinSealedClassDefinition(
    className: String,
): String {
    val sealedClassDefinition = StringBuilder()
    sealedClassDefinition.appendLine("sealed class $className {")
    val innerClassesDefinition = candidTypes
        .joinToString("\n") { "\t${it.getKotlinDefinitionForSealedClass(className)}" }
    sealedClassDefinition.appendLine(innerClassesDefinition)
    sealedClassDefinition.appendLine("}")

    // TODO, need to check for inner classes

    return sealedClassDefinition.toString()
}

private fun CandidTypeCustom.getTypeAliasDefinition(className: String): String {
    val typeAliasDefinition = "typealias $className = ${typeDefinition.replace("\"", "")}"
    return when(optionalType) {
        OptionalType.None -> typeAliasDefinition
        OptionalType.Optional -> "${typeAliasDefinition}?"
        OptionalType.DoubleOptional -> TODO()
    }
}

private fun CandidTypeVec.getTypeAliasDefinition(className: String) : String {
    val arrayDefinition = "typealias $className = Array<${vecType.getKotlinClassName()}>"
    return when(optionalType) {
        OptionalType.None -> arrayDefinition
        OptionalType.Optional -> "${arrayDefinition}?"
        OptionalType.DoubleOptional -> TODO()
    }
}

private fun CandidTypeRecord.getTypeAliasDefinition(
    className: String
): String {

    // Write class with its variables
    val dataClassDefinition = StringBuilder("data class $className(")
    if(candidTypes.isNotEmpty()) dataClassDefinition.appendLine()
    val classVariables = candidTypes.joinToString(separator = ",\n") {
        val variableName = it.getVariableName()
        val variableType = when(it.optionalType) {
            OptionalType.None -> it.getKotlinVariableType()
            OptionalType.Optional -> "${it.getKotlinVariableType()}?"
            OptionalType.DoubleOptional -> "List<List<${it.getKotlinVariableType()}?>>"
        }
        "\tval $variableName: $variableType"
    }
    dataClassDefinition.appendLine(classVariables)
    dataClassDefinition.append(")")

    /**
     * Check if the class contains a variable that defines a new class,
     * such as variant or record
     */
    val innerClassToDeclare = candidTypes
        .map { it.getInnerClassesToDeclare() }
        .flatten()
    if(innerClassToDeclare.isNotEmpty()) {
        dataClassDefinition.appendLine(" {")
        val kotlinInnerClasses = innerClassToDeclare.joinToString("\n") {
            val innerClassName = it.typeName!!
            it.getKotlinClassDefinition(innerClassName)
        }
        kotlinInnerClasses.split("\n").forEach {
            dataClassDefinition.appendLine("\t$it")
        }
        dataClassDefinition.appendLine("}")
    }
    return dataClassDefinition.toString()
}