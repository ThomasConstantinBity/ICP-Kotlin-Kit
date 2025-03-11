package com.bity.icp_kotlin_kit.domain.model.candid_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeNat64(
    override val variableName: String = "nat64Value",
    override val typeId: String = variableName,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinType(variableName: String?): String = "ULong"

    companion object : ParserNodeDeclaration<CandidTypeNat64> by reflective()
}