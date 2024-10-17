package com.bity.icp_kotlin_kit.data.model.candid.deserializer

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidFunction
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidKeyedType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidPrimitiveType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidPrincipal
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidRecord
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidService
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVariant
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVector
import com.bity.icp_kotlin_kit.data.model.candid.model.ServiceMethod
import com.bity.icp_kotlin_kit.data.model.candid.serializer.CandidSerializer
import com.bity.icp_kotlin_kit.cryptography.LEB128
import com.bity.icp_kotlin_kit.data.model.CandidDeserializationError
import com.bity.icp_kotlin_kit.util.ext_function.readFrom
import com.bity.icp_kotlin_kit.util.ext_function.readNextBytes
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.lang.UnsupportedOperationException

internal object CandidDeserializer {
    @Throws(
        CandidDeserializationError.InvalidPrefix::class,
        CandidDeserializationError.InvalidUTF8String::class,
        CandidDeserializationError.InvalidTypeReference::class,
        CandidDeserializationError.UnSerializedBytesLeft::class,
    )
    fun decode(data: ByteArray): List<CandidValue> {
        require(
            data
                .take(CandidSerializer.magicBytes.size).toByteArray()
                .contentEquals(CandidSerializer.magicBytes)
        ) {
            throw CandidDeserializationError.InvalidPrefix()
        }
        val unwrappedData = data.drop(CandidSerializer.magicBytes.size).toByteArray()
        val stream = ByteArrayInputStream(unwrappedData)
        val typeTable = CandidDecodableTypeTable(stream)
        val nCandidValues: Int = LEB128.decodeUnsigned(stream)
        val decodedTypes = (0 until nCandidValues).map {
            val typeRef: Int = LEB128.decodeSigned(stream)
            typeRef
        }
        val decodedValues = decodedTypes.map { candidType ->
            val decodedValue = decodeValue(candidType, stream, typeTable)
            decodedValue
        }

        require(stream.available() == 0) {
            throw CandidDeserializationError.UnSerializedBytesLeft()
        }
        stream.close()
        return decodedValues
    }

    @Throws(
        CandidDeserializationError.InvalidUTF8String::class,
        CandidDeserializationError.InvalidTypeReference::class
    )
    private fun decodeValue(
        typeRef: Int,
        stream: ByteArrayInputStream,
        table: CandidDecodableTypeTable
    ): CandidValue {
        val primitiveType = CandidPrimitiveType.candidPrimitiveTypeByValue(typeRef)
        return if(primitiveType != null) {
            decodePrimitiveValue(primitiveType, stream)
        } else {
            decodeTypeTableValue(typeRef, stream, table)
        }
    }

    private fun decodePrimitiveValue(
        primitiveType: CandidPrimitiveType,
        stream: ByteArrayInputStream
    ): CandidValue {
        return when (primitiveType) {
            CandidPrimitiveType.NULL -> CandidValue.Null
            CandidPrimitiveType.BOOL -> CandidValue.Bool(stream.read() != 0)
            CandidPrimitiveType.NATURAL -> CandidValue.Natural(LEB128.decodeUnsigned(stream))
            CandidPrimitiveType.INTEGER -> CandidValue.Integer(LEB128.decodeSigned(stream))
            CandidPrimitiveType.NATURAL8 -> CandidValue.Natural8(UByte.readFrom(stream))
            CandidPrimitiveType.NATURAL16 -> CandidValue.Natural16(UShort.readFrom(stream))
            CandidPrimitiveType.NATURAL32 -> CandidValue.Natural32(UInt.readFrom(stream))
            CandidPrimitiveType.NATURAL64 -> CandidValue.Natural64(ULong.readFrom(stream))
            CandidPrimitiveType.INTEGER8 -> CandidValue.Integer8(Byte.readFrom(stream))
            CandidPrimitiveType.INTEGER16 -> CandidValue.Integer16(Short.readFrom(stream))
            CandidPrimitiveType.INTEGER32 -> CandidValue.Integer32(Int.readFrom(stream))
            CandidPrimitiveType.INTEGER64 -> CandidValue.Integer64(Long.readFrom(stream))
            CandidPrimitiveType.FLOAT32 -> CandidValue.Float32(Float.readFrom(stream))
            CandidPrimitiveType.FLOAT64 -> CandidValue.Float64(Double.readFrom(stream))

            CandidPrimitiveType.TEXT -> {
                val string = readStringFromInputStream(stream)
                CandidValue.Text(string)
            }

            CandidPrimitiveType.RESERVED -> CandidValue.Reserved
            CandidPrimitiveType.EMPTY -> CandidValue.Empty
            CandidPrimitiveType.PRINCIPAL -> {
                val isPresent = stream.read() == 1
                if(isPresent) {
                    val nBytes: Int = LEB128.decodeUnsigned(stream)
                    val bytes = ByteArray(nBytes) { stream.read().toByte() }
                    CandidValue.Principal(
                        candidPrincipal = CandidPrincipal(bytes)
                    )
                } else {
                    CandidValue.Principal(candidPrincipal = null)
                }
            }

            else -> throw CandidDeserializationError.InvalidPrimitive()
        }
    }

