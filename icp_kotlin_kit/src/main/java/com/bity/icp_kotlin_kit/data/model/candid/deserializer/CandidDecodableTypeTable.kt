package com.bity.icp_kotlin_kit.data.model.candid.deserializer

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidFunctionSignature
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidKeyedType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidPrimitiveType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidServiceSignature
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidServiceSignatureMethod
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.cryptography.LEB128
import com.bity.icp_kotlin_kit.data.model.error.CandidDeserializationError
import java.io.ByteArrayInputStream

internal class CandidDecodableTypeTable(stream: ByteArrayInputStream) {

    private val types: List<CandidType>
    val tableData: List<CandidTypeTableData>

    init {
        val typeCount: Int = LEB128.decodeUnsigned(stream)
        val typeRange = 0 until typeCount
        tableData = typeRange.map { CandidTypeTableData.decode(stream) }
        types = typeRange.map { buildType(it) }
    }

    @Throws()
    private fun buildType(
        typeRef: Int
    ): CandidType {
        return when(val type = tableData[typeRef]) {

            is CandidTypeTableData.Vector -> {
                val referencedType = candidType(type.containedType)
                CandidType.Vector(referencedType)
            }

            is CandidTypeTableData.Option -> {
                val referenceType = candidType(type.containedType)
                CandidType.Option(referenceType)
            }

            is CandidTypeTableData.Record -> {
                val rowTypes = type.rows.map {
                    val rowType = candidType(it.type)
                    CandidKeyedType(
                        key = it.hashedKey,
                        type = rowType
                    )
                }
                CandidType.Record(rowTypes)
            }

            is CandidTypeTableData.Variant -> {
                val rowTypes = type.rows.map {
                    val rowType = candidType(it.type)
                    CandidKeyedType(
                        key = it.hashedKey,
                        type = rowType
                    )
                }
                CandidType.Variant(rowTypes)
            }

            is CandidTypeTableData.Function -> CandidType.Function(
                signature = CandidFunctionSignature(
                    inputs = type.inputTypes.map { candidType(it) },
                    outputs = type.outputTypes.map { candidType(it) },
                    query = type.annotations.contains(0x01UL),
                    oneWay = type.annotations.contains(0x02UL),
                    compositeQuery = type.annotations.contains(0x03UL)
                )
            )

            is CandidTypeTableData.Service -> {
                val serviceMethods = type.methods.map {
                    val signature = candidType(it.functionType).functionSignature
                        ?: throw CandidDeserializationError.InvalidTypeReference()
                    CandidServiceSignatureMethod(
                        name = it.name,
                        functionSignature = signature
                    )
                }
                CandidType.Service(
                    CandidServiceSignature(serviceMethods)
                )
            }
        }
    }

    @Throws(
        CandidDeserializationError.InvalidTypeReference::class,
        CandidDeserializationError.InvalidPrimitive::class
    )
    private fun candidType(type: Int): CandidType {
        return if(type >= 0) {
            require(tableData.size > type) {
                throw CandidDeserializationError.InvalidTypeReference()
            }
            if(isTypeRecursive(type)) CandidType.Named("$type")
            else buildType(type)
        } else {
            val primitiveContainedType = CandidPrimitiveType.candidPrimitiveTypeByValue(type)
                ?: throw CandidDeserializationError.InvalidPrimitive()
            CandidType.init(primitiveContainedType)
                ?: throw CandidDeserializationError.InvalidPrimitive()
        }
    }

    @Throws(
        CandidDeserializationError.InvalidPrimitive::class,
        CandidDeserializationError.InvalidTypeReference::class
    )
    fun getTypeForReference(reference: Int): CandidType {
        if(reference < 0) {
            val primitive = CandidPrimitiveType.candidPrimitiveTypeByValue(reference)
                ?: throw CandidDeserializationError.InvalidPrimitive()
            return CandidType.init(primitive)
                ?: throw CandidDeserializationError.InvalidPrimitive()
        }
        require(types.size > reference) {
            throw CandidDeserializationError.InvalidTypeReference()
        }
        return if(isTypeRecursive(reference)) CandidType.Named("$reference") else types[reference]
    }

    private fun isTypeRecursive(typeRef: Int, visited: List<Int> = emptyList()): Boolean {
        if(typeRef < 0) return false
        if(visited.contains(typeRef)) return true
        val visitedUpdated = visited + typeRef
        return when(val type = tableData[typeRef]) {
            is CandidTypeTableData.Vector -> return isTypeRecursive(type.containedType, visitedUpdated)
            is CandidTypeTableData.Option -> return isTypeRecursive(type.containedType, visitedUpdated)
            is CandidTypeTableData.Record -> type.rows
                .firstOrNull { isTypeRecursive(it.type, visitedUpdated) } != null
            is CandidTypeTableData.Variant -> type.rows
                .firstOrNull { isTypeRecursive(it.type, visitedUpdated) } != null
            is CandidTypeTableData.Function -> {
                val types = type.inputTypes + type.outputTypes
                types.firstOrNull { isTypeRecursive(it, visitedUpdated) } != null
            }
            is CandidTypeTableData.Service -> type.methods
                .firstOrNull { isTypeRecursive(it.functionType, visitedUpdated) } != null
        }
    }
}