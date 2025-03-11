package com.bity.icp_kotlin_kit.domain.model.candid_file

import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidType

internal data class CandidParsedType(
    val candidDefinition: String,
    val candidTypeDefinition: CandidType
)