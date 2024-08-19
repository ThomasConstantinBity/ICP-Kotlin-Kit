package com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class IDLTypeInt(
    typeId: String?,
    isOptional: Boolean = false
) : IDLType(typeId, isOptional) {
    companion object : ParserNodeDeclaration<IDLTypeInt> by reflective()
}