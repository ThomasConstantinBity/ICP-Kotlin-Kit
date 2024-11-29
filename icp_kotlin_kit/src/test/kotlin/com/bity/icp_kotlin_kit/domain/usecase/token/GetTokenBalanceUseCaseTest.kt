package com.bity.icp_kotlin_kit.domain.usecase.token

import com.bity.icp_kotlin_kit.PrincipalTestData
import com.bity.icp_kotlin_kit.TokenTestData
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard.ICP
import com.bity.icp_kotlin_kit.domain.model.enum.ICPTokenStandard.ICRC1
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigInteger

class GetTokenBalanceUseCaseTest : PrincipalTestData, TokenTestData {

    private lateinit var tokenRepository: TokenRepository
    private lateinit var getTokenBalancesUseCase: GetTokenBalanceUseCase

    private val principal = aPrincipal()
    private val canister = aCanister()

    @BeforeEach
    fun setUp() {
        tokenRepository = mockk()
        getTokenBalancesUseCase = GetTokenBalanceUseCase(
            tokenRepository = tokenRepository
        )
    }

    @Test
    fun `gets token balance`() = runBlocking {
        // given
        val token1 = aToken(ICRC1, canister)
        val token2 = aToken(ICP, canister)
        val balance1 = BigInteger("100")
        val balance2 = BigInteger("200")

        coEvery { tokenRepository.getAllTokens() } returns listOf(token1, token2)
        coEvery { tokenRepository.getTokenBalance(ICRC1, canister, principal) } returnsMany listOf(balance1)
        coEvery { tokenRepository.getTokenBalance(ICP, canister, principal) } returnsMany listOf(balance2)

        // when
        val result = getTokenBalancesUseCase(principal)

        // then
        assertEquals(2, result.size)
        assertEquals(balance1, result[0].balance)
        assertEquals(balance2, result[1].balance)
    }
}