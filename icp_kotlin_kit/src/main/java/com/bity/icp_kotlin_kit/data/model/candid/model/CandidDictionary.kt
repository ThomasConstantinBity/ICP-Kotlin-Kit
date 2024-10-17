package com.bity.icp_kotlin_kit.data.model.candid.model

import java.lang.RuntimeException

internal class CandidDictionary(
    hashedDictionary: HashMap<ULong, CandidValue>
): HashMap<ULong, CandidValue>(hashedDictionary) {

    val candidSortedItems: List<CandidDictionaryItem> =
        hashedDictionary
            .map { CandidDictionaryItem(
                hashedKey = it.key,
                value = it.value)
            }
            .sortedBy { it.hashedKey }

    val candidTypes: List<CandidDictionaryItemType> = candidSortedItems
        .map { CandidDictionaryItemType(it) }

    constructor(dictionary: Map<String, CandidValue>) : this(
        hashedDictionary = HashMap(
            dictionary.map { (key, value) ->
                val candidDictionaryItem = CandidDictionaryItem(key, value)
                candidDictionaryItem.hashedKey to candidDictionaryItem.value
            }.toMap()
        )
    )

    fun getNotNull(key: String): CandidValue {
        val hashedKey = hash(key)
        return get(hashedKey) ?: throw RuntimeException("Value cannot be null")
    }

    operator fun get(key: String?): CandidValue? {
        key ?: return null
        val hashedKey = hash(key)
        return get(hashedKey)
    }

    companion object {
        // https://github.com/dfinity/candid/blob/master/spec/Candid.md
        // hash(id) = ( Sum(i=0..k) utf8(id)[i] * 223^(k-i) ) mod 2^32 where k = |utf8(id)|-1
        fun hash(key: String): ULong {
            val data = key.toByteArray(Charsets.UTF_8)
            return data.fold(0UL) { acc, byte ->
                (acc * 223.toULong() + byte.toUByte()) and 0x00000000ffffffff.toULong()
            }
        }
    }
}