package com.bity.icp_kotlin_kit.domain.usecase.token

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPToken
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigInteger

class GetTokenBalanceUseCaseTest {

    private lateinit var tokenRepository: TokenRepository
    private lateinit var getTokenBalancesUseCase: GetTokenBalanceUseCase

    private val mockPrincipal = mockk<ICPPrincipal>(relaxed = true)
    private val token = ICPToken(
        standard = ICPTokenStandard.ICRC1,
        canister = mockPrincipal,
        name = "",
        decimals = 8,
        symbol = "",
        description = "",
        totalSupply = BigInteger.ONE,
        verified = true,
        logoUrl = null,
        websiteUrl = null
    )

    @BeforeEach
    fun setUp() {
        tokenRepository = mockk()
        getTokenBalancesUseCase = GetTokenBalanceUseCase(
            tokenRepository = tokenRepository
        )
    }

    @Test
    fun `test getting token balances successfully`() = runBlocking {
        // given
        val balance1 = BigInteger("100")
        val balance2 = BigInteger("200")

        coEvery { tokenRepository.getAllTokens() } returns listOf(token, token)
        coEvery { tokenRepository.getTokenBalance(ICPTokenStandard.ICRC1, mockPrincipal, mockPrincipal) } returnsMany listOf(balance1, balance2)

        // when
        val result = getTokenBalancesUseCase(mockPrincipal)

        // then
        assertEquals(2, result.size)
        assertEquals(balance1, result[0].balance)
        assertEquals(balance2, result[1].balance)
    }
}