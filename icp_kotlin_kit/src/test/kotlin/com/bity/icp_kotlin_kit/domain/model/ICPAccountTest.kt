package com.bity.icp_kotlin_kit.domain.model

import com.bity.icp_kotlin_kit.domain.model.ICPAccount.Companion.DEFAULT_SUB_ACCOUNT_ID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertTrue

class ICPAccountTest {

    @ParameterizedTest(name = "[{index}] - {0}")
    @MethodSource("icpAccountTextualRepresentation")
    fun `icp account from textual representation`(
        textualRepresentation: String,
        expected: ICPAccount
    ) {
        val actual = ICPAccount.fromTextualRepresentation(textualRepresentation)
        assertEquals(
            expected.principal.string,
            actual.principal.string
        )
        assertTrue(
            expected.subAccountId.contentEquals(actual.subAccountId)
        )
    }

    companion object {

        @JvmStatic
        private fun icpAccountTextualRepresentation() = listOf(
            Arguments.of(
                "mi5lp-tjcms-b77vo-qbfgp-cjzyc-imkew-uowpv-ca7f4-l5fzx-yy6ba-qqe",
                ICPAccount(
                    principal = ICPPrincipal("mi5lp-tjcms-b77vo-qbfgp-cjzyc-imkew-uowpv-ca7f4-l5fzx-yy6ba-qqe"),
                    subAccountId = DEFAULT_SUB_ACCOUNT_ID
                )
            )
        )
    }

}