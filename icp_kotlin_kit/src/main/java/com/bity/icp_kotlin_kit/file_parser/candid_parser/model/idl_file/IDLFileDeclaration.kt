package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file

import com.bity.icp_kotlin_kit.domain.model.candid_file.CandidParsedType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidService

internal data class IDLFileDeclaration(
    val candidParsedTypes: List<CandidParsedType>,
    val service: CandidService?
)

