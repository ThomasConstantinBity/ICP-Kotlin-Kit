package com.bity.icp_kotlin_kit.candid.model

data class CandidKeyedValue(
    val key: CandidKey,
    val value: CandidValue
) {
    constructor(key: Long, value: CandidValue): this(
        key = CandidKey(key),
        value = value
    )
}