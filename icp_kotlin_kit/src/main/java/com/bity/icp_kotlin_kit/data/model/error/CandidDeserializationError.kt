package com.bity.icp_kotlin_kit.data.model.error

sealed class CandidDeserializationError: Error() {
    class InvalidPrefix: CandidDeserializationError()
    class InvalidPrimitive: CandidDeserializationError()
    class InvalidTypeReference: CandidDeserializationError()
    class InvalidUTF8String: CandidDeserializationError()
    class UnSerializedBytesLeft: CandidDeserializationError()
}