package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPMethodApiModel

internal class ICPMethod(
    val canister: ICPPrincipal,
    val methodName: String,
    val args: List<CandidValue>?
)

internal fun ICPMethod.toDataModel(): ICPMethodApiModel =
    ICPMethodApiModel(
        canister = canister.toDataModel(),
        methodName = methodName,
        args = args
    )