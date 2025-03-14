package com.bity.icp_kotlin_kit.domain.model.candid_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypePrincipal(
    override val typeId: String? = null,
    override val variableName: String = typeId ?: "icpPrincipalApiModel",
    override val isTypeAlias: Boolean = false,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinType(variableName: String?): String = "ICPPrincipalApiModel"

    companion object : ParserNodeDeclaration<CandidTypePrincipal> by reflective()
}