package com.bity.icp_kotlin_kit.candid.model

class CandidServiceSignatureMethod(
    val name: String,
    private val functionSignature: FunctionSignatureType
) {
    constructor(
        name: String,
        functionSignature: CandidFunctionSignature
    ): this(
        name = name,
        functionSignature = FunctionSignatureType.Concrete(functionSignature)
    )

    fun isSubType(other: List<CandidServiceSignatureMethod>): Boolean {
        val otherMethod = other.firstOrNull { it.name == name }
            ?: return true
        if (functionSignature !is FunctionSignatureType.Concrete) return false
        if (otherMethod.functionSignature !is FunctionSignatureType.Concrete) return false
        return functionSignature.candidFunctionSignature.isSubType(otherMethod.functionSignature.candidFunctionSignature)
    }
}