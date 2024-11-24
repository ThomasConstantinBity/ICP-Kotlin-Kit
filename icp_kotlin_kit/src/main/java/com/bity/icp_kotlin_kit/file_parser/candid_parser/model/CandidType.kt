package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.dsl.subtype

internal sealed class CandidType {

    abstract val typeId: String?
    abstract val typeName: String?
    abstract val optionalType: OptionalType

    open fun getInnerClassesToDeclare(): List<CandidType> = emptyList()
    abstract fun getKotlinVariableType(): String
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

        val variableName = typeName ?: getVariableName()

        val variableType = when(optionalType) {
            OptionalType.None -> getKotlinVariableType()
            OptionalType.Optional -> "${getKotlinVariableType()}?"
            OptionalType.DoubleOptional -> "List<List<${getKotlinVariableType()}?>>"
        }
        return "data class $typeId(val ${variableName}: $variableType): $className()"
    }

    fun getKotlinClassDefinition(className: String): String {
        return when(this) {

            is CandidTypeVariant -> getKotlinSealedClassDefinition(className)
            is CandidTypeRecord -> getClassDefinition(className)

            else -> TODO()
        }
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

private fun CandidTypeRecord.getClassDefinition(
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
        "\t$variableName : $variableType"
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