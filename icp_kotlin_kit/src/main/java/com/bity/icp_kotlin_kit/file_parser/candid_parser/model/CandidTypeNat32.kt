package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeNat32(
    override val typeId: String? = null,
    override val variableName: String = "nat32Value",
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override fun getKotlinType(variableName: String?): String = "UInt"

    companion object : ParserNodeDeclaration<CandidTypeNat32> by reflective()
}