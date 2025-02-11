package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidType

internal interface CandidTypeParserService {
    fun parseCandidType(candidType: String): CandidType
}