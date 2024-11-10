package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_service

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLService
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class IDLServiceDeclaration(
    val initArgsDeclaration: String? = null,
    val services: List<IDLService> = emptyList()
) {
    companion object : ParserNodeDeclaration<IDLServiceDeclaration> by reflective()
}