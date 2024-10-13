package com.bity.icp_kotlin_kit.candid.model

data class CandidServiceSignature(
    val methods: List<CandidServiceSignatureMethod>
) {
    fun isSubType(other: CandidServiceSignature): Boolean =
        methods.all { it.isSubType(other.methods) }
                && other.methods.firstOrNull { otherMethod ->
                    methods.find { it.name == otherMethod.name } != null
                } == null
}