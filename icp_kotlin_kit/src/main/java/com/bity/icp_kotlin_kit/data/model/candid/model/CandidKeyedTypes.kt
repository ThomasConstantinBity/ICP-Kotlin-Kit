package com.bity.icp_kotlin_kit.data.model.candid.model

data class CandidKeyedTypes(
    val items: List<CandidKeyedType>
) {
    val size = items.size

    fun isVariantSubType(other: CandidKeyedTypes): Boolean =
        items.all { it.isVariantSubType(other) }

    fun isRecordSuperType(other: CandidKeyedTypes): Boolean =
        items.all { it.isRecordSuperType(other) }

    operator fun get(key: CandidKey): CandidKeyedType? =
        items.firstOrNull { it.key == key }
}