package com.bity.icp_kotlin_kit.data.model.candid.serializer

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidOption
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.cryptography.LEB128
import com.bity.icp_kotlin_kit.util.ext_function.joinedData

internal object CandidSerializer {

    @OptIn(ExperimentalStdlibApi::class)
    val magicBytes = "4449444C".hexToByteArray()

    fun encode(value: CandidValue?): ByteArray {
        value?.let {
            return encode(listOf(it))
        }
        return encode(emptyList())
    }

    fun encode(values: List<CandidValue>?): ByteArray {
        val typeTable = CandidTypeTable()
        val encodableValues = values?.map { buildTree(it, typeTable) } ?: listOf()
        return magicBytes +
                typeTable.encode() +
                LEB128.encodeUnsigned(encodableValues.size) +
                encodableValues.map { it.encodeType() }.joinedData() +
                encodableValues.map { it.encodeValue() }.joinedData()
    }

    private fun buildTree(
        value: CandidValue,
        typeTable: CandidTypeTable
    ): CandidEncodableValue =
        when(value) {
            is CandidValue.Null -> CandidEncodableValue.Null
            is CandidValue.Blob -> {
                val typeReference = typeTable.getReference(value.candidType)
                CandidEncodableValue.Blob(typeReference, value.data)
            }
            is CandidValue.Bool -> CandidEncodableValue.Bool(value.bool)
            CandidValue.Empty -> CandidEncodableValue.Empty
            is CandidValue.Float32 -> CandidEncodableValue.Float32(value.float)
            is CandidValue.Float64 -> CandidEncodableValue.Float64(value.double)
            is CandidValue.Function -> {
                val typeReference = typeTable.getReference(value.candidType)
                CandidEncodableValue.Function(
                    typeRef = typeReference,
                    serviceMethod = value.function.method
                )
            }
            is CandidValue.Integer -> CandidEncodableValue.Integer(value.bigInt)
            is CandidValue.Integer16 -> CandidEncodableValue.Integer16(value.int16)
            is CandidValue.Integer32 -> CandidEncodableValue.Integer32(value.int32)
            is CandidValue.Integer64 -> CandidEncodableValue.Integer64(value.int64)
            is CandidValue.Integer8 -> CandidEncodableValue.Integer8(value.int8)
            is CandidValue.Natural -> CandidEncodableValue.Natural(value.bigUInt)
            is CandidValue.Natural16 -> CandidEncodableValue.Natural16(value.uInt16)
            is CandidValue.Natural32 -> CandidEncodableValue.Natural32(value.uInt32)
            is CandidValue.Natural64 -> CandidEncodableValue.Natural64(value.uInt64)
            is CandidValue.Natural8 -> CandidEncodableValue.Natural8(value.uInt8)
            is CandidValue.Option -> {
                val typeReference = typeTable.getReference(value.candidType)
                when(value.option) {
                    is CandidOption.None ->
                        CandidEncodableValue.Option(typeReference, null)
                    is CandidOption.Some ->
                        CandidEncodableValue.Option(
                            typeReference,
                            buildTree(value.option.value!!, typeTable)
                        )
                }
            }
            is CandidValue.Record -> {
                val typeReference = typeTable.getReference(value.candidType)
                CandidEncodableValue.Record(
                    typeRef = typeReference,
                    values = value.record.candidSortedItems.map {
                        CandidEncodableValue.DictionaryEncodableItem(
                            hashedKey = it.key.longValue.toULong(),
                            value = buildTree(it.value, typeTable)
                        )
                    }
                )
            }
            CandidValue.Reserved -> CandidEncodableValue.Reserved
            is CandidValue.Text -> CandidEncodableValue.Text(value.string)
            is CandidValue.Variant -> {
                val typeReference = typeTable.getReference(value.candidType)
                CandidEncodableValue.Variant(
                    typeRef = typeReference,
                    CandidEncodableValue.VariantEncodableItem(
                        value.variant.valueIndex,
                        buildTree(value.variant.value, typeTable)
                    )
                )
            }
            is CandidValue.Vector -> {
                val typeReference = typeTable.getReference(value.candidType)
                CandidEncodableValue.Vector(
                    typeRef = typeReference,
                    candidEncodableValues = value.vector.values.map { buildTree(it, typeTable) }
                )
            }
            is CandidValue.Principal -> {
                val typeReference = typeTable.getReference(value.candidType)
                val principal = value.candidPrincipal
                if(principal != null) {
                    CandidEncodableValue.Principal(
                        typeRef = typeReference,
                        principal.bytes
                    )
                } else {
                    CandidEncodableValue.Principal(
                        typeRef = typeReference,
                        data = null
                    )
                }
            }

            is CandidValue.Service -> {
                val typeReference = typeTable.getReference(value.candidType)
                CandidEncodableValue.Service(
                    typeRef = typeReference,
                    principalId = value.candidService.principal?.bytes
                )
            }
        }
}