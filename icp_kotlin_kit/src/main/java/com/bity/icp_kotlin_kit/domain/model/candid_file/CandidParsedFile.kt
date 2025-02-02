package com.bity.icp_kotlin_kit.domain.model.candid_file

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidType

class CandidParsedFile internal constructor(
    private val candidTypes: List<CandidType>
) {

    private val typealiases = candidTypes.filter { it.isTypeAlias }
    private val types = candidTypes.filter { !it.isTypeAlias }

}