package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class IDLTypeNat(
    comment: IDLComment? = null,
    id: String? = null,
    isOptional: Boolean = false,
) : IDLType(
    comment = comment,
    id = id,
    isOptional = isOptional
) {
    override fun typeVariable(className: String?): String = "BigInteger"

    companion object : ParserNodeDeclaration<IDLTypeNat> by reflective()
}
