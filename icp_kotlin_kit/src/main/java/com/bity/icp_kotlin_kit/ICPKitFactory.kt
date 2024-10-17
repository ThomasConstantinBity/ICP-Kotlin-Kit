package com.bity.icp_kotlin_kit

import com.bity.icp_kotlin_kit.data.datasource.api.service.ICPRetrofitService
import com.bity.icp_kotlin_kit.data.repository.ICPCanisterRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.LedgerCanisterRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.TokenRepositoryImpl
import com.bity.icp_kotlin_kit.domain.generated_file.LedgerCanister
import com.bity.icp_kotlin_kit.domain.generated_file.NNSICPIndexCanister
import com.bity.icp_kotlin_kit.domain.generated_file.NNS_SNS_W
import com.bity.icp_kotlin_kit.domain.generated_file.Tokens
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.repository.ICPCanisterRepository
import com.bity.icp_kotlin_kit.util.jackson.CborConverterFactory
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.cbor.CBORFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private const val BASE_URL: String = "https://icp-api.io/api/v2/canister/"

private val objectMapper = ObjectMapper(CBORFactory())
private val cborConverterFactory = CborConverterFactory.create(
    objectMapper.apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
)

private val httpClient= OkHttpClient().newBuilder().build()

internal fun provideICPCanisterRepository(): ICPCanisterRepository =
    ICPCanisterRepositoryImpl(
        icpRetrofitService = provideICPRetrofitService(),
    )

private fun provideICPRetrofitService(): ICPRetrofitService =
    Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(cborConverterFactory)
        .build()
        .create(ICPRetrofitService::class.java)

/**
 * Token Repository
 */
internal fun provideTokenRepository() = TokenRepositoryImpl(
    tokensService = Tokens.TokensService(
        canister = ICPPrincipal("b7hhy-tyaaa-aaaah-abbja-cai")
    )
)

internal fun provideLedgerCanisterRepository() = LedgerCanisterRepositoryImpl(
    ledgerCanisterService = LedgerCanister.LedgerCanisterService(
        canister = ICPSystemCanisters.Ledger.icpPrincipal
    )
)

internal fun provideNNSSNSWService(): NNS_SNS_W.nns_sns_wService =
    NNS_SNS_W.nns_sns_wService(
        canister = ICPSystemCanisters.NNS_SNS_W.icpPrincipal
    )

internal val icpIndexService: NNSICPIndexCanister.NNSICPIndexCanisterService by lazy {
    NNSICPIndexCanister.NNSICPIndexCanisterService(
        canister = ICPSystemCanisters.Index.icpPrincipal
    )
}