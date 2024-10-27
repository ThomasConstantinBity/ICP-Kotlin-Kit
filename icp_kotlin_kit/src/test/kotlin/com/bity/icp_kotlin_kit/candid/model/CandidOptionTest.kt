package com.bity.icp_kotlin_kit.candid.model

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidOption
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CandidOptionTest {
    @ParameterizedTest
    @MethodSource("equals")
    internal fun test(actual: CandidOption, expected: CandidOption) {
        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun equals() = listOf(
            Arguments.of(
                CandidOption.None(
                    type = CandidType.Null
                ),
                CandidOption.None(
                    type = CandidType.Null
                ),
            ),
        )
    }
}