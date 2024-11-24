package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidServiceFunction(
    val comment: IDLComment? = null,
    val functionName: String,
    val inputArgs: List<CandidType> = emptyList(),
    val outputArgs: List<CandidType> = emptyList(),
    val functionType: CandidServiceFunctionType = CandidServiceFunctionType.Query
) {
    companion object : ParserNodeDeclaration<CandidServiceFunction> by reflective()
}