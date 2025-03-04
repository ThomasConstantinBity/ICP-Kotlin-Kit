package com.bity.icp_kotlin_kit.domain.model.candid_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeNat32(
    override val typeId: String? = null,
    override val variableName: String = "nat32Value",
    override val optionalType: OptionalType = OptionalType.None,
    override val isTypeAlias: Boolean = false
) : CandidType() {

    override fun getKotlinType(variableName: String?): String = "UInt"

    companion object : ParserNodeDeclaration<CandidTypeNat32> by reflective()
}