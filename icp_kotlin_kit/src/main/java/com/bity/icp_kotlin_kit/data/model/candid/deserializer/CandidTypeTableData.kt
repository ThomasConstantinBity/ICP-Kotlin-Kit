package com.bity.icp_kotlin_kit.data.model.candid.deserializer

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidPrimitiveType
import com.bity.icp_kotlin_kit.data.model.candid.model.KeyedContainerRowData
import com.bity.icp_kotlin_kit.cryptography.LEB128
import com.bity.icp_kotlin_kit.data.model.error.CandidDeserializationError
import java.io.ByteArrayInputStream

internal sealed class CandidTypeTableData {

    data class Vector(
        val containedType: Int
    ): CandidTypeTableData()

    data class Option(
        val containedType: Int
    ): CandidTypeTableData()

    data class Record(
        val rows: List<KeyedContainerRowData>
    ): CandidTypeTableData()

    data class Variant(
        val rows: List<KeyedContainerRowData>
    ): CandidTypeTableData()

    data class Function(
        val inputTypes: List<Int>,
        val outputTypes: List<Int>,
        val annotations: List<ULong>
    ): CandidTypeTableData()

    data class Service(
        val methods: List<ServiceMethod>
    ): CandidTypeTableData() {

        class ServiceMethod(
            val name: String,
            val functionType: Int
        )

    }

    companion object {
        @Throws(CandidDeserializationError.InvalidPrimitive::class)
        fun decode(stream: ByteArrayInputStream): CandidTypeTableData {
            val candidType: Int = LEB128.decodeSigned(stream)
            val primitive = CandidPrimitiveType.candidPrimitiveTypeByValue(candidType)
                ?: throw CandidDeserializationError.InvalidPrimitive()
            return when(primitive) {

                CandidPrimitiveType.VECTOR -> {
                    val containedType: Int = LEB128.decodeSigned(stream)
                    Vector(containedType)
                }

                CandidPrimitiveType.OPTION -> {
                    val containedType: Int = LEB128.decodeSigned(stream)
                    Option(containedType)
                }

                CandidPrimitiveType.VARIANT -> {
                    val rows = decodeRows(stream)
                    return Variant(rows)
                }

                CandidPrimitiveType.RECORD -> {
                    val rows = decodeRows(stream)
                    Record(rows)
                }

                CandidPrimitiveType.FUNCTION -> {
                    val nInputs: Int = LEB128.decodeUnsigned(stream)
                    val inputTypes: List<Int> = (0 until nInputs).map {
                        LEB128.decodeSigned(stream)
                    }
                    val nOutputs: Int = LEB128.decodeUnsigned(stream)
                    val outputTypes: List<Int> = (0 until nOutputs).map {
                        LEB128.decodeSigned(stream)
                    }
                    val nAnnotations: Int = LEB128.decodeUnsigned(stream)
                    val annotations: List<ULong> = (0 until nAnnotations).map {
                        LEB128.decodeUnsigned(stream)
                    }
                    Function(
                        inputTypes,
                        outputTypes,
                        annotations
                    )
                }

                CandidPrimitiveType.SERVICE -> {
                    val nMethods: Int = LEB128.decodeUnsigned(stream)
                    val methods = (0 until nMethods).map {
                        val name = CandidDeserializer.readStringFromInputStream(stream)
                        val functionReference: Int = LEB128.decodeSigned(stream)
                        Service.ServiceMethod(
                            name = name,
                            functionType = functionReference
                        )
                    }
                    Service(methods)
                }

                else -> throw CandidDeserializationError.InvalidPrimitive()
            }
        }

        private fun decodeRows(stream: ByteArrayInputStream): List<KeyedContainerRowData> {
            val nRows: Int = LEB128.decodeUnsigned(stream)
            val rows = (0 until nRows).map {
                KeyedContainerRowData(
                    hashedKey = LEB128.decodeUnsigned(stream),
                    type = LEB128.decodeSigned(stream)
                )
            }
            return rows
        }
    }
}