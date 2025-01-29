package com.bity.icp_kotlin_kit.domain.exception

sealed class ICPException(message: String? = null): Throwable(message)