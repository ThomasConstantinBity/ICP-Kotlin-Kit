package com.bity.icp_kotlin_kit.data.service

import com.bity.icp_kotlin_kit.data.service.nft.EXTNFTService
import com.bity.icp_kotlin_kit.di.nftCollectionIdService
import com.bity.icp_kotlin_kit.domain.generated_file.EXTService
import com.bity.icp_kotlin_kit.domain.generated_file.EXTService.UnnamedClass2
import com.bity.icp_kotlin_kit.domain.generated_file.Metadata
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.util.MutantSpaceApesPrincipal
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class EXTNFTServiceTest {

    private val canister = mockk<ICPPrincipal>()
    private val extService = mockk<EXTService>()
    private lateinit var service: EXTNFTService

    @BeforeEach
    fun setUp() {
        service = EXTNFTService(
            canister = canister,
            service = extService,
            idService = nftCollectionIdService
        )
    }

    @Test
    fun `fetchCollectionNFTs Mutant Space Apes`() = runTest {
        val collectionPrincipal = MutantSpaceApesPrincipal

        coEvery { canister.bytes } returns MutantSpaceApesPrincipal.bytes
        coEvery { canister.string } returns MutantSpaceApesPrincipal.string
        coEvery { extService.getTokens(any(), any(), any()) } returns arrayOf(
            UnnamedClass2(
                tokenIndex = 0U,
                metadata = Metadata.nonfungible(null)
            ),
            UnnamedClass2(
                tokenIndex = 1U,
                metadata = Metadata.nonfungible(null)
            ),
            UnnamedClass2(
                tokenIndex = 373U,
                metadata = Metadata.nonfungible(null)
            )
        )

        val nftCollection = service.fetchNFTs(collectionPrincipal)

        assertEquals(3, nftCollection.size)

        assertEquals(BigInteger.ZERO, nftCollection.first().id)
        assertEquals("jkgnc-hakor-uwiaa-aaaaa-deacb-eaqca-aaaaa-a", nftCollection.first().nftId)
        assertEquals("https://gikg4-eaaaa-aaaam-qaieq-cai.raw.icp0.io/?tokenid=jkgnc-hakor-uwiaa-aaaaa-deacb-eaqca-aaaaa-a", nftCollection.first().metadata?.thumbnailUrl)

        assertEquals(BigInteger.ONE, nftCollection[1].id)
        assertEquals("hwf6d-cqkor-uwiaa-aaaaa-deacb-eaqca-aaaaa-q", nftCollection[1].nftId)
        assertEquals("https://gikg4-eaaaa-aaaam-qaieq-cai.raw.icp0.io/?tokenid=hwf6d-cqkor-uwiaa-aaaaa-deacb-eaqca-aaaaa-q", nftCollection[1].metadata?.thumbnailUrl)

        assertEquals(BigInteger("373"), nftCollection.last().id)
        assertEquals("op4gl-3qkor-uwiaa-aaaaa-deacb-eaqca-aaaf2-q", nftCollection.last().nftId)
        assertEquals("https://gikg4-eaaaa-aaaam-qaieq-cai.raw.icp0.io/?tokenid=op4gl-3qkor-uwiaa-aaaaa-deacb-eaqca-aaaf2-q", nftCollection.last().metadata?.thumbnailUrl)
    }

}