package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.candid_file.CandidTypeDefinition

internal interface CandidTypeParserService {
    fun isCandidTypeDefinition(content: String): Boolean
    fun parseCandidType(candidType: String): CandidTypeDefinition
}