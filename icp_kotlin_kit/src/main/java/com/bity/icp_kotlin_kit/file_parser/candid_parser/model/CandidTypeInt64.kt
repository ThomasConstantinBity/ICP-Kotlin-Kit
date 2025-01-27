package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeInt64(
    override val typeId: String,
    override val variableName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override val kotlinType: String = "Long"
    override fun isKotlinTypealiasDefinition(): Boolean = false

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val int64Value: $kotlinType"
        return "class $typeId($variableDefinition): $parentClassname()"
    }

    companion object : ParserNodeDeclaration<CandidTypeInt64> by reflective()
}