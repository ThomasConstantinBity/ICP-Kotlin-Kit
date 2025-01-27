package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeVec(
    override val typeId: String? = null,
    override val variableName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val vecType: CandidType,
): CandidType() {

    override fun isKotlinTypealiasDefinition(): Boolean = false
    override val kotlinType: String
        get() {
            val arrayDefinition = if(typeId == "Array") "kotlin.Array" else "Array"
            return "$arrayDefinition<${vecType.getKotlinVariableType()}>"
        }

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val arrayValue: $kotlinType"
        return "class $typeId($variableDefinition): $parentClassname()"
    }

    companion object : ParserNodeDeclaration<CandidTypeVec> by reflective()
}