package com.bity.icp_kotlin_kit.data.datasource.api.model

import com.bity.icp_kotlin_kit.cryptography.ICPCryptography
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal

class ICPPrincipalApiModel(
    val bytes: ByteArray,
    val string: String
) {
    constructor(bytes: ByteArray): this(
        bytes = bytes,
        string = ICPCryptography.encodeCanonicalText(bytes)
    )
}

internal fun ICPPrincipalApiModel.toDomainModel(): ICPPrincipal =
    ICPPrincipal(
        string = string,
        bytes = bytes
    )