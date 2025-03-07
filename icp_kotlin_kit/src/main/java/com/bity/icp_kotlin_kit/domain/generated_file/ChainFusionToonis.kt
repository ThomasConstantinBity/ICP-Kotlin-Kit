package com.bity.icp_kotlin_kit.domain.generated_file

import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification

/**
 * type TokenIndex__1 = nat32;
 */
typealias CFTTokenIndex__1 = UInt

/**
 * type TokenIdentifier__7 = text;
 */
typealias TokenIdentifier__7 = String

/**
 * type TokenIdentifier__5 = text;
 */
typealias TokenIdentifier__5 = String

/**
 * type Time__2 = int;
 */
typealias Time__2 = BigInteger

/**
 * type SubAccount = vec nat8;
 */
typealias CFTSubAccount = kotlin.Array<UByte>

/**
 * type Memo = blob;
 */
typealias CFTMemo = ByteArray

/**
 * type Extension__1 = text;
 */
typealias Extension__1 = String

/**
 * type Balance__2 = nat;
 */
typealias Balance__2 = BigInteger

/**
 * type Balance = nat;
 */
typealias CFTBalance = BigInteger

/**
 * type AccountIdentifier__4 = text;
 */
typealias CFTAccountIdentifier__4 = String

/**
 * type AccountIdentifier = text;
 */
typealias CFTAccountIdentifier = String

object ChainFusionToonis {

    /**
     * type User__3 =
     *  variant {
     *    address: AccountIdentifier;
     *    "principal": principal;
     *  };
     */
    sealed class User__3 {
        class address(val address: CFTAccountIdentifier): User__3()
        class principal(val principal: ICPPrincipalApiModel): User__3()

    }

    /**
     * type TransferResponse__1 =
     *  variant {
     *    err:
     *     variant {
     *       CannotNotify: AccountIdentifier;
     *       InsufficientBalance;
     *       InvalidToken: TokenIdentifier__5;
     *       Other: text;
     *       Rejected;
     *       Unauthorized: AccountIdentifier;
     *     };
     *    ok: Balance;
     *  };
     */
    sealed class TransferResponse__1 {
        class err(val err: err): TransferResponse__1()
        class ok(val ok: CFTBalance): TransferResponse__1()
    }

    /**
     * type TransferRequest__1 =
     *  record {
     *    amount: Balance;
     *    from: User__3;
     *    memo: Memo;
     *    notify: bool;
     *    subaccount: opt SubAccount;
     *    to: User__3;
     *    token: TokenIdentifier__5;
     *  };
     */
    class TransferRequest__1(
        val amount: CFTBalance,
        val from: User__3,
        val memo: CFTMemo,
        val notify: Boolean,
        val subaccount: CFTSubAccount?,
        val to: User__3,
        val token: TokenIdentifier__5
    )

    /**
     * type Result__2_2 =
     *  variant {
     *    err: CommonError__2;
     *    ok: Balance__2;
     *  };
     */
    sealed class Result__2_2 {
        class err(val err: CommonError__2): Result__2_2()
        class ok(val ok: Balance__2): Result__2_2()

    }

    /**
     * type Result__2_1 =
     *  variant {
     *    err: CommonError__2;
     *    ok: AccountIdentifier__4;
     *  };
     */
    sealed class Result__2_1 {
        class err(val err: CommonError__2): Result__2_1()
        class ok(val ok: CFTAccountIdentifier__4): Result__2_1()
    }

    /**
     * type Result__2 =
     *  variant {
     *    err: CommonError__2;
     *    ok: Metadata__1;
     *  };
     */
    sealed class Result__2 {
        class err(val err: CommonError__2): Result__2()
        class ok(val ok: Metadata__1): Result__2()
    }

    /**
     * type Result_14 =
     *  variant {
     *    err: CommonError__2;
     *    ok: Balance__2;
     *  };
     */
    sealed class Result_14 {
        class err(val err: CommonError__2): Result_14()
        class ok(val ok: Balance__2): Result_14()
    }

    /**
     * type Result_13 =
     *  variant {
     *    err: CommonError__2;
     *    ok: vec TokenIndex__1;
     *  };
     */
    sealed class Result_13 {
        class err(val err: CommonError__2): Result_13()
        class ok(val ok: kotlin.Array<CFTTokenIndex__1>): Result_13()
    }

    /**
     * type Result_12 =
     *  variant {
     *    err: CommonError__2;
     *    ok: vec record {
     *              TokenIndex__1;
     *              opt Listing__2;
     *              opt blob;
     *            };
     *  };
     */
    sealed class Result_12 {
        class err(val err: CommonError__2): Result_12()
        class ok(val ok: kotlin.Array<okClass>): Result_12()

        class okClass(
            val tokenIndex__1: CFTTokenIndex__1,
            val listing__2: Listing__2?,
            val blobValue: ByteArray?
        )
    }

    /**
     * type Property__2 =
     *  record {
     *    trait_type: text;
     *    value: text;
     *  };
     */
    class Property__2(
        val trait_type: String,
        val value: String
    )

    /**
     * type MintRequest__1 =
     *  record {
     *    metadata: opt blob;
     *    to: User__3;
     *  };
     */
    class MintRequest__1(
        val metadata: ByteArray?,
        val to: User__3
    )

    /**
     * type Metadata__1 =
     *  variant {
     *    fungible:
     *     record {
     *       decimals: nat8;
     *       metadata: opt blob;
     *       name: text;
     *       symbol: text;
     *     };
     *    nonfungible: record {metadata: opt blob;};
     *  };
     */
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

