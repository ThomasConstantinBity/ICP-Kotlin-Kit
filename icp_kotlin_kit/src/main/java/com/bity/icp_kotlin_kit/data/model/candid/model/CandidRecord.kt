package com.bity.icp_kotlin_kit.data.model.candid.model

data class CandidRecord(
    val candidSortedItems: List<CandidKeyedValue>
) {

    val candidTypes = candidSortedItems.map {
        CandidKeyedType(it)
    }

    constructor(hashedDictionary: Map<Long, CandidValue>): this(
        candidSortedItems = hashedDictionary
            .map {
                CandidKeyedValue(
                    key = it.key,
                    value = it.value
                )
            }
            .sortedBy { it.key }
    )

    operator fun get(hashedKey: ULong): CandidValue? =
        candidSortedItems.firstOrNull { it.key.longValue.toULong() == hashedKey }?.value

    operator fun get(key: String): CandidValue? {
        val hashedKey = CandidKey.candidHash(key)
        return get(hashedKey)
    }

    companion object {
        fun init(dictionary: Map<String, CandidValue>): CandidRecord =
            CandidRecord(
                hashedDictionary = dictionary.entries
                    .associate {
                        CandidKey.candidHash(it.key).toLong() to it.value
                    }
            )
    }
}