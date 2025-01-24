package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeNat32(
    override val typeId: String,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override fun getKotlinVariableType(): String = "UInt"

    companion object : ParserNodeDeclaration<CandidTypeNat32> by reflective()
}