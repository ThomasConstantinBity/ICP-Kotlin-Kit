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
    override val shouldDeclareInnerClass: Boolean =
        candidTypes.any{ it.shouldDeclareInnerClass }

    override fun getClassNameForInnerClassDefinition(baseName: String?): String =
        getKotlinType(baseName)

    override fun getKotlinType(variableName: String?): String =
        when {
            typeId != null -> typeId
            variableName == null -> TODO("$this")
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
                separator = ",\n\t",
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

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        requireNotNull(typeId)
        val classDefinition = StringBuilder("class $typeId(")
        val variableDefinition = candidTypes.joinToString(
            prefix = "\n",
            separator = "\n\t",
        ) {
            "val ${it.variableName}: ${it.getKotlinVariableType()}"
        }
        classDefinition.appendLine(variableDefinition)
        classDefinition.appendLine("): $parentClassname()")
        return classDefinition.toString()
    }

    companion object : ParserNodeDeclaration<CandidTypeRecord> by reflective()
}