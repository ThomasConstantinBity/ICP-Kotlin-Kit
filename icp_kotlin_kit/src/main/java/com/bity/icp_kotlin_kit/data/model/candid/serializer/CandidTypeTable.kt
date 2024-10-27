package com.bity.icp_kotlin_kit.data.model.candid.serializer

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.cryptography.LEB128
import com.bity.icp_kotlin_kit.util.ext_function.joinedData

internal class CandidTypeTable {
    private var customTypes = mutableListOf<CandidTypeData>()

    fun getReference(type: CandidType): Int =
        when(type) {

            is CandidType.Vector -> {
                val typeData = CandidTypeData(
                    types = listOf(
                        CandidTypeData.EncodableType.Signed(type.primitiveType.value),
                        CandidTypeData.EncodableType.Signed(getReference(type.candidType))
                    )
                )
                addOrFind(typeData)
            }

            is CandidType.Option -> {
                val typeData = CandidTypeData(
                    types = listOf(
                        CandidTypeData.EncodableType.Signed(type.primitiveType.value),
                        CandidTypeData.EncodableType.Signed(getReference(type.candidType))
                    )
                )
                addOrFind(typeData)
            }

            is CandidType.Record -> {
                val typeData = CandidTypeData(
                    types = listOf(
                        CandidTypeData.EncodableType.Signed(type.primitiveType.value),
                        CandidTypeData.EncodableType.Unsigned(type.candidKeyedTypes.size.toULong())
                    ) + type.candidKeyedTypes.items.flatMap {
                        listOf(
                            CandidTypeData.EncodableType.Unsigned(it.key.longValue.toULong()),
                            CandidTypeData.EncodableType.Signed(getReference(it.type))
                        )
                    }
                )
                addOrFind(typeData)
            }

            is CandidType.Variant -> {
                val typeData = CandidTypeData(
                    types = listOf(
                        CandidTypeData.EncodableType.Signed(type.primitiveType.value),
                        CandidTypeData.EncodableType.Unsigned(type.candidKeyedTypes.size.toULong())
                    ) + type.candidKeyedTypes.items.flatMap {
                        listOf(
                            CandidTypeData.EncodableType.Unsigned(it.key.longValue.toULong()),
                            CandidTypeData.EncodableType.Signed(getReference(it.type))
                        )
                    }
                )
                addOrFind(typeData)
            }

            is CandidType.Function -> TODO()
            is CandidType.Service -> TODO()
            is CandidType.Named -> TODO()

            else -> type.primitiveType.value
        }

    fun encode(): ByteArray =
        LEB128.encodeUnsigned(customTypes.size) +
                customTypes.map { it.encode() }.joinedData()

    private fun addOrFind(typeData: CandidTypeData): Int {
        val index = customTypes.indexOfFirst { it == typeData }
        if(index == -1) {
            customTypes.add(typeData)
            return customTypes.size - 1
        }
        return index
    }
}