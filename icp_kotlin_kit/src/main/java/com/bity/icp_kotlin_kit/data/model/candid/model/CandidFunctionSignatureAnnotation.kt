package com.bity.icp_kotlin_kit.data.model.candid.model

data class CandidFunctionSignatureAnnotation(
    // indicates that the referenced function is a query method,
    // meaning it does not alter the state of its canister, and that
    // it can be invoked using the cheaper “query call” mechanism.
    val isQuery: Boolean,

    // indicates that this function returns no response, intended for fire-and-forget scenarios.
    val isOneWay: Boolean,

    // composite_query is a special query function that has IC-specific features and limitations:
    //  - composite_query function can only call other composite_query and query functions.
    //  - composite_query function can only be called from other composite_query functions (not callable from query or update functions) and from outside of IC. Therefore, query is not a subtype of composite_query.
    //  - composite_query cannot be made cross-subnets.
    //  - All these limitations are temporary due to the implementation. Eventually, query and composite_query functions will become the same thing.
    val isCompositeQuery: Boolean
)