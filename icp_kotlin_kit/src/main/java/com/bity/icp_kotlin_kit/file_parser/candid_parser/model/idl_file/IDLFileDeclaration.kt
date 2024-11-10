package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLFun
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLType
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

// TODO, add support for service constructor
internal data class IDLFileDeclaration(
    val comment: IDLComment? = null,
    val types: List<IDLType> = mutableListOf(),
    val serviceConstructors: List<IDLType> = emptyList(),
    val services: List<IDLFun> = mutableListOf()
) {
    companion object : ParserNodeDeclaration<IDLFileDeclaration> by reflective()
}