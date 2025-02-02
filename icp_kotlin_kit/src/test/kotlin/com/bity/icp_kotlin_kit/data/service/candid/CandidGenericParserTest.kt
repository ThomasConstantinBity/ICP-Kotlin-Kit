package com.bity.icp_kotlin_kit.data.service.candid

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CandidGenericParserTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl()

    @ParameterizedTest(name = "{index}")
    @MethodSource("typealiases")
    fun typealiases(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = candidTypeParserService.parseCandidType(typeDefinition)
        assertTrue(candidTypeDefinition.isTypeAlias)
        val kotlinDefinition = candidTypeDefinition.getTypealiasDefinition()
        assertEquals(
            expected = expectedGeneratedClass
                .replace("""\s+|\t+""".toRegex(), " ")
                .trim(),
            actual = kotlinDefinition
                .replace("""\s+|\t+""".toRegex(), " ")
                .trim(),
            message = """
                Expected:
                $expectedGeneratedClass
                
                Actual:
                $kotlinDefinition
            """.trimIndent()
        )

    }

    companion object {

        @JvmStatic
        private fun typealiases() = listOf(
            Arguments.of(
                "type AskConfigShared = opt AskFeatureArray;",
                "typealias AskConfigShared = AskFeatureArray?"
            ),
            Arguments.of(
                "type AskFeatureArray = vec AskFeature;",
                "typealias AskFeatureArray = Array<AskFeature>"
            ),
            Arguments.of(
                "type Caller = opt principal;",
                "typealias Caller = ICPPrincipalApiModel?"
            ),
            Arguments.of(
                "type AskSubscribeResponse = bool;",
                "typealias AskSubscribeResponse = Boolean"
            ),
            Arguments.of(
                "type BidConfigShared = opt vec BidFeature;",
                "typealias BidConfigShared = Array<BidFeature>?"
            )
        )

    }

}