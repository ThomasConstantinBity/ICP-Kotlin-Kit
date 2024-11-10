package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class IDLTypeService(
    comment: IDLComment? = null,
    id: String?
) : IDLType(
    comment = comment,
    id = id,
    isOptional = false
) {
    companion object : ParserNodeDeclaration<IDLTypeService> by reflective()
}