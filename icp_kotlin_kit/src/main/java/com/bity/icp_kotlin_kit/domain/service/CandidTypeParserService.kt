package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidType

internal interface CandidTypeParserService {
    fun isCandidTypeDefinition(content: String): Boolean
    fun parseCandidType(candidType: String): CandidType
}