package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeRecord(
    override val typeId: String? = null,
    override val variableName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val candidTypes: List<CandidType>
): CandidType() {

    override val isTypeAlias: Boolean = false
    override val shouldDeclareInnerClass: Boolean = true

    override fun getClassNameForInnerClassDefinition(baseName: String?): String =
        getKotlinType(baseName)

    override fun getKotlinType(variableName: String?): String =
        when {
            typeId != null -> typeId
            variableName == null -> TODO()
            else -> {
                val kotlinType = variableName
                    .split("_")
                    .joinToString { split -> split.replaceFirstChar { it.uppercase() } }
                when(kotlinType) {
                    "Map",
                    "Array" -> "${kotlinType}Class"

                    else -> kotlinType
                }
            }
        }

    override fun getClassDefinition(): String {
        val className = typeId ?: name
        return getClassDefinition(className)
    }

    override fun getInnerClassDefinition(className: String): String =
        getClassDefinition(className)

    private fun getClassDefinition(className: String): String {
        val kotlinClassDefinition = StringBuilder("class $className(")
        val variables = candidTypes
            .joinToString(
                prefix = "\n",
                separator = ",\n",
            ) {
                val variableName = it.variableName
                val variableType = it.getKotlinVariableType()
                "val $variableName: $variableType"
            }
        kotlinClassDefinition.appendLine(variables)

        // Declare inner classes
        val innerClassesToDeclare = candidTypes.filter { it.shouldDeclareInnerClass }
        if (innerClassesToDeclare.isNotEmpty()) {
            kotlinClassDefinition.appendLine(") {")
            val kotlinInnerClasses = innerClassesToDeclare.joinToString(
                separator = "\n\t",
                prefix = "\t"
            ) {
                val innerClassName = it.variableName!!.replaceFirstChar { char -> char.uppercase() }
                it.getInnerClassDefinition(innerClassName)
            }
            kotlinClassDefinition.appendLine(kotlinInnerClasses)
            kotlinClassDefinition.appendLine("}")
        } else {
            kotlinClassDefinition.appendLine(")")
        }

        return kotlinClassDefinition.toString()
    }

    /*
    override fun getKotlinVariableType(): String {
        TODO()
        /*when {
            typeName == null -> {
                val unnamedClassName = UnnamedClassHelper.getUnnamedClassName()
                typeName = unnamedClassName
                return unnamedClassName
            }

            else -> TODO()
        }*/
    }
     */

    /*override fun shouldDeclareInnerClass(): Boolean = true

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
    }*/

    companion object : ParserNodeDeclaration<CandidTypeRecord> by reflective()
}