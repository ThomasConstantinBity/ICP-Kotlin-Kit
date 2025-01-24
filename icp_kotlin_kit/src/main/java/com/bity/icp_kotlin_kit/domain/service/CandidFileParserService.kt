package com.bity.icp_kotlin_kit.domain.service

interface CandidFileParserService {
    fun parseCandidFile(candidContent: String): String
}