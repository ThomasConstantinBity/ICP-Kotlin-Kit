package com.bity.icp_kotlin_kit.domain.generated_file

import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.NTuple7
import com.bity.icp_kotlin_kit.domain.model.request.PollingValues
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification

/**
 * File generated using ICP Kotlin Kit Plugin
 */
private typealias UpdateCallsAggregatedData = Array<ULong>
private typealias TokenIndex__1 = UInt
private typealias TokenIndex = UInt
private typealias TokenIdentifier__2 = String
private typealias TokenIdentifier__1 = String
private typealias TokenIdentifier = String
private typealias Time = BigInteger
private typealias Supply = BigInteger
private typealias SubAccount__2 = Array<UByte>
private typealias SubAccount__1 = Array<UByte>
private typealias EXTSubAccount = Array<UByte>
private typealias Recipe = Array<String>
private typealias Nanos = ULong
private typealias Memo = ByteArray
private typealias ListingResponse = Array<Any>
private typealias Inventory = Array<ItemInventory>
private typealias Floor = ULong
private typealias Extension = String
private typealias CanisterMemoryAggregatedData = Array<ULong>
private typealias CanisterHeapMemoryAggregatedData = Array<ULong>
private typealias CanisterCyclesAggregatedData = Array<ULong>
private typealias Balance = BigInteger
private typealias Airdrop = Array<String>
private typealias AccountIdentifier__2 = String
private typealias AccountIdentifier__1 = String
private typealias AccountIdentifier = String
sealed class User {
    class address(
        val accountIdentifier: AccountIdentifier
    ): User()

    class principal(
        val iCPPrincipalApiModel: ICPPrincipalApiModel
    ): User()

}

sealed class TypeReward {
    class Material(
        val nFT: NFT
    ): TypeReward()

    class NFT(
        val nFT: NFT
    ): TypeReward()

    data object Other : TypeReward()
    class Token(
        val token: Token
    ): TypeReward()

}

sealed class TransferResponse {
    sealed class ERR {
        class CannotNotify(
            val accountIdentifier: AccountIdentifier
        ): ERR()

        data object InsufficientBalance : ERR()
        class InvalidToken(
            val tokenIdentifier__1: TokenIdentifier__1
        ): ERR()

        class Other(
            val string: String
        ): ERR()

        data object Rejected : ERR()
        class Unauthorized(
            val accountIdentifier: AccountIdentifier
        ): ERR()

    }

    class ok(
        val balance: Balance
    ): TransferResponse()

}

class TransferRequest(
    val amount: Balance,
    val from: User,
    val memo: Memo,
    val notify: Boolean,
    val subaccount: EXTSubAccount?,
    val to: User,
    val token: TokenIdentifier__1
)

class Transaction(
    val bytes: kotlin.Array<UByte>,
    val closed: Time?,
    val from: AccountIdentifier__1,
    val id: BigInteger,
    val initiated: Time,
    val memo: ByteArray?,
    val price: ULong,
    val seller: ICPPrincipalApiModel,
    val to: AccountIdentifier__1,
    val token: TokenIdentifier__2
)

class Token(
    val decimals: UByte,
    val name: String
)

sealed class Template {
    class Accessory(
        val after_wear: String,
        val before_wear: String,
        val recipe: Recipe
    ): Template()

    class LegendaryAccessory(
        val byteArray: ByteArray
    ): Template()

    class Material(
        val byteArray: ByteArray
    ): Template()

}

sealed class StreamingStrategy {
    class Callback(
        val callback: StreamingCallback,
        val token: StreamingCallbackToken
    ): StreamingStrategy()

}

class StreamingCallbackToken(
    val content_encoding: String,
    val index: BigInteger,
    val key: String
)

class StreamingCallbackResponse(
    val body: ByteArray,
    val token: StreamingCallbackToken?
)

