package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeBool(
    override val typeId: String,
    override val variableName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override val kotlinType: String = "Boolean"

    companion object : ParserNodeDeclaration<CandidTypeBool> by reflective()
}