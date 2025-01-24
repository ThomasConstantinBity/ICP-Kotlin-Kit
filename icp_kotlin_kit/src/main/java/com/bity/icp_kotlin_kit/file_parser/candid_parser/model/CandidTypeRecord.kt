package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import com.bity.icp_kotlin_kit.file_parser.file_generator.helper.UnnamedClassHelper
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeRecord(
    override val typeId: String,
    override var typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val candidTypes: List<CandidType>
): CandidType() {

    override fun shouldDeclareInnerClass(): Boolean = true

    override fun getKotlinClassName(candidTypeDefinitionId: String?): String =
        if(typeId != null) TODO() else "${candidTypeDefinitionId}Value"

    override fun getKotlinDefinition(candidTypeDefinitionId: String): String {
        val dataClassDefinition = StringBuilder("data class $candidTypeDefinitionId(")
        val classVariables = candidTypes.joinToString(
            separator = ",\n\t",
            prefix = "\n\t"
        ) {
            val variableName = it.getVariableName()
            val variableType = it.getKotlinVariableTypeWithOptionalDefinition()
            "val $variableName: $variableType"
        }
        dataClassDefinition.appendLine(classVariables)

        val innerClassesToDeclare = candidTypes.filter { it.shouldDeclareInnerClass() }
        if(innerClassesToDeclare.isNotEmpty()) {
            dataClassDefinition.appendLine(") {")
            val innerClassesDefinition = innerClassesToDeclare.joinToString(
                prefix = "\n\t\t",
                separator = "\n\t\t"
            ) { it.getKotlinDefinition(it.getKotlinClassName()) }
            dataClassDefinition.appendLine(innerClassesDefinition)
            dataClassDefinition.appendLine("}")
        } else {
            dataClassDefinition.appendLine(")")
        }
        return dataClassDefinition.toString()
    }

    override fun getInnerClassesToDeclare(): List<CandidType> {
        return listOf(this)
    }

    override fun getKotlinVariableType(): String {

        when {
            typeName == null -> {
                val unnamedClassName = UnnamedClassHelper.getUnnamedClassName()
                typeName = unnamedClassName
                return unnamedClassName
            }

            else -> TODO()
        }
    }

    companion object : ParserNodeDeclaration<CandidTypeRecord> by reflective()
}