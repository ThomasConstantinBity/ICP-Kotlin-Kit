package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeNat8(
    override val typeId: String,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun shouldDeclareInnerClass(): Boolean = false

    override fun getKotlinVariableType(): String = "UByte"


    companion object : ParserNodeDeclaration<CandidTypeNat8> by reflective()
}