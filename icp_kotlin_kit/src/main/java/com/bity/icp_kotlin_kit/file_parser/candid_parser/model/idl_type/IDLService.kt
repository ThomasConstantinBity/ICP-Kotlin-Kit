package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_service.IDLServiceType
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class IDLService(
    comment: IDLComment? = null,
    id: String,
    val inputParamsDeclaration: String,
    val outputParamsDeclaration: String,
    val serviceType: IDLServiceType? = null
): IDLType(
    comment = comment,
    isOptional = false,
    id = id
) {
    companion object : ParserNodeDeclaration<IDLService> by reflective()
}