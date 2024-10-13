package com.bity.icp_kotlin_kit.candid.model

import com.bity.icp_kotlin_kit.data.model.CandidVariantError

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CandidVariant

        if (value != other.value) return false
        if (valueIndex != other.valueIndex) return false
        if (candidTypes != other.candidTypes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + valueIndex.hashCode()
        result = 31 * result + candidTypes.hashCode()
        return result
    }


}