package com.bity.icp_kotlin_kit.domain.model.candid_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeInt32(
    override val typeId: String? = null,
    override val variableName: String = "int32Value",
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override fun getKotlinType(variableName: String?): String = "Int"

    companion object : ParserNodeDeclaration<CandidTypeInt32> by reflective()
}