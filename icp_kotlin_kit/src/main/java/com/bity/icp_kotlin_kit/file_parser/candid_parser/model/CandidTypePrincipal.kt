package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypePrincipal(
    override val typeId: String,
    override val variableName: String? = null,
    override val isTypeAlias: Boolean = false,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun isKotlinTypealiasDefinition(): Boolean = isTypeAlias
    override val kotlinType: String = "ICPPrincipalApiModel"

    override fun getClassDefinitionForSealedClass(parentClassname: String): String {
        val variableDefinition = "val icpPrincipalApiModel: ICPPrincipalApiModel"
        return "class $typeId($variableDefinition): $parentClassname()"
    }

    companion object : ParserNodeDeclaration<CandidTypePrincipal> by reflective()
}