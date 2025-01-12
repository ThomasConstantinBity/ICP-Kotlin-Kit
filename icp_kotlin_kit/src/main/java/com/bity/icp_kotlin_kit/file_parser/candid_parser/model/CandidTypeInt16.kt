package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeInt16(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override fun getKotlinVariableType(): String = "Short"

    companion object : ParserNodeDeclaration<CandidTypeInt16> by reflective()
}