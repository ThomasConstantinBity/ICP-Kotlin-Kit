package com.bity.icp_kotlin_kit.candid.model

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidDictionary
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class CandidDictionaryTest {
    @ParameterizedTest
    @MethodSource("testVectors")
    fun `Dictionary Key Hash`(
        key: String,
        expectedValue: Long
    ) {
        val hashed = CandidDictionary.hash(key)
        assertEquals(expectedValue.toULong(), hashed)
    }

    companion object {
        @JvmStatic
        fun testVectors(): List<Array<Any>> =
            listOf(
                arrayOf("a", 97),
                arrayOf("b", 98),
                arrayOf("c", 99),
                arrayOf("ab", 21729),
                arrayOf("ba", 21951),
                arrayOf("ac", 21730),
                arrayOf("abcd %§±`~", 1687702841),
                arrayOf("abcd".repeat(1000), 3277195728)
            )
    }
}