package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeVariant(
    override val typeId: String? = null,
    override val variableName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val candidTypes: List<CandidType>,
): CandidType() {

    override val isTypeAlias: Boolean = false

    override fun getKotlinType(variableName: String?): String = typeId ?: TODO()

    override fun getClassDefinition(): String {
        requireNotNull(typeId)
        val sealedClassDefinition = StringBuilder("sealed class $typeId {")
        val classesDefinition = candidTypes.joinToString(
            prefix = "\n\t",
            separator = "\n\t",
            postfix = "\n"
        ) {
            it.getClassDefinitionForSealedClass(typeId)
        }
        sealedClassDefinition.appendLine(classesDefinition)
        val innerClasses = getInnerClassDefinition()
        innerClasses?.let {
            sealedClassDefinition.appendLine(it)
        }
        sealedClassDefinition.appendLine("}")
        return sealedClassDefinition.toString()
    }

    private fun getInnerClassDefinition(): String? {
        val innerTypesToDeclare = candidTypes.filter { it.shouldDeclareInnerClass }
        return if(innerTypesToDeclare.isEmpty()) null
        else innerTypesToDeclare.joinToString {
            it.getInnerClassDefinition(it.getClassNameForInnerClassDefinition())
        }
    }

    companion object : ParserNodeDeclaration<CandidTypeVariant> by reflective()
}