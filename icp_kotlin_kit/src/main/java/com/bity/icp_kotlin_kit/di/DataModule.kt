package com.bity.icp_kotlin_kit.di

import com.bity.icp_kotlin_kit.data.datasource.api.service.ICPRetrofitService
import com.bity.icp_kotlin_kit.data.factory.NFTServiceFactoryImpl
import com.bity.icp_kotlin_kit.data.factory.TokenServiceFactoryImpl
import com.bity.icp_kotlin_kit.data.factory.TransactionProviderFactoryImpl
import com.bity.icp_kotlin_kit.data.repository.ICPCanisterRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.LedgerCanisterRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.NFTCachedServiceImpl
import com.bity.icp_kotlin_kit.data.repository.NFTRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.SNSCachedServiceImpl
import com.bity.icp_kotlin_kit.data.repository.TokenRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.TokensCachedServiceImpl
import com.bity.icp_kotlin_kit.domain.factory.NFTServiceFactory
import com.bity.icp_kotlin_kit.domain.factory.TokenServiceFactory
import com.bity.icp_kotlin_kit.domain.factory.TransactionProviderFactory
import com.bity.icp_kotlin_kit.domain.generated_file.DABNFT
import com.bity.icp_kotlin_kit.domain.generated_file.LedgerCanister
import com.bity.icp_kotlin_kit.domain.generated_file.NNSICPIndexCanister
import com.bity.icp_kotlin_kit.domain.generated_file.NNS_SNS_W
import com.bity.icp_kotlin_kit.domain.generated_file.TokensService
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.repository.ICPCanisterRepository
import com.bity.icp_kotlin_kit.domain.repository.LedgerCanisterRepository
import com.bity.icp_kotlin_kit.domain.service.NFTCachedService
import com.bity.icp_kotlin_kit.domain.repository.NFTRepository
import com.bity.icp_kotlin_kit.domain.service.SNSCachedService
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import com.bity.icp_kotlin_kit.domain.service.TokensCachedService
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
private val httpClient = OkHttpClient().newBuilder().build()

private val icpRetrofitService: ICPRetrofitService =
    Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(cborConverterFactory)
        .build()
        .create(ICPRetrofitService::class.java)

internal val icpCanisterRepository: ICPCanisterRepository by lazy {
    ICPCanisterRepositoryImpl(
        icpRetrofitService = icpRetrofitService,
    )
}

internal val ledgerCanisterRepository: LedgerCanisterRepository by lazy {
    LedgerCanisterRepositoryImpl(
        ledgerCanisterService = ledgerCanisterService
    )
}

val nftRepository: NFTRepository by lazy {
    NFTRepositoryImpl(
        nftServiceFactory = nftActorFactory,
        nftCachedService = nftCachedService
    )
}

internal val tokenRepository: TokenRepository by lazy {
    TokenRepositoryImpl(
        tokensCachedService = tokensCachedService,
        actorFactory = tokenServiceFactory
    )
}

/**
 * Service
 */
private val ledgerCanisterService: LedgerCanister.LedgerCanisterService by lazy {
    LedgerCanister.LedgerCanisterService(
        canister = ICPSystemCanisters.Ledger.icpPrincipal
    )
}
private val dabNFTService: DABNFT.DABNFTService by lazy {
    DABNFT.DABNFTService(
        canister = ICPSystemCanisters.NFTRegistry.icpPrincipal
    )
}
private val tokensService: TokensService by lazy {
    TokensService(
        canister = ICPSystemCanisters.TokenRegistry.icpPrincipal
    )
}
private val tokensCachedService: TokensCachedService by lazy {
    TokensCachedServiceImpl(
        tokensService = tokensService,
        actorFactory = tokenServiceFactory
    )
}
private val nftCachedService: NFTCachedService by lazy {
    NFTCachedServiceImpl(
        nftService = dabNFTService
    )
}
private val snsCachedService: SNSCachedService by lazy {
    SNSCachedServiceImpl(
        service = nnsSNSWService
    )
}

internal val nnsSNSWService: NNS_SNS_W.nns_sns_wService by lazy {
    NNS_SNS_W.nns_sns_wService(
        canister = ICPSystemCanisters.NNS_SNS_W.icpPrincipal
    )
}

private val icpIndexService: NNSICPIndexCanister.NNSICPIndexCanisterService by lazy {
    NNSICPIndexCanister.NNSICPIndexCanisterService(
        canister = ICPSystemCanisters.Index.icpPrincipal
    )
}

/**
 * Factory
 */
private val tokenServiceFactory: TokenServiceFactory by lazy {
    TokenServiceFactoryImpl()
}

private val nftActorFactory: NFTServiceFactory by lazy {
    NFTServiceFactoryImpl()
}

internal val transactionProviderFactory: TransactionProviderFactory by lazy {
    TransactionProviderFactoryImpl(
        snsService = snsCachedService,
        indexService = icpIndexService
    )
}