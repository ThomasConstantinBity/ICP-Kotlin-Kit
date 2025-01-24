package com.bity.icp_kotlin_kit.data.model.candid.model

data class CandidKeyedType(
    val key: CandidKey,
    val type: CandidType
) {

    constructor(key: Long, type: CandidType): this(
        key = CandidKey(key),
        type = type
    )

    constructor(value: CandidKeyedValue): this(
        key = value.key,
        type = value.value.candidType
    )

    fun isVariantSubType(other: CandidKeyedTypes): Boolean {
        val otherItem = other[key]
            ?: return false
        return type.isSubType(otherItem.type)
    }

    fun isRecordSuperType(other: CandidKeyedTypes): Boolean {
        val item = other[key]
        return if(item == null)
            //  optional fields can be removed
            type.primitiveType == CandidPrimitiveType.OPTION
        else
            type.isSuperType(item.type)
    }
}