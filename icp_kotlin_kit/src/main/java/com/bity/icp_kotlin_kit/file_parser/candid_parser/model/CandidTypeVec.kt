package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeVec(
    override val typeId: String? = null,
    override val variableName: String = "arrayValue",
    override val optionalType: OptionalType = OptionalType.None,
    val vecType: CandidType,
): CandidType() {

    override val isTypeAlias: Boolean = true
    override val shouldDeclareInnerClass: Boolean =
        vecType is CandidTypeRecord

    override fun getClassNameForInnerClassDefinition(baseName: String?): String =
        vecType.getClassNameForInnerClassDefinition(typeId)

    override fun getInnerClassDefinition(className: String): String =
        vecType.getInnerClassDefinition(className)

    override fun getKotlinType(variableName: String?): String {
        val arrayDefinition = if(typeId == "Array") "kotlin.Array" else "Array"
        return "$arrayDefinition<${vecType.getKotlinVariableType(typeId ?: this.variableName)}>"
    }

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val arrayValue: ${getKotlinType()}"
        return "class $typeId($variableDefinition): $parentClassname()"
    }

    companion object : ParserNodeDeclaration<CandidTypeVec> by reflective()
}