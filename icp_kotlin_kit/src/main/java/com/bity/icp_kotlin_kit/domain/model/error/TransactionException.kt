package com.bity.icp_kotlin_kit.domain.model.error

sealed class TransactionException(errorMessage: String? = null): Exception(errorMessage) {
    class ICRC1GetAllTransactionsException(message: String): TransactionException(message)
}