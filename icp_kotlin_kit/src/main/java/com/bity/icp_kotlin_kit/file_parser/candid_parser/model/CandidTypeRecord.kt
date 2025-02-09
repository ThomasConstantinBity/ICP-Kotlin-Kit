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
        when {
            candidTypes.any { it is CandidTypeVariant } -> true
            else -> candidTypes.any{ it.shouldDeclareInnerClass }
        }

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
                val variableType = if (it.getKotlinVariableType() == it.variableName)
                    it.variableName!!.split("_")
                        .joinToString("") { string ->
                            string.replaceFirstChar { c ->
                                c.uppercase()
                            }
                        }
                else it.getKotlinVariableType()
                "val $variableName: $variableType"
            }
        kotlinClassDefinition.appendLine(variables)
        val innerClassToDeclare = getInnerClassToDeclare()
        if(innerClassToDeclare != null) {
            kotlinClassDefinition.appendLine(") {")
            kotlinClassDefinition.appendLine(innerClassToDeclare)
        } else {
            kotlinClassDefinition.appendLine(")")
        }
        return kotlinClassDefinition.toString()
    }

    private fun getInnerClassToDeclare(): String? {
        val innerClassesToDeclare = candidTypes
            .filter {
                it.shouldDeclareInnerClass || it is CandidTypeVariant
            }
        return if (innerClassesToDeclare.isNotEmpty()) {
            val innerClassDefinition = StringBuilder()
            val kotlinInnerClasses = innerClassesToDeclare.joinToString(
                separator = "\n\t",
                prefix = "\t"
            ) {
                val innerClassName = it.variableName!!.split("_")
                    .joinToString("") { string ->
                        string.replaceFirstChar { c ->
                            c.uppercase()
                        }
                    }
                it.getInnerClassDefinition(innerClassName)
            }
            innerClassDefinition.appendLine(kotlinInnerClasses)
            innerClassDefinition.appendLine("}")
            innerClassDefinition.toString()
        } else {
            null
        }
    }

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        requireNotNull(typeId)
        val classDefinition = StringBuilder("class $typeId(")
        val variableDefinition = candidTypes.joinToString(
            prefix = "\n",
            separator = ",\n\t",
        ) {
            "val ${it.variableName}: ${it.getKotlinVariableType()}"
        }
        classDefinition.appendLine(variableDefinition)
        classDefinition.appendLine("): $parentClassname()")
        return classDefinition.toString()
    }

    companion object : ParserNodeDeclaration<CandidTypeRecord> by reflective()
}