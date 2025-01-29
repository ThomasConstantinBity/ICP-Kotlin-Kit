package com.bity.icp_kotlin_kit.domain.exception

sealed class ICPAccountException : ICPException() {

    class InvalidTextualRepresentation : ICPAccountException()
    class InvalidChecksum : ICPAccountException()

}