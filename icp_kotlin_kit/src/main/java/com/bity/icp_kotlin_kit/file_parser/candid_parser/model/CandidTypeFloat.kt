package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeFloat(
    override val typeId: String? = null,
    override val variableName: String = "floatValue",
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinType(variableName: String?): String = "Double"

    companion object : ParserNodeDeclaration<CandidTypeFloat> by reflective()
}