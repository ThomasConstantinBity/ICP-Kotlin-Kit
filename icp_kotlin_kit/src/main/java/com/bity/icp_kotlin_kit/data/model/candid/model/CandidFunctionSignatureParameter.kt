package com.bity.icp_kotlin_kit.data.model.candid.model

data class CandidFunctionSignatureParameter(
    val index: Int,
    val name: String?,
    val type: CandidType
) {
    fun isArgumentsSubType(other: List<CandidFunctionSignatureParameter>): Boolean {
        val otherItem = other.firstOrNull { index == it.index }
        return if(otherItem == null)
            type.primitiveType == CandidPrimitiveType.OPTION
        else type.isSuperType(otherItem.type)
    }
    fun isResultSubType(other: List<CandidFunctionSignatureParameter>): Boolean {
        val otherItem = other.firstOrNull { index == it.index }
            ?: return true
        return type.isSubType(otherItem.type)
    }
}