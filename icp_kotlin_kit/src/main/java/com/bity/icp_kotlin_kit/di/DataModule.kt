package com.bity.icp_kotlin_kit.di

import com.bity.icp_kotlin_kit.data.datasource.api.service.ICPRetrofitService
import com.bity.icp_kotlin_kit.data.factory.NFTRepositoryFactoryImpl
import com.bity.icp_kotlin_kit.data.factory.TokenRepositoryFactoryImpl
import com.bity.icp_kotlin_kit.data.factory.TransactionProviderFactoryImpl
import com.bity.icp_kotlin_kit.data.repository.ICPCanisterRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.LedgerCanisterRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.NFTCachedRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.SNSCachedRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.TokenRepositoryImpl
import com.bity.icp_kotlin_kit.data.repository.TokensCachedRepositoryImpl
import com.bity.icp_kotlin_kit.data.service.nft.NFTCollectionIdServiceImpl
import com.bity.icp_kotlin_kit.data.service.transaction.TransactionServiceImpl
import com.bity.icp_kotlin_kit.domain.factory.NFTRepositoryFactory
import com.bity.icp_kotlin_kit.domain.factory.TokenRepositoryFactory
import com.bity.icp_kotlin_kit.domain.factory.TransactionProviderFactory
import com.bity.icp_kotlin_kit.domain.generated_file.DABNFT
import com.bity.icp_kotlin_kit.domain.generated_file.LedgerCanister
import com.bity.icp_kotlin_kit.domain.generated_file.NNSICPIndexCanister
import com.bity.icp_kotlin_kit.domain.generated_file.NNS_SNS_W
import com.bity.icp_kotlin_kit.domain.generated_file.TokensService
import com.bity.icp_kotlin_kit.domain.model.enum.ICPSystemCanisters
import com.bity.icp_kotlin_kit.domain.repository.ICPCanisterRepository
import com.bity.icp_kotlin_kit.domain.repository.LedgerCanisterRepository
import com.bity.icp_kotlin_kit.domain.repository.NFTCachedRepository
import com.bity.icp_kotlin_kit.domain.repository.SNSCachedRepository
import com.bity.icp_kotlin_kit.domain.repository.TokenRepository
import com.bity.icp_kotlin_kit.domain.service.NFTCollectionIdService
import com.bity.icp_kotlin_kit.domain.repository.TokensCachedRepository
import com.bity.icp_kotlin_kit.domain.service.TransactionService
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

internal val tokenRepository: TokenRepository by lazy {
    TokenRepositoryImpl(
        tokensCachedRepository = tokensCachedRepository,
        tokenRepositoryFactory = tokenRepositoryFactory
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
private val tokensCachedRepository: TokensCachedRepository by lazy {
    TokensCachedRepositoryImpl(
        canister = tokensService,
        actorFactory = tokenRepositoryFactory
    )
}
private val snsCachedRepository: SNSCachedRepository by lazy {
    SNSCachedRepositoryImpl(
        canister = nnsSNSWService
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

val nftCollectionIdService: NFTCollectionIdService by lazy {
    NFTCollectionIdServiceImpl()
}

val transactionService : TransactionService by lazy {
    TransactionServiceImpl(
        tokenRepository = tokenRepository,
        transactionRepositoryFactory = transactionProviderFactory
    )
}

/**
 * Factory
 */
private val tokenRepositoryFactory: TokenRepositoryFactory by lazy {
    TokenRepositoryFactoryImpl()
}

internal val transactionProviderFactory: TransactionProviderFactory by lazy {
    TransactionProviderFactoryImpl(
        snsService = snsCachedRepository,
        indexService = icpIndexService
    )
}






















/**
 * Factory
 */
internal val nftRepositoryFactory: NFTRepositoryFactory by lazy {
    NFTRepositoryFactoryImpl()
}

/**
 * Repository
 */
internal val nftCachedRepository: NFTCachedRepository by lazy {
    NFTCachedRepositoryImpl(
        dabCanister = dabNFTService,
        nftRepositoryFactory = nftRepositoryFactory
    )
}