package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeInt8(
    override val typeId: String? = null,
    override val variableName: String = "int8Value",
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override fun getKotlinType(variableName: String?): String = "Byte"

    companion object : ParserNodeDeclaration<CandidTypeInt8> by reflective()
}