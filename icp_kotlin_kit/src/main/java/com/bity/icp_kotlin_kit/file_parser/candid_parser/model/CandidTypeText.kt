package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeText(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinVariableType(): String = "String"

    companion object : ParserNodeDeclaration<CandidTypeText> by reflective()
}

internal data class CandidTypePrincipal(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinVariableType(): String = "ICPPrincipalApiModel"

    companion object : ParserNodeDeclaration<CandidTypePrincipal> by reflective()
}