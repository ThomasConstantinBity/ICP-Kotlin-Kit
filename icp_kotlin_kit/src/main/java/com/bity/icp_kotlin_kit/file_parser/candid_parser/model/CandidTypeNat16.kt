package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeNat16(
    override val typeId: String? = null,
    override val variableName: String = "nat16Value",
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override fun getKotlinType(variableName: String?): String = "UShort"

    companion object : ParserNodeDeclaration<CandidTypeNat16> by reflective()
}