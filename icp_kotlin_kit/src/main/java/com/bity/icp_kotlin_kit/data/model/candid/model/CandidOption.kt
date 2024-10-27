package com.bity.icp_kotlin_kit.data.model.candid.model

sealed class CandidOption(
    val value: CandidValue?,
    val containedType: CandidType
) {
    class None(type: CandidType): CandidOption(
        value = null,
        containedType = type
    )
    class Some(wrapped: CandidValue): CandidOption(
        value = wrapped,
        containedType = wrapped.candidType
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CandidOption) return false
        return value == other.value && containedType == other.containedType
    }

    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + containedType.hashCode()
        return result
    }
}