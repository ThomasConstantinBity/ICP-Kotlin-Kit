package com.bity.icp_kotlin_kit

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVariant
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.di.icpCanisterRepository
import com.bity.icp_kotlin_kit.domain.generated_file.OrigynNFT
import com.bity.icp_kotlin_kit.domain.model.ICPMethod
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.toDataModel
import com.bity.icp_kotlin_kit.domain.repository.ICPCanisterRepository
import com.bity.icp_kotlin_kit.domain.usecase.nft.GetNFTHoldings
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


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

}