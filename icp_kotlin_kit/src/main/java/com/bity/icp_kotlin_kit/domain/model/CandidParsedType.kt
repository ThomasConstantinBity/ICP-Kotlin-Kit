package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidType

internal data class CandidParsedType(
    val candidDefinition: String,
    val candidTypeDefinition: CandidType
)