class StreamingCallback(
    methodName: String,
    canister: ICPPrincipal
) : ICPQuery (

    methodName = methodName,
    canister = canister
) {

    suspend operator fun invoke(
        streamingCallbackToken: StreamingCallbackToken,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): StreamingCallbackResponse {

        val result = this(
            values = listOf(
                ValueToEncode(streamingCallbackToken)
            ),
            certification = certification,
            sender = sender,
            pollingValues = pollingValues
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

}

class Reward(
    val amount: BigInteger,
    val category: TypeReward,
    val collection: ICPPrincipalApiModel,
    val date: Time
)

sealed class Result__1_2 {
    class err(
        val commonError__1: CommonError__1
    ): Result__1_2()

    data object ok : Result__1_2()
}

sealed class Result__1_1 {
    class err(
        val string: String
    ): Result__1_1()

    class ok(
        val tokenIdentifier: TokenIdentifier
    ): Result__1_1()

}

sealed class Result__1 {
    class err(
        val string: String
    ): Result__1()

    data object ok : Result__1()
}

sealed class Result_7 {
    class err(
        val string: String
    ): Result_7()

    class ok(
        val string: String
    ): Result_7()

}

sealed class Result_6 {
    class err(
        val commonError: CommonError
    ): Result_6()

    class ok(
        val accountIdentifier__2: AccountIdentifier__2
    ): Result_6()

}

sealed class Result_5 {
    class err(
        val string: String
    ): Result_5()

    class ok(
        val inventory: Inventory
    ): Result_5()

}

sealed class Result_4 {
    class err(
        val commonError__1: CommonError__1
    ): Result_4()

    class ok(
        val metadata: Metadata
    ): Result_4()

}

sealed class Result_3 {
    class err(
        val commonError: CommonError
    ): Result_3()

    data object ok : Result_3()
}

sealed class Result_2 {
    class err(
        val commonError: CommonError
    ): Result_2()

    class ok(
        val values: kotlin.Array<TokenIndex>
    ): Result_2()

}

sealed class Result_1 {
    class err(
        val commonError: CommonError
    ): Result_1()

    class ok(
        val values: Array<_ArrayClass>
    ): Result_1() {

        class _ArrayClass(
            val tokenIndex: TokenIndex,
            val listing: Listing?,
            val byteArray: ByteArray?
        )

    }

}

sealed class Result {
    class err(
        val string: String
    ): Result()

    data object ok : Result()
}

class Response(
    val body: ByteArray,
    val headers: kotlin.Array<HeaderField>,
    val status_code: UShort,
    val streaming_strategy: StreamingStrategy?
)

class Request(
    val body: ByteArray,
    val headers: kotlin.Array<HeaderField>,
    val method: String,
    val url: String
)

class NumericEntity(
    val avg: ULong,
    val first: ULong,
    val last: ULong,
    val max: ULong,
    val min: ULong
)

class NFT(
    val identifier: String,
    val name: String
)

sealed class MetricsGranularity {
    data object daily : MetricsGranularity()
    data object hourly : MetricsGranularity()
}

sealed class Metadata__1 {
    class fungible(
        val decimals: UByte,
        val metadata: ByteArray?,
        val name: String,
        val symbol: String
    ): Metadata__1()

    class nonfungible(
        val metadata: ByteArray?
    ): Metadata__1()

}

sealed class Metadata {
    class fungible(
        val decimals: UByte,
        val metadata: ByteArray?,
        val name: String,
        val symbol: String
    ): Metadata()

    class nonfungible(
        val metadata: ByteArray?
    ): Metadata()

}

class MaterialInventory(
    val name: String,
    val tokenIdentifier: String
)

class LogMessagesData(
    val message: String,
    val timeNanos: Nanos
)

sealed class LockResponse {
    class err(
        val commonError__2: CommonError__2
    ): LockResponse()

    class ok(
        val accountIdentifier__1: AccountIdentifier__1
    ): LockResponse()

}

class Listing__1(
    val locked: Time?,
    val price: ULong,
    val seller: ICPPrincipalApiModel,
    val subaccount: SubAccount__1?
)

class Listing(
    val locked: Time?,
    val price: ULong,
    val seller: ICPPrincipalApiModel,
    val subaccount: EXTSubAccount?
)

sealed class ListResponse {
    class err(
        val commonError__2: CommonError__2
    ): ListResponse()

    data object ok : ListResponse()
}

class ListRequest(
    val from_subaccount: SubAccount__1?,
    val price: ULong?,
    val token: TokenIdentifier__2
)

sealed class ItemInventory {
    class Accessory(
        val accessoryInventory: AccessoryInventory
    ): ItemInventory()

    class Material(
        val materialInventory: MaterialInventory
    ): ItemInventory()

}

class HourlyMetricsData(
    val canisterCycles: CanisterCyclesAggregatedData,
    val canisterHeapMemorySize: CanisterHeapMemoryAggregatedData,
    val canisterMemorySize: CanisterMemoryAggregatedData,
    val timeMillis: BigInteger,
    val updateCalls: UpdateCallsAggregatedData
)

class HeaderField(
    val string: String,
    val string_1: String
)

class GetMetricsParameters(
    val dateFromMillis: BigInteger,
    val dateToMillis: BigInteger,
    val granularity: MetricsGranularity
)

class GetLogMessagesParameters(
    val count: UInt,
    val filter: GetLogMessagesFilter?,
    val fromTimeNanos: Nanos?
)

class GetLogMessagesFilter(
    val analyzeCount: UInt,
    val messageContains: String?,
    val messageRegex: String?
)

class GetLatestLogMessagesParameters(
    val count: UInt,
    val filter: GetLogMessagesFilter?,
    val upToTimeNanos: Nanos?
)

class ExtListing(
    val locked: Time?,
    val price: ULong,
    val seller: ICPPrincipalApiModel
)

class EntrepotTransaction(
    val buyer: AccountIdentifier__1,
    val price: ULong,
    val seller: ICPPrincipalApiModel,
    val time: Time,
    val token: TokenIdentifier__2
)

class Disbursement(
    val tokenIndex__1: TokenIndex__1,
    val accountIdentifier__1: AccountIdentifier__1,
    val subAccount__1: SubAccount__1,
    val uLong: ULong
)

sealed class DetailsResponse {
    class err(
        val commonError__2: CommonError__2
    ): DetailsResponse()

    class ok(
        val accountIdentifier__1: AccountIdentifier__1,
        val listing__1: Listing__1?
    ): DetailsResponse()

}

class DailyMetricsData(
    val canisterCycles: NumericEntity,
    val canisterHeapMemorySize: NumericEntity,
    val canisterMemorySize: NumericEntity,
    val timeMillis: BigInteger,
    val updateCalls: ULong
)

sealed class CommonError__2 {
    class InvalidToken(
        val tokenIdentifier__1: TokenIdentifier__1
    ): CommonError__2()

    class Other(
        val string: String
    ): CommonError__2()

}

sealed class CommonError__1 {
    class InvalidToken(
        val tokenIdentifier__1: TokenIdentifier__1
    ): CommonError__1()

    class Other(
        val string: String
    ): CommonError__1()

}

sealed class CommonError {
    class InvalidToken(
        val tokenIdentifier__1: TokenIdentifier__1
    ): CommonError()

    class Other(
        val string: String
    ): CommonError()

}

sealed class CanisterMetricsData {
    class daily(
        val values: kotlin.Array<DailyMetricsData>
    ): CanisterMetricsData()

    class hourly(
        val values: kotlin.Array<HourlyMetricsData>
    ): CanisterMetricsData()

}

class CanisterMetrics(
    val data: CanisterMetricsData
)

sealed class CanisterLogResponse {
    class messages(
        val canisterLogMessages: CanisterLogMessages
    ): CanisterLogResponse()

    class messagesInfo(
        val canisterLogMessagesInfo: CanisterLogMessagesInfo
    ): CanisterLogResponse()

}

sealed class CanisterLogRequest {
    class getLatestMessages(
        val getLatestLogMessagesParameters: GetLatestLogMessagesParameters
    ): CanisterLogRequest()

    class getMessages(
        val getLogMessagesParameters: GetLogMessagesParameters
    ): CanisterLogRequest()

    data object getMessagesInfo : CanisterLogRequest()
}

class CanisterLogMessagesInfo(
    val count: UInt,
    val features: kotlin.Array<CanisterLogFeature>,
    val firstTimeNanos: Nanos?,
    val lastTimeNanos: Nanos?
)

class CanisterLogMessages(
    val data: kotlin.Array<LogMessagesData>,
    val lastAnalyzedMessageTimeNanos: Nanos?
)

sealed class CanisterLogFeature {
    data object filterMessageByContains : CanisterLogFeature()
    data object filterMessageByRegex : CanisterLogFeature()
}

sealed class BalanceResponse {
    class err(
        val commonError__1: CommonError__1
    ): BalanceResponse()

    class ok(
        val balance: Balance
    ): BalanceResponse()

}

class BalanceRequest(
    val token: TokenIdentifier__1,
    val user: User
)

class AccessoryInventory(
    val equipped: Boolean,
    val name: String,
    val tokenIdentifier: String
)

class EXTService(
    private val canister: ICPPrincipal
) {

    suspend fun acceptCycles         (
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "acceptCycles",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        TODO()
    }

    suspend fun add_admin                    (
        iCPPrincipalApiModel: ICPPrincipalApiModel,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "add_admin",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(iCPPrincipalApiModel)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun add_template                    (
        string: String,
        template: Template,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result_7 {

        val icpQuery = ICPQuery(
            methodName = "add_template",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(string), ValueToEncode(template)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun airdrop_rewards                    (
        unnamedClass0: kotlin.Array<UnnamedClass0>,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "airdrop_rewards",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(unnamedClass0)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    class UnnamedClass0(
        val accountIdentifier__2: AccountIdentifier__2,
        val airdrop: Airdrop
    )

    suspend fun availableCycles         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger {

        val icpQuery = ICPQuery(
            methodName = "availableCycles",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun balance                    (
        balanceRequest: BalanceRequest,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BalanceResponse {

        val icpQuery = ICPQuery(
            methodName = "balance",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(balanceRequest)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun bearer                    (
        tokenIdentifier: TokenIdentifier,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result_6 {

        val icpQuery = ICPQuery(
            methodName = "bearer",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(tokenIdentifier)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun can_settle                    (
        iCPPrincipalApiModel: ICPPrincipalApiModel,
        tokenIdentifier__1: TokenIdentifier__1,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result__1_2 {

        val icpQuery = ICPQuery(
            methodName = "can_settle",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(iCPPrincipalApiModel), ValueToEncode(tokenIdentifier__1)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun collectCanisterMetrics         (
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "collectCanisterMetrics",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun create_accessory                    (
        string: String,
        bigInteger: BigInteger,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result__1_1 {

        val icpQuery = ICPQuery(
            methodName = "create_accessory",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(string), ValueToEncode(bigInteger)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun cron_burned         (
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "cron_burned",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun cron_disbursements         (
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "cron_disbursements",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun cron_events         (
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "cron_events",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun cron_settlements         (
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "cron_settlements",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun cron_verification         (
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "cron_verification",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun details                    (
        tokenIdentifier: TokenIdentifier,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): DetailsResponse {

        val icpQuery = ICPQuery(
            methodName = "details",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(tokenIdentifier)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun extensions         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<Extension> {

        val icpQuery = ICPQuery(
            methodName = "extensions",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun getCanisterLog                    (
        canisterLogRequest: CanisterLogRequest?,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): CanisterLogResponse? {

        val icpQuery = ICPQuery(
            methodName = "getCanisterLog",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(
                arg = canisterLogRequest,
                expectedClass = CanisterLogRequest::class,
                expectedClassNullable = true
            )),

            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun getCanisterMetrics                    (
        getMetricsParameters: GetMetricsParameters,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): CanisterMetrics? {

        val icpQuery = ICPQuery(
            methodName = "getCanisterMetrics",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(getMetricsParameters)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun getInventory         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result_5 {

        val icpQuery = ICPQuery(
            methodName = "getInventory",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun getRegistry         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<UnnamedClass1> {

        val icpQuery = ICPQuery(
            methodName = "getRegistry",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    class UnnamedClass1(
        val tokenIndex: TokenIndex,
        val accountIdentifier__2: AccountIdentifier__2
    )

    suspend fun getTokens         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<UnnamedClass2> {

        val icpQuery = ICPQuery(
            methodName = "getTokens",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    class UnnamedClass2(
        val tokenIndex: TokenIndex,
        val metadata: Metadata
    )

    suspend fun get_admins         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<ICPPrincipalApiModel> {

        val icpQuery = ICPQuery(
            methodName = "get_admins",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun get_pending_transactions         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<UnnamedClass3> {

        val icpQuery = ICPQuery(
            methodName = "get_pending_transactions",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    class UnnamedClass3(
        val tokenIndex: TokenIndex,
        val transaction: Transaction
    )

    suspend fun get_recorded_rewards                    (
        iCPPrincipalApiModel: ICPPrincipalApiModel,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<Reward>? {

        val icpQuery = ICPQuery(
            methodName = "get_recorded_rewards",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(iCPPrincipalApiModel)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun get_stats_items         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<UnnamedClass4> {

        val icpQuery = ICPQuery(
            methodName = "get_stats_items",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    class UnnamedClass4(
        val string: String,
        val supply: Supply,
        val floor: Floor?
    )

    suspend fun http_request                    (
        request: Request,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Response {

        val icpQuery = ICPQuery(
            methodName = "http_request",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(request)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun is_admin                    (
        iCPPrincipalApiModel: ICPPrincipalApiModel,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Boolean {

        val icpQuery = ICPQuery(
            methodName = "is_admin",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(iCPPrincipalApiModel)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun list                    (
        listRequest: ListRequest,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): ListResponse {

        val icpQuery = ICPQuery(
            methodName = "list",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(listRequest)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun listings         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): ListingResponse {

        val icpQuery = ICPQuery(
            methodName = "listings",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun lock                    (
        tokenIdentifier: TokenIdentifier,
        uLong: ULong,
        accountIdentifier__2: AccountIdentifier__2,
        uByte: kotlin.Array<UByte>,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): LockResponse {

        val icpQuery = ICPQuery(
            methodName = "lock",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(tokenIdentifier), ValueToEncode(uLong), ValueToEncode(accountIdentifier__2), ValueToEncode(uByte)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun metadata                    (
        tokenIdentifier: TokenIdentifier,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result_4 {

        val icpQuery = ICPQuery(
            methodName = "metadata",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(tokenIdentifier)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun payments         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<SubAccount__2>? {

        val icpQuery = ICPQuery(
            methodName = "payments",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decode(result.first())
    }

    suspend fun read_disbursements         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<Disbursement> {

        val icpQuery = ICPQuery(
            methodName = "read_disbursements",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun record_icps                    (
        accountIdentifier__2: AccountIdentifier__2,
        bigInteger: BigInteger,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "record_icps",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(accountIdentifier__2), ValueToEncode(bigInteger)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun record_nft                    (
        accountIdentifier__2: AccountIdentifier__2,
        iCPPrincipalApiModel: ICPPrincipalApiModel,
        string: String,
        string_1: String,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "record_nft",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(
                ValueToEncode(accountIdentifier__2),
                ValueToEncode(iCPPrincipalApiModel),
                ValueToEncode(string),
                ValueToEncode(string_1)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun record_token                    (
        accountIdentifier__2: AccountIdentifier__2,
        bigInteger: BigInteger,
        string: String,
        uByte: UByte,
        iCPPrincipalApiModel: ICPPrincipalApiModel,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "record_token",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(accountIdentifier__2), ValueToEncode(bigInteger), ValueToEncode(string), ValueToEncode(uByte), ValueToEncode(iCPPrincipalApiModel)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun remove_accessory                    (
        tokenIdentifier: TokenIdentifier,
        tokenIdentifier_1: TokenIdentifier,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result__1 {

        val icpQuery = ICPQuery(
            methodName = "remove_accessory",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(tokenIdentifier), ValueToEncode(tokenIdentifier_1)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun remove_admin                    (
        iCPPrincipalApiModel: ICPPrincipalApiModel,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "remove_admin",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(iCPPrincipalApiModel)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun remove_record_nft                    (
        accountIdentifier__2: AccountIdentifier__2,
        string: String,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "remove_record_nft",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(accountIdentifier__2), ValueToEncode(string)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun setMaxMessagesCount                    (
        bigInteger: BigInteger,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "setMaxMessagesCount",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(bigInteger)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun settle                    (
        tokenIdentifier: TokenIdentifier,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result_3 {

        val icpQuery = ICPQuery(
            methodName = "settle",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(tokenIdentifier)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun size         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): BigInteger {

        val icpQuery = ICPQuery(
            methodName = "size",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun stats         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): NTuple7<ULong, ULong, ULong, ULong, BigInteger, BigInteger, BigInteger> {

        val icpQuery = ICPQuery(
            methodName = "stats",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return NTuple7(
            a = CandidDecoder.decodeNotNull<ULong>(result[0]),
            b = CandidDecoder.decodeNotNull<ULong>(result[1]),
            c = CandidDecoder.decodeNotNull<ULong>(result[2]),
            d = CandidDecoder.decodeNotNull<ULong>(result[3]),
            e = CandidDecoder.decodeNotNull<BigInteger>(result[4]),
            f = CandidDecoder.decodeNotNull<BigInteger>(result[5]),
            g = CandidDecoder.decodeNotNull<BigInteger>(result[6])
        )
    }

    suspend fun tokenId                    (
        tokenIndex: TokenIndex,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): TokenIdentifier {

        val icpQuery = ICPQuery(
            methodName = "tokenId",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(tokenIndex)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun tokens                    (
        accountIdentifier__2: AccountIdentifier__2,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result_2 {

        val icpQuery = ICPQuery(
            methodName = "tokens",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(accountIdentifier__2)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun tokens_ext                    (
        accountIdentifier__2: AccountIdentifier__2,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result_1 {

        val icpQuery = ICPQuery(
            methodName = "tokens_ext",
            canister = canister
        )

        val result = icpQuery(
            values = listOf(ValueToEncode(accountIdentifier__2)),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun transactions         (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): kotlin.Array<EntrepotTransaction> {

        val icpQuery = ICPQuery(
            methodName = "transactions",
            canister = canister
        )

        val result = icpQuery(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun transfer                    (
        transferRequest: TransferRequest,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): TransferResponse {

        val icpQuery = ICPQuery(
            methodName = "transfer",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(transferRequest)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

    suspend fun update_accessories         (
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ) {

        val icpQuery = ICPQuery(
            methodName = "update_accessories",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = null,
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()
        TODO()
    }

    suspend fun wear_accessory                    (
        tokenIdentifier_1: TokenIdentifier,
        tokenIdentifier_2: TokenIdentifier,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Result {

        val icpQuery = ICPQuery(
            methodName = "wear_accessory",
            canister = canister
        )

        val result = icpQuery.callAndPoll(
            values = listOf(ValueToEncode(tokenIdentifier_1), ValueToEncode(tokenIdentifier_2)),
            sender = sender,
            pollingValues = pollingValues,
        ).getOrThrow()

        return CandidDecoder.decodeNotNull(result.first())
    }

}