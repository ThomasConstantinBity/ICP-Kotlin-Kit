package com.bity.icp_kotlin_kit.domain.model.candid_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeBool(
    override val typeId: String? = null,
    override val variableName: String = "boolValue",
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override val isTypeAlias: Boolean = true
    override fun getKotlinType(variableName: String?): String = "Boolean"

    companion object : ParserNodeDeclaration<CandidTypeBool> by reflective()
}