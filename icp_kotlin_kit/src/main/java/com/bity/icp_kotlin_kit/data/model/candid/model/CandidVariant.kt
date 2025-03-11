package com.bity.icp_kotlin_kit.data.model.candid.model

import com.bity.icp_kotlin_kit.data.model.error.CandidVariantError

class CandidVariant {

    val value: CandidValue
    val valueIndex: ULong
    val candidTypes: List<CandidKeyedType>
    val key: CandidKey
        get() = candidTypes[valueIndex.toInt()].key

    constructor(
        candidTypesList: List<CandidKeyedType>,
        value: CandidValue,
        valueIndex: ULong
    ) {
        this.value = value
        this.valueIndex = valueIndex
        candidTypes = candidTypesList.sortedBy { it.key }
    }

    constructor(
        candidTypes: Map<String, CandidType>,
        value: Pair<String ,CandidValue>
    ) {
        val sortedTypes = candidTypes.map {
            CandidKeyedType(CandidKey(it.key), it.value)
        }.sortedBy { it.key }
        val index = sortedTypes.indexOfFirst { it.key.stringValue == value.first }
        require(index != -1) {
            throw CandidVariantError.ValueNotPartOfTypes()
        }
        valueIndex = index.toULong()
        this.candidTypes = sortedTypes
        this.value = value.second
    }

    override fun toString(): String = "variant { $value }"
}