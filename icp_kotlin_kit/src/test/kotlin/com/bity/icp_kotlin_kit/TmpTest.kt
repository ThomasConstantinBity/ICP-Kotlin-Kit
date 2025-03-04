package com.bity.icp_kotlin_kit

import com.bity.icp_kotlin_kit.data.datasource.api.model.toDomainModel
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVariant
import com.bity.icp_kotlin_kit.data.model.error.RemoteClientError
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.data.service.nft.custom.chain_fusion_toonis.ChainFusionToonisNFTService
import com.bity.icp_kotlin_kit.di.icpCanisterRepository
import com.bity.icp_kotlin_kit.di.nftRepository
import com.bity.icp_kotlin_kit.domain.generated_file.ChainFusionToonis
import com.bity.icp_kotlin_kit.domain.generated_file.DBANFTService
import com.bity.icp_kotlin_kit.domain.generated_file.OrigynNFT
import com.bity.icp_kotlin_kit.domain.model.ICPMethod
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPNftStandard
import com.bity.icp_kotlin_kit.domain.repository.ICPCanisterRepository
import com.bity.icp_kotlin_kit.domain.usecase.nft.GetAllNFTCollectionsUseCase
import com.bity.icp_kotlin_kit.domain.usecase.nft.GetNFTHoldings
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogHandler
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger
import com.bity.icp_kotlin_kit.util.nft_service.NFTServiceUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource


/**
 * tokenId: gold-067412
 * icrc7_tokens:
 * [
 * 0:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726648730583090"
 * 1:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726648730583091"
 * 2:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726648730583092"
 * 3:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726648730583093"
 * 4:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726648730583094"
 * 5:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726648730583095"
 * 6:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726648730583096"
 * 7:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726648730583097"
 * 8:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726653025550384"
 * 9:"220006664755056617740679098655037441121118878992453416312888872764769238635723410719726653025550385"
 * 10:"220006664755056617740679098655037441121118878992453416312889213047135604977049255797226856276557872"
 * 11:"220006664755056617740679098655037441121118878992453416312889213047135604977049255797226856276557873"
 * 12:"220006664755056617740679098655037441121118878992453416312889213047135604977049255797226856276557874"
 * 13:"220006664755056617740679098655037441121118878992453416312889213047135604977049255797226856276557875"
 * 14:"220006664755056617740679098655037441121118878992453416312889213047135604977049255797226856276557876"
 * 15:"220006664755056617740679098655037441121118878992453416312889213047135604977049255797226856276557877"
 * 16:"220006664755056617740679098655037441121118878992453416312889213047135604977049255797226856276557878"
 * 17:"220006664755056617740679098655037441121118878992453416312889213047135604977049255797226856276557879"
 * ]
 */
class TmpTest {

    private val logger = object : ICPKitLogHandler {

        override fun logInfo(message: String) {
            println(message)
        }

        override fun logError(message: String?, throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    @BeforeEach
    fun setup() {
        ICPKitLogger.setLogger(logger)
    }

    @Test
    fun `get all NFTs`() = runTest {
        GetAllNFTCollectionsUseCase()
            .invoke()
            .sortedBy { it.name }
            .forEach {
                logger.logInfo(
                    """
                        --------- ${it.name} ---------
                        standard: ${it.standard.name}
                        canister: ${it.canister.string}
                        ---------------------------
                    """.trimIndent()
                )
            }
    }

    @Test
    fun `Mutant Space Apes`() = runTest {
        val collectionPrincipal = ICPPrincipal("gikg4-eaaaa-aaaam-qaieq-cai")
        val nfts = nftRepository.fetchCollectionNFTs(collectionPrincipal)
        nfts.forEach { nft ->
            logger.logInfo("[${nft.id}] - ${nft.nftId}: ${nft.metadata?.thumbnailUrl}")
        }
    }

    @Test
    fun `Cosmic Birth`() = runTest {
        val collectionPrincipal = ICPPrincipal("vqtoo-uqaaa-aaaap-aajla-cai")
        val nfts = nftRepository.fetchCollectionNFTs(collectionPrincipal)
        nfts.forEach { nft ->
            logger.logInfo("[${nft.id}] - ${nft.nftId}: ${nft.metadata?.thumbnailUrl}")
        }
    }

    @Test
    fun `Crypto Cat`() = runTest {
        val collectionPrincipal = ICPPrincipal("pjuco-6iaaa-aaaam-adu7q-cai")
        val nfts = nftRepository.fetchCollectionNFTs(collectionPrincipal)
        nfts.forEach { nft ->
            logger.logInfo("[${nft.id}] - ${nft.nftId}: ${nft.metadata?.thumbnailUrl}")
        }
    }

    @Test
    // Standard is ICRC7 but doesn't support icrc7_tokens
    fun `Chain Fusion Toonis`() = runTest {
        val collectionPrincipal = ICPPrincipal("nsbts-5iaaa-aaaah-aeblq-cai")
        val nfts = nftRepository.fetchCollectionNFTs(collectionPrincipal)
        nfts.forEach { nft ->
            logger.logInfo("[${nft.id}] - ${nft.nftId}: ${nft.metadata?.thumbnailUrl}")
        }
    }

    @Test
    fun `Cashier Collection`() = runTest {
        val collectionPrincipal = ICPPrincipal("hfevg-caaaa-aaaai-actwa-cai")
        val nfts = nftRepository.fetchCollectionNFTs(collectionPrincipal)
        nfts.forEach { nft ->
            logger.logInfo("[${nft.id}] - ${nft.nftId}: ${nft.metadata?.thumbnailUrl}")
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("icrc7Canisters")
    fun `ICRC7 NFTs balance`(
        collectionName: String,
        canister: String
    ) = runTest {
        val service = DBANFTService(ICPPrincipal(canister))
        try {
            val nfts = service.icrc7_tokens(null, null)
            logger.logInfo("Tokens for ${collectionName}: ${nfts.size}")
            nfts.map {
                async {
                    val realNFTHolder = service.icrc7_owner_of(arrayOf(it))
                    logger.logInfo("Holder of ${it}: ${realNFTHolder.firstOrNull()?.owner?.string}")
                    realNFTHolder.firstOrNull()?.owner?.toDomainModel()?.let {
                        val nftHoldings = GetNFTHoldings().invoke(it)
                        logger.logInfo("NFT holding for ${it.string}: ${nftHoldings.size}")
                    }
                }
            }.awaitAll()
        } catch (t: Throwable) {
            if(t !is RemoteClientError) {
                logger.logError(throwable = t)
                fail(t)
            }
        }
    }

    @Test
    fun tmpTest() = runTest {

        /*val nftList = GetAllNFTCollectionsUseCase().invoke()
        nftList.forEach { println("${it.name} - ${it.canister.string}") }*/

        // GLD NFT
        // val principal = ICPPrincipal("ahzsn-oryvk-4joqr-orao4-glrr3-ovhmd-bacbr-ojr5q-2k4lw-neyy4-xqe")

        val principal = ICPPrincipal("cae3j-vi4cf-pyafe-s6wrq-k6f73-2auvo-hr4ir-kovqi-pboap-nu47v-4qe")
        val nftHolding = GetNFTHoldings().invoke(principal)
        println("NFT holding for ${principal.string}: ${nftHolding.size}")
        nftHolding.forEach { println(it) }

    }

    @Test
    fun parsingTest() = runTest {

        val canisterRepository: ICPCanisterRepository = icpCanisterRepository
        val canister = ICPPrincipal("zhfjc-liaaa-aaaal-acgja-cai")

        val query = ICPQuery(
            methodName = "balance_of_nft_origyn",
            canister = canister
        )

        /*val args = listOf(
            CandidValue.Record(
                CandidRecord.init(hashMapOf("principal" to CandidValue.Principal("ahzsn-oryvk-4joqr-orao4-glrr3-ovhmd-bacbr-ojr5q-2k4lw-neyy4-xqe"))))
        )*/

        val args = listOf(
            CandidValue.Variant(
                CandidVariant(
                    candidTypes = mapOf("principal" to CandidType.Principal),
                    value = Pair("principal", CandidValue.Principal("ahzsn-oryvk-4joqr-orao4-glrr3-ovhmd-bacbr-ojr5q-2k4lw-neyy4-xqe"))
                )
            )
        )
        val icpMethod = ICPMethod(
            canister = canister,
            methodName = "balance_of_nft_origyn",
            args = args
        )
        val result = canisterRepository.query(icpMethod)
        val ok = CandidDecoder.decodeNotNull<OrigynNFT.BalanceResult>(result.getOrThrow().first())
        println((ok as OrigynNFT.BalanceResult.ok).ok)
    }

    companion object {

        /**
         * --------- BAD 3D Avatars ---------
         * standard: ICRC7
         * canister: lzhom-nqaaa-aaaap-ahmpq-cai
         * ---------------------------

         * --------- Chain Fusion Toonis ---------
         * standard: ICRC7
         * canister: nsbts-5iaaa-aaaah-aeblq-cai
         * ---------------------------
         * --------- Cosmicrafts Game NFTs ---------
         * standard: ICRC7
         * canister: phgme-naaaa-aaaap-abwda-cai
         * ---------------------------
         * --------- Cosmicrafts Lootboxes ---------
         * standard: ICRC7
         * canister: w4fdk-fiaaa-aaaap-qccgq-cai
         * ---------------------------
         * --------- DAS - Scott Bateman ---------
         * standard: ICRC7
         * canister: cokeu-yaaaa-aaaag-qjvea-cai
         * ---------------------------
         * --------- Front Row  ---------
         * standard: ICRC7
         * canister: frbof-fqaaa-aaaal-qshca-cai
         * ---------------------------

         */

        @JvmStatic
        private fun icrc7Canisters() = listOf(
            Arguments.of(
                "Cashier Collection",
                "hfevg-caaaa-aaaai-actwa-cai"
            ),
            Arguments.of(
                "Crypto Cat",
                "pjuco-6iaaa-aaaam-adu7q-cai"
            ),
            Arguments.of(
                "Front Row Test 23062024",
                "auw3m-7yaaa-aaaal-qjf6q-cai"
            )
        )
    }

}