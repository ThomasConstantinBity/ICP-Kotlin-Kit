package com.bity.icp_kotlin_kit.candid.model

class CandidFunctionSignature(
    val arguments: List<CandidFunctionSignatureParameter>,
    private val results: List<CandidFunctionSignatureParameter>,
    private val annotations: CandidFunctionSignatureAnnotation
) {

    constructor(
        inputs: List<CandidType>,
        outputs: List<CandidType>,
        query: Boolean = false,
        oneWay: Boolean = false,
        compositeQuery: Boolean = false
    ): this (
        arguments = inputs.mapIndexed { index, element ->
            CandidFunctionSignatureParameter(
                index = index,
                name = null,
                type = element
            ) },
        results = outputs.mapIndexed { index, element ->
            CandidFunctionSignatureParameter(
                index = index,
                name = null,
                type = element
            ) },
        annotations = CandidFunctionSignatureAnnotation(
            isQuery = query,
            isOneWay = oneWay,
            isCompositeQuery = compositeQuery
        )
    )

    fun isSubType(other: CandidFunctionSignature): Boolean =
        arguments.all { it.isArgumentsSubType(other.arguments) }
                && results.isResultsSubType(other.results)
                && annotations == other.annotations
}

private fun Iterable<CandidFunctionSignatureParameter>.isResultsSubType(
    other: List<CandidFunctionSignatureParameter>
): Boolean =
    all { it.isResultSubType(other) }
            && other.all { otherItem -> any { it.index == otherItem.index } }
