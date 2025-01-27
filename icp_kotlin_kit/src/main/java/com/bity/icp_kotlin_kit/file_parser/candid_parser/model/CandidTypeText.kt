package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeText(
    override val typeId: String? = null,
    override val variableName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun isKotlinTypealiasDefinition(): Boolean = false
    override val kotlinType: String = "String"

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val textValue: ${getKotlinVariableType()}"
        return "class $typeId($variableDefinition): $parentClassname()"
    }

    companion object : ParserNodeDeclaration<CandidTypeText> by reflective()
}