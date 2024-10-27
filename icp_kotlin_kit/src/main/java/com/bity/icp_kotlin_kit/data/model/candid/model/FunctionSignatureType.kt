package com.bity.icp_kotlin_kit.data.model.candid.model

sealed class FunctionSignatureType {

    class Concrete(
        val candidFunctionSignature: CandidFunctionSignature
    ): FunctionSignatureType()

    class Reference(val string: String): FunctionSignatureType()
}