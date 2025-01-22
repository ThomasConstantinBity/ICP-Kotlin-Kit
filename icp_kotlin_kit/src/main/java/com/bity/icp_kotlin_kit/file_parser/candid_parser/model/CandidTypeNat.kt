package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeNat(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override fun shouldDeclareInnerClass(): Boolean = false

    override fun getKotlinVariableType(): String = "BigInteger"

    companion object : ParserNodeDeclaration<CandidTypeNat> by reflective()
}