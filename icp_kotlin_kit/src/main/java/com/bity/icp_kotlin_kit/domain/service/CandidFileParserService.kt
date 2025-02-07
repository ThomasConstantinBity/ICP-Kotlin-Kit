package com.bity.icp_kotlin_kit.domain.service

import com.bity.icp_kotlin_kit.domain.model.candid_file.CandidParsedFile

internal interface CandidFileParserService {
    fun parseCandidFile(candidContent: String): CandidParsedFile
}