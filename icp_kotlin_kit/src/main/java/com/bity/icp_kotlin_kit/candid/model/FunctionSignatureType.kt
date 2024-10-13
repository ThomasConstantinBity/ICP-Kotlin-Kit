package com.bity.icp_kotlin_kit.candid.model

sealed class FunctionSignatureType {

    class Concrete(
        val candidFunctionSignature: CandidFunctionSignature
    ): FunctionSignatureType()

    class Reference(val string: String): FunctionSignatureType()
}