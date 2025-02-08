package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeVec(
    override val typeId: String? = null,
    override val variableName: String = "array",
    override val optionalType: OptionalType = OptionalType.None,
    val vecType: CandidType,
): CandidType() {

    override val isTypeAlias: Boolean = true
    override val shouldDeclareInnerClass: Boolean =
        vecType is CandidTypeRecord

    override fun getClassNameForInnerClassDefinition(baseName: String?): String =
        vecType.getClassNameForInnerClassDefinition(typeId ?: variableName)

    override fun getInnerClassDefinition(className: String): String =
        vecType.getInnerClassDefinition(className)

    override fun getKotlinType(variableName: String?): String {
        return "kotlin.Array<${vecType.getKotlinVariableType(typeId ?: this.variableName)}>"
    }

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val ${variableName.replaceFirstChar { it.lowercase() }}: ${getKotlinType()}"
        val className = variableName.replaceFirstChar { it.uppercase() }
        return "class $className($variableDefinition): $parentClassname()"
    }

    companion object : ParserNodeDeclaration<CandidTypeVec> by reflective()
}