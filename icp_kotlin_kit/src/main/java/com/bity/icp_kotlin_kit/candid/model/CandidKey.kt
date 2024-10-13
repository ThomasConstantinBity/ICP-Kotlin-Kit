package com.bity.icp_kotlin_kit.candid.model

data class CandidKey(
    val longValue: Long,
    val stringValue: String? = null
) : Comparable<CandidKey> {

    constructor(string: String): this(
        longValue = candidHash(string).toLong(),
        stringValue = string
    )

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: CandidKey): Int {
        return when {
            this == other -> 0
            longValue > other.longValue -> 1
            else -> -1
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CandidKey

        return longValue == other.longValue
    }

    override fun hashCode(): Int {
        return longValue.hashCode()
    }

    companion object {
        // https://github.com/dfinity/candid/blob/master/spec/Candid.md
        // hash(id) = ( Sum(i=0..k) utf8(id)[i] * 223^(k-i) ) mod 2^32 where k = |utf8(id)|-1
        fun candidHash(key: String): ULong {
            val data = key.toByteArray(Charsets.UTF_8)
            return data.fold(0UL) { acc, byte ->
                (acc * 223.toULong() + byte.toUByte()) and 0x00000000ffffffff.toULong()
            }
        }
    }
}