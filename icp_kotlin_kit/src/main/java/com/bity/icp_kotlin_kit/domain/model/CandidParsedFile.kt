package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidType

internal class CandidParsedFile(
    val candidTypes: List<CandidType>
) {

    private val typealiases = candidTypes.filter { it.isTypealiasDefinition() }
    private val types = candidTypes.filter { !it.isTypealiasDefinition() }

}