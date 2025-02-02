package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeText(
    override val typeId: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override val variableName: String = typeId ?: "textValue"
    override fun getKotlinType(variableName: String?): String = "String"

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val $variableName: ${getKotlinType()}"
        return "class $typeId($variableDefinition): $parentClassname()"
    }

    companion object : ParserNodeDeclaration<CandidTypeText> by reflective()
}