    private fun decodeTypeTableValue(
        typeRef: Int,
        stream: ByteArrayInputStream,
        table: CandidDecodableTypeTable
    ): CandidValue {
        return when(val type = table.tableData[typeRef]) {

            is CandidTypeTableData.Option -> {
                val isPresent = stream.read() == 1
                if(isPresent) {
                    val value = decodeValue(type.containedType, stream, table)
                    CandidValue.Option(value)
                } else CandidValue.Option(table.getTypeForReference(type.containedType))
            }

            is CandidTypeTableData.Vector -> {
                val nItems: UInt = LEB128.decodeUnsigned(stream)
                val items = (0 until nItems.toInt()).map {
                    decodeValue(type.containedType, stream, table)
                }
                // special handling of vector(nat8). We convert them to blob
                if(type.containedType == CandidPrimitiveType.NATURAL8.value) {
                    CandidValue.Blob(
                        items.mapNotNull { it.natural8Value }
                            .map { it.toByte() }
                            .toByteArray()
                    )
                } else if(items.isEmpty())
                    CandidValue.Vector(table.getTypeForReference(type.containedType))
                else CandidValue.Vector(CandidVector(items))
            }

            is CandidTypeTableData.Record -> {
                val dictionary = hashMapOf<Long, CandidValue>()
                type.rows.forEach {
                    dictionary[it.hashedKey] = decodeValue(it.type, stream, table)
                }
                CandidValue.Record(CandidRecord(dictionary))
            }

            is CandidTypeTableData.Variant -> {
                val valueIndex: Int = LEB128.decodeUnsigned(stream)
                val valueType = type.rows[valueIndex].type
                CandidValue.Variant(
                    variant = CandidVariant(
                        candidTypesList = type.rows.map {
                            CandidKeyedType(
                                key = it.hashedKey,
                                type = table.getTypeForReference(it.type)
                            )
                        },
                        value = decodeValue(valueType, stream, table),
                        valueIndex = valueIndex.toULong()
                    )
                )
            }

            is CandidTypeTableData.Function -> {
                val isPresent = stream.read() == 1
                val serviceMethod = if(isPresent) {
                    require(stream.read() == 1) {
                        throw CandidDeserializationError.InvalidTypeReference()
                    }
                    val principalIdLength: Int = LEB128.decodeUnsigned(stream)
                    val principalId = stream.readNextBytes(principalIdLength)
                    val name = readStringFromInputStream(stream)
                    ServiceMethod(
                        name = name,
                        principal = CandidPrincipal(principalId)
                    )
                } else null
                val functionSignature = table.getTypeForReference(typeRef).functionSignature
                    ?: throw RuntimeException("serviceSignature must be not null")
                CandidValue.Function(
                    function = CandidFunction(
                        signature = functionSignature,
                        method = serviceMethod
                    )
                )
            }

            is CandidTypeTableData.Service -> {
                val isPresent = stream.read() == 1
                val principal = if(isPresent) {
                    val principalLength: Int = LEB128.decodeUnsigned(stream)
                    CandidPrincipal(stream.readNextBytes(principalLength))
                } else null
                val serviceSignature = table.getTypeForReference(typeRef).serviceSignature
                    ?: throw RuntimeException("serviceSignature must be not null")
                CandidValue.Service(
                    CandidService(
                        principal = principal,
                        signature = serviceSignature
                    )
                )
            }
        }
    }

    @Throws(CandidDeserializationError.InvalidUTF8String::class)
    fun readStringFromInputStream(inputStream: InputStream): String {
        val length: Int = LEB128.decodeUnsigned(inputStream)
        val data = ByteArray(length)
        inputStream.read(data, 0, length)
        return try {
            String(data, Charsets.UTF_8)
        } catch (_: UnsupportedOperationException) {
            throw CandidDeserializationError.InvalidUTF8String()
        }
    }
}