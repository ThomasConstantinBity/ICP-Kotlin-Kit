package com.bity.icp_kotlin_kit.data.model.candid.serializer

import com.bity.icp_kotlin_kit.cryptography.LEB128
import com.bity.icp_kotlin_kit.util.ext_function.joinedData

internal class CandidTypeData(
    val types: List<EncodableType>
) {
    sealed class EncodableType {
        class Signed(val int: Int): EncodableType()
        class Unsigned(val uInt: ULong): EncodableType()
        class Data(val data: ByteArray): EncodableType()

        fun encode(): ByteArray =
            when(this) {
                is Signed -> LEB128.encodeSigned(int)
                is Unsigned -> LEB128.encodeUnsigned(uInt)
                is Data -> data
            }

        // Required by CandidTypeTable::addOrFind
        override fun equals(other: Any?): Boolean {
            other ?: return false
            return when {
                this is Signed && other is Signed -> int == other.int
                this is Unsigned && other is Unsigned -> uInt == other.uInt
                this is Data && other is Data -> data.contentEquals(other.data)
                else -> false
            }
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    fun encode(): ByteArray =
        types.map { it.encode() }.joinedData()

    // Required by CandidTypeTable::addOrFind
    override fun equals(other: Any?): Boolean {
        if(other !is CandidTypeData) return false
        return types == other.types
    }

    override fun hashCode(): Int {
        return types.hashCode()
    }
}