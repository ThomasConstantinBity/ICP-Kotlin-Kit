package com.bity.icp_kotlin_kit.domain.model.token_transaction

import com.bity.icp_kotlin_kit.domain.model.ICPAccount

sealed class ICPTokenTransactionDestination(
    val address: String
) {
    data class AccountId(val accountId: String):
            ICPTokenTransactionDestination(accountId)
    data class Account(val icpAccount: ICPAccount):
            ICPTokenTransactionDestination(icpAccount.textualRepresentation())
}