    /**
     * type MetadataStorageType__1 =
     *  variant {
     *    Fleek;
     *    Last;
     *    MetaBox;
     *    S3;
     *  };
     */
    sealed class MetadataStorageType__1 {
        object Fleek: MetadataStorageType__1()
        object Last: MetadataStorageType__1()
        object MetaBox: MetadataStorageType__1()
        object S3: MetadataStorageType__1()
    }

    /**
     * type MetadataStorageInfo__1 =
     *  record {
     *    environmentImageThree: text;
     *    thumb: text;
     *    url: text;
     *  };
     */
    class MetadataStorageInfo__1(
        val environmentImageThree: String,
        val thumb: String,
        val url: String
    )

    /**
     * type Listing__2 =
     *  record {
     *    locked: opt Time__2;
     *    price: nat64;
     *    seller: principal;
     *  };
     */
    class Listing__2(
        val locked: Time__2?,
        val price: ULong,
        val seller: ICPPrincipalApiModel
    )

    /**
     * type HttpResponse__1 =
     *  record {
     *    body: blob;
     *    headers: vec HeaderField;
     *    status_code: nat16;
     *  };
     */
    class HttpResponse__1(
        val body: ByteArray,
        val headers: kotlin.Array<HeaderField>,
        val status_code: UShort
    )

    /**
     * type HttpRequest__1 =
     *  record {
     *    body: blob;
     *    headers: vec HeaderField;
     *    method: text;
     *    url: text;
     *  };
     */
    class HttpRequest__1(
        val body: ByteArray,
        val headers: kotlin.Array<HeaderField>,
        val method: String,
        val url: String
    )

    /**
     * type HeaderField =
     *  record {
     *    text;
     *    text;
     *  };
     */
    class HeaderField(
        val textValue_1: String,
        val textValue_2: String
    )

    /**
     * type CommonError__2 =
     *  variant {
     *    InvalidToken: TokenIdentifier__5;
     *    Other: text;
     *  };
     */
    sealed class CommonError__2 {
        class InvalidToken(val InvalidToken: TokenIdentifier__5): CommonError__2()
        class Other(val Other: String): CommonError__2()
    }

    /**
     * type CommonError__1 =
     *  variant {
     *    InvalidToken: TokenIdentifier__5;
     *    Other: text;
     *  };
     */
    sealed class CommonError__1 {
        class InvalidToken(val InvalidToken: TokenIdentifier__5): CommonError__1()
        class Other(val Other: String): CommonError__1()
    }

    /**
     * type BalanceResponse__1 =
     *  variant {
     *    err: CommonError__1;
     *    ok: Balance;
     *  };
     */
    sealed class BalanceResponse__1 {
        class err(val err: CommonError__1): BalanceResponse__1()
        class ok(val ok: CFTBalance): BalanceResponse__1()
    }

    /**
     * type BalanceRequest__1 =
     *  record {
     *    token: TokenIdentifier__5;
     *    user: User__3;
     *  };
     */
    class BalanceRequest__1(
        val token: TokenIdentifier__5,
        val user: User__3
    )

    /**
     * type ApproveRequest__1 =
     *  record {
     *    allowance: Balance;
     *    spender: principal;
     *    subaccount: opt SubAccount;
     *    token: TokenIdentifier__5;
     *  };
     */
    class ApproveRequest__1(
        val allowance: CFTBalance,
        val spender: ICPPrincipalApiModel,
        val subaccount: CFTSubAccount?,
        val token: TokenIdentifier__5
    )

    /**
     * type AllowanceRequest__1 =
     *  record {
     *    owner: User__3;
     *    spender: principal;
     *    token: TokenIdentifier__5;
     *  };
     */
    class AllowanceRequest__1(
        val owner: User__3,
        val spender: ICPPrincipalApiModel,
        val token: TokenIdentifier__5
    )

    class CFTService(
        private val canister: ICPPrincipal
    ) {
        // getTokens: () -> (vec record { TokenIndex__1; Metadata__1; }) query;
        suspend fun getTokens(): Array<ArrayClass>
        {
            val icpQuery = ICPQuery(
                methodName = "getTokens",
                canister = canister
            )
            val result = icpQuery.query(
                values = listOf()
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        class ArrayClass(
            val tokenIndex__1: CFTTokenIndex__1,
            val metadata__1: Metadata__1
        )

        // getTokensByIds: (vec TokenIndex__1) -> (vec record { TokenIndex__1; Metadata__1; }) query;
        suspend fun getTokensByIds(
            array: kotlin.Array<CFTTokenIndex__1>
        ): kotlin.Array<ArrayClass>
        {
            val icpQuery = ICPQuery(
                methodName = "getTokensByIds",
                canister = canister
            )
            val result = icpQuery.query(
                values = listOf(
                    ValueToEncode(
                        arg = array,
                        arrayType = ArrayClass::class,
                        arrayTypeNullable = false
                    )
                )
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        // tokens_ext: (AccountIdentifier) -> (Result) query;
        suspend fun tokens_ext(
            accountIdentifier: CFTAccountIdentifier__4,
        ): Result_12 {
            val icpQuery = ICPQuery(
                methodName = "tokens_ext",
                canister = canister
            )
            val result = icpQuery.query(
                values = listOf(
                    ValueToEncode(
                        arg = accountIdentifier
                    )
                )
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

    }
}