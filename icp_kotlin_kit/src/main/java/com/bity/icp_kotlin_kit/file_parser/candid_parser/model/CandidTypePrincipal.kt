package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypePrincipal(
    override val typeId: String? = null,
    override val isTypeAlias: Boolean = false,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override val variableName: String = typeId ?: "icpPrincipalApiModel"
    override fun getKotlinType(variableName: String?): String = "ICPPrincipalApiModel"

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val : ${getKotlinType()}"
        return "class $typeId($variableDefinition): $parentClassname()"
    }

    companion object : ParserNodeDeclaration<CandidTypePrincipal> by reflective()
}