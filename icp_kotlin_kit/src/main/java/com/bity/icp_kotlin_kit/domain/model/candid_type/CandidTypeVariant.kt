package com.bity.icp_kotlin_kit.domain.model.candid_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeVariant(
    override val typeId: String? = null,
    override val variableName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val candidTypes: List<CandidType>,
): CandidType() {

    override val isTypeAlias: Boolean = false
    override fun getKotlinType(variableName: String?): String = typeId ?: this.variableName ?: TODO("$this")

    override fun getClassDefinition(): String {
        requireNotNull(typeId)
        return getSealedClassDefinition(typeId)
    }

    private fun getSealedClassDefinition(sealedClassName: String): String {
        val sealedClassDefinition = StringBuilder("sealed class $sealedClassName {")
        val classesDefinition = candidTypes.joinToString(
            prefix = "\n\t",
            separator = "\n\t",
            postfix = "\n"
        ) {
            it.getClassDefinitionForSealedClass(sealedClassName)
        }
        sealedClassDefinition.appendLine(classesDefinition)
        val innerClasses = getInnerClassDefinition()
        innerClasses?.let {
            sealedClassDefinition.appendLine(it)
        }
        sealedClassDefinition.appendLine("}")
        return sealedClassDefinition.toString()
    }

    override fun getInnerClassDefinition(className: String): String =
        getSealedClassDefinition(className)

    private fun getInnerClassDefinition(): String? {
        val innerTypesToDeclare = candidTypes.filter { it.shouldDeclareInnerClass }
        return if(innerTypesToDeclare.isEmpty()) null
        else innerTypesToDeclare.joinToString {
            it.getInnerClassDefinition(it.getClassNameForInnerClassDefinition())
        }
    }

    companion object : ParserNodeDeclaration<CandidTypeVariant> by reflective()
}