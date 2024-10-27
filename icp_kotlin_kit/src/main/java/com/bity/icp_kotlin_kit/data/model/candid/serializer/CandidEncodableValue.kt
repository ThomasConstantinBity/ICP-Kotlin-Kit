package com.bity.icp_kotlin_kit.data.model.candid.serializer

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidPrimitiveType
import com.bity.icp_kotlin_kit.data.model.candid.model.ServiceMethod
import com.bity.icp_kotlin_kit.cryptography.LEB128
import com.bity.icp_kotlin_kit.util.ext_function.bytes
import com.bity.icp_kotlin_kit.util.ext_function.joinedData
import java.math.BigInteger

internal sealed class CandidEncodableValue {
    class DictionaryEncodableItem(
        val hashedKey: ULong,
        val value: CandidEncodableValue
    )
    class VariantEncodableItem(
        val valueIndex: ULong,
        val value: CandidEncodableValue
    )

    data object Null: CandidEncodableValue()
    class Bool(val bool: Boolean): CandidEncodableValue()
    class Natural(val bigUInt: BigInteger): CandidEncodableValue()
    class Integer(val bigInt: BigInteger): CandidEncodableValue()
    class Natural8(val uInt8: UByte): CandidEncodableValue()
    class Natural16(val uInt16: UShort): CandidEncodableValue()
    class Natural32(val uInt32: UInt): CandidEncodableValue()
    class Natural64(val uInt64: ULong): CandidEncodableValue()
    class Integer8(val int8: Byte): CandidEncodableValue()
    class Integer16(val int16: Short): CandidEncodableValue()
    class Integer32(val int32: Int): CandidEncodableValue()
    class Integer64(val int64: Long): CandidEncodableValue()
    class Float32(val float: Float): CandidEncodableValue()
    class Float64(val double: Double): CandidEncodableValue()
    data object Reserved: CandidEncodableValue()
    data object Empty: CandidEncodableValue()
    class Text(val string: String): CandidEncodableValue()
    class Option(
        val typeRef: Int,
        val candidEncodableValue: CandidEncodableValue?
    ): CandidEncodableValue()
    class Vector(
        val typeRef: Int,
        val candidEncodableValues: List<CandidEncodableValue>
    ): CandidEncodableValue()
    class Blob(
        val typeRef: Int,
        val data: ByteArray
    ): CandidEncodableValue()
    class Record(
        val typeRef: Int,
        val values: List<DictionaryEncodableItem>
    ): CandidEncodableValue()
    class Variant(
        val typeRef: Int,
        val variantEncodableItem: VariantEncodableItem
    ): CandidEncodableValue()
    class Function(
        val typeRef: Int,
        val serviceMethod: ServiceMethod?
    ): CandidEncodableValue()
    class Service(
        val typeRef: Int,
        val principalId: ByteArray?
    ): CandidEncodableValue()
    class Principal(
        val typeRef: Int,
        val data: ByteArray?
    ): CandidEncodableValue()

    fun encodeType(): ByteArray {
        val encodeSigned: (Number) -> ByteArray = LEB128::encodeSigned
        return when(this) {
            is Null -> encodeSigned(CandidPrimitiveType.NULL.value)
            is Blob -> encodeSigned(typeRef)
            is Bool -> encodeSigned(CandidPrimitiveType.BOOL.value)
            Empty -> encodeSigned(CandidPrimitiveType.EMPTY.value)
            is Float32 -> encodeSigned(CandidPrimitiveType.FLOAT32.value)
            is Float64 -> encodeSigned(CandidPrimitiveType.FLOAT64.value)
            is Function -> encodeSigned(typeRef)
            is Integer -> encodeSigned(CandidPrimitiveType.INTEGER.value)
            is Integer16 -> encodeSigned(CandidPrimitiveType.INTEGER16.value)
            is Integer32 -> encodeSigned(CandidPrimitiveType.INTEGER32.value)
            is Integer64 -> encodeSigned(CandidPrimitiveType.INTEGER64.value)
            is Integer8 -> encodeSigned(CandidPrimitiveType.INTEGER8.value)
            is Natural -> encodeSigned(CandidPrimitiveType.NATURAL.value)
            is Natural16 -> encodeSigned(CandidPrimitiveType.NATURAL16.value)
            is Natural32 -> encodeSigned(CandidPrimitiveType.NATURAL32.value)
            is Natural64 -> encodeSigned(CandidPrimitiveType.NATURAL64.value)
            is Natural8 -> encodeSigned(CandidPrimitiveType.NATURAL8.value)
            is Option -> encodeSigned(typeRef)
            is Record -> encodeSigned(typeRef)
            Reserved -> encodeSigned(CandidPrimitiveType.RESERVED.value)
            is Service -> encodeSigned(typeRef)
            is Text -> encodeSigned(CandidPrimitiveType.TEXT.value)
            is Variant -> encodeSigned(typeRef)
            is Vector -> encodeSigned(typeRef)
            is Principal -> encodeSigned(typeRef)
        }
    }

    fun encodeValue(): ByteArray {
        val encodeUnsigned: (Int) -> ByteArray = LEB128::encodeUnsigned
        val encodeUnsignedULong: (ULong) -> ByteArray = LEB128::encodeUnsigned
        return when(this) {
            is Null -> byteArrayOf()
            is Blob -> encodeUnsigned(data.size) + data
            is Bool -> byteArrayOf(bool.toByte())
            Empty -> byteArrayOf()
            is Float32 -> float.bytes
            is Float64 -> double.bytes
            is Function -> {
                if(serviceMethod == null) {
                    byteArrayOf(0x00)
                } else {
                    val methodName = serviceMethod.name.toByteArray(Charsets.UTF_8)
                    byteArrayOf(0x01) +
                            encodeUnsigned(serviceMethod.principal.bytes.size) +
                            serviceMethod.principal.bytes +
                            byteArrayOf(0x01) +
                            encodeUnsigned(methodName.size) +
                            methodName
                }
            }
            is Integer -> LEB128.encodeSigned(bigInt)
            is Integer8 -> byteArrayOf(int8)
            is Integer16 -> int16.bytes
            is Integer32 -> int32.bytes
            is Integer64 -> int64.bytes
            is Natural -> LEB128.encodeUnsigned(bigUInt)
            is Natural8 -> byteArrayOf(uInt8.toByte())
            is Natural16 -> uInt16.bytes
            is Natural32 -> uInt32.bytes
            is Natural64 -> uInt64.bytes
            is Option ->
                if (candidEncodableValue == null) {
                    byteArrayOf(0x00)
                } else {
                    byteArrayOf(0x01) + candidEncodableValue.encodeValue()
                }
            is Record -> values.map { it.value.encodeValue() }.joinedData()
            Reserved -> byteArrayOf()
            is Text -> {
                val utf8 = string.toByteArray(Charsets.UTF_8)
                encodeUnsigned(utf8.size) + utf8
            }
            is Variant ->
                encodeUnsignedULong(variantEncodableItem.valueIndex) +
                        variantEncodableItem.value.encodeValue()
            is Vector ->
                encodeUnsigned(candidEncodableValues.size) +
                        candidEncodableValues
                            .map { it.encodeValue() }
                            .joinedData()
            is Service ->
                if(principalId == null) {
                    byteArrayOf(0x00)
                } else {
                    byteArrayOf(0x01) + encodeUnsigned(principalId.size) + principalId
                }

            is Principal -> {
                val principal = data ?: return byteArrayOf(0x00)
                byteArrayOf(0x01) + encodeUnsigned(principal.size) + principal
            }
        }
    }

    private fun Boolean.toByte(): Byte =
        if(this) 1.toByte() else 0.toByte()
}