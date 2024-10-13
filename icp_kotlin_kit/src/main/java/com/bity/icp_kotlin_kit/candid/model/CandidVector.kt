package com.bity.icp_kotlin_kit.candid.model

import com.bity.icp_kotlin_kit.data.model.CandidVectorError

data class CandidVector(
    val values: List<CandidValue>,
    val containedType: CandidType
) {

    constructor(containedType: CandidType): this(
        values = emptyList(),
        containedType = containedType
    )

    init {
        values.forEach {
            require(it.candidType.isSuperType(containedType)) {
                throw CandidVectorError.WrongCandidType()
            }
        }
    }

    @Throws(CandidVectorError.NoElementsAndNoType::class)
    constructor(sequence: List<CandidValue>): this(
        values = sequence,
        containedType = sequence.firstOrNull()?.candidType
            ?: throw CandidVectorError.NoElementsAndNoType()
    )
}