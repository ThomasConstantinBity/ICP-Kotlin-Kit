package com.bity.icp_kotlin_kit.candid.model

import java.math.BigInteger

sealed class CandidValue(
    val candidType: CandidType
) {

    val natural8Value: BigInteger?
        get() = (this as? Natural8)
            ?.let { BigInteger.valueOf(it.uInt8.toLong()) }

    data object Null : CandidValue(
        candidType = CandidType.Null
    )

    data class Bool(val bool: Boolean) : CandidValue(
        candidType = CandidType.Bool
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as Bool

            return bool == other.bool
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + bool.hashCode()
            return result
        }
    }

    data class Natural(val bigUInt: BigInteger) : CandidValue(
        candidType = CandidType.Natural
    )

    data class Integer(val bigInt: BigInteger) : CandidValue(
        candidType = CandidType.Integer
    )

    data class Natural8(val uInt8: UByte) : CandidValue(
        candidType = CandidType.Natural8
    )

    data class Natural16(val uInt16: UShort) : CandidValue(
        candidType = CandidType.Natural16
    )

    data class Natural32(val uInt32: UInt) : CandidValue(
        candidType = CandidType.Natural32
    )

    data class Natural64(val uInt64: ULong) : CandidValue(
        candidType = CandidType.Natural64
    )

    data class Integer8(val int8: Byte) : CandidValue(
        candidType = CandidType.Integer8
    )

    data class Integer16(val int16: Short) : CandidValue(
        candidType = CandidType.Integer16
    )

    data class Integer32(val int32: Int) : CandidValue(
        candidType = CandidType.Integer32
    )

    data class Integer64(val int64: Long) : CandidValue(
        candidType = CandidType.Integer64
    )

    data class Float32(val float: Float) : CandidValue(
        candidType = CandidType.Float32
    )

    data class Float64(val double: Double) : CandidValue(
        candidType = CandidType.Float64
    )

    data class Text(val string: String) : CandidValue(
        candidType = CandidType.Text
    )

    data class Blob(val data: ByteArray) : CandidValue(
        candidType = CandidType.Vector(CandidType.Natural8)
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as Blob

            return data.contentEquals(other.data)
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + data.contentHashCode()
            return result
        }
    }

    data object Reserved : CandidValue(
        candidType = CandidType.Reserved
    )

    data object Empty : CandidValue(
        candidType = CandidType.Empty
    )

    data class Option(val option: CandidOption): CandidValue(
        candidType = CandidType.Option(option.containedType)
    ) {
        constructor(value: CandidValue): this(CandidOption.Some(value))

        constructor(containedType: CandidType): this(CandidOption.None(containedType))
    }

    data class Vector(val vector: CandidVector) : CandidValue(
        candidType = CandidType.Vector(vector.containedType)
    ) {
        constructor(containedType: CandidType): this(CandidVector(containedType))
    }

    data class Record(val record: CandidRecord) : CandidValue(
        candidType = CandidType.Record(record.candidTypes)
    )

    data class Variant(val variant: CandidVariant) : CandidValue(
        candidType = CandidType.Variant(variant.candidTypes)
    )

    data class Function(val function: CandidFunction) : CandidValue(
        candidType = CandidType.Function(
            signature = function.signature
        )
    )

    data class Principal(
        val candidPrincipal: CandidPrincipal?
    ): CandidValue(
        candidType = CandidType.Principal
    ) {

        constructor(string: String): this(
            candidPrincipal = CandidPrincipal(string)
        )

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as Principal

            return candidPrincipal == other.candidPrincipal
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + (candidPrincipal?.hashCode() ?: 0)
            return result
        }
    }

    data class Service(
        val candidService: CandidService
    ): CandidValue(
        candidType = CandidType.Service(candidService.signature)
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as Service

            return candidService == other.candidService
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + candidService.hashCode()
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CandidValue

        return candidType == other.candidType
    }

    override fun hashCode(): Int {
        return candidType.hashCode()
    }
}