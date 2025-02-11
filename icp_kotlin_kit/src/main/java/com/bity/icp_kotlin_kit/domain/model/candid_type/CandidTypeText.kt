package com.bity.icp_kotlin_kit.domain.model.candid_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeText(
    override val typeId: String? = null,
    override val variableName: String = "textValue",
    override val isTypeAlias: Boolean = false,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinType(variableName: String?): String = "String"

    companion object : ParserNodeDeclaration<CandidTypeText> by reflective()
}