package com.bity.icp_kotlin_kit

import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.usecase.nft.GetNFTHoldings
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class TmpTest {

    @Test
    fun test() = runTest {
        GetNFTHoldings().invoke(ICPPrincipal("mi5lp-tjcms-b77vo-qbfgp-cjzyc-imkew-uowpv-ca7f4-l5fzx-yy6ba-qqe"))
    }
}