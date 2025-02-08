package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeBlob(
    override val typeId: String? = null,
    override val variableName: String = "blobValue",
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override val isTypeAlias: Boolean = true
    override fun getKotlinType(variableName: String?): String = "ByteArray"

    companion object : ParserNodeDeclaration<CandidTypeBlob> by reflective()

}