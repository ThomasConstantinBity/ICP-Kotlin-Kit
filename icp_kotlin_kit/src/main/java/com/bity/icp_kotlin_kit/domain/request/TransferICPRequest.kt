package com.bity.icp_kotlin_kit.domain.request

import com.bity.icp_kotlin_kit.domain.model.ICPAccount
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import java.math.BigInteger

data class TransferICPRequest(
    val sendingAccount: ICPAccount,
    val receivingAddress: String,
    val amount: BigInteger,
    val signingPrincipal: ICPSigningPrincipal,
    val fee: BigInteger,
    val memo: ULong = 0UL
)