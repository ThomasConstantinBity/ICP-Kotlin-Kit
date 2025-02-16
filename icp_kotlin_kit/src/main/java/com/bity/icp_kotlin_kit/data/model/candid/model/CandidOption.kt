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
}