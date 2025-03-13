package com.bity.icp_kotlin_kit.data.generated_file

import java.math.BigInteger
import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.request.PollingValues

object ICRC1Oracle {

    /**
     * type Category = variant {
     *     Sns;
     *     Known;
     *     Spam;
     *     ChainFusionTestnet;
     *     ChainFusion;
     *     Community;
     *     Native;
     * };
     */
    sealed class Category {
        object Sns: Category()
        object Known: Category()
        object Spam: Category()
        object ChainFusionTestnet: Category()
        object ChainFusion: Category()
        object Community: Category()
        object Native: Category()
    }

    /**
     * type Conf = record {
     *     controllers : opt vec principal;
     *     im_canister : opt principal;
     * };
     */
    class Conf(
        val controllers: kotlin.Array<ICPPrincipalApiModel>?,
        val im_canister: ICPPrincipalApiModel?
    )

    /**
     * type ICRC1 = record {
     *     logo : opt text;
     *     name : text;
     *     ledger : text;
     *     category : Category;
     *     index : opt text;
     *     symbol : text;
     *     decimals : nat8;
     *     fee : nat;
     * };
     */
    class ICRC1(
        val logo: String?,
        val name: String,
        val ledger: String,
        val category: Category,
        val index: String?,
        val symbol: String,
        val decimals: UByte,
        val fee: BigInteger
    )

    /**
     * type ICRC1Request = record {
     *     logo : opt text;
     *     name : text;
     *     ledger : text;
     *     index : opt text;
     *     symbol : text;
     *     decimals : nat8;
     *     fee : nat;
     * };
     */
    class ICRC1Request(
        val logo: String?,
        val name: String,
        val ledger: String,
        val index: String?,
        val symbol: String,
        val decimals: UByte,
        val fee: BigInteger
    )

    class ICRC1OracleCanister(
        private val canister: ICPPrincipal
    ) {
        suspend fun count_icrc1_canisters(): ULong {
            val icpQuery = ICPQuery(
                methodName = "count_icrc1_canisters",
                canister = canister
            )
            val result = icpQuery.query(
                values = listOf()
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_all_icrc1_canisters(): kotlin.Array<ICRC1> {
            val icpQuery = ICPQuery(
                methodName = "get_all_icrc1_canisters",
                canister = canister
            )
            val result = icpQuery.query(
                values = listOf()
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_icrc1_paginated(
            startAt: ULong,
            pageSize: ULong
        ): Array<ICRC1> {
            val icpQuery = ICPQuery(
                methodName = "get_icrc1_paginated",
                canister = canister
            )
            val result = icpQuery.query(
                values = listOf(
                    ValueToEncode(startAt),
                    ValueToEncode(pageSize)
                )
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun replace_icrc1_canisters(
            array: kotlin.Array<ICRC1>,
            sender: ICPSigningPrincipal,
            pollingValues: PollingValues = PollingValues()
        ) {
            val icpQuery = ICPQuery(
                methodName = "replace_icrc1_canisters",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = array,
                        arrayType = ICRC1::class
                    )
                ),
                sender = sender,
                pollingValues = pollingValues
            ).getOrThrow()
        }

        suspend fun set_operator(
            icpPrincipalApiModel: ICPPrincipalApiModel,
            sender: ICPSigningPrincipal,
            pollingValues: PollingValues = PollingValues()
        ) {
            val icpQuery = ICPQuery(
                methodName = "set_operator",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(icpPrincipalApiModel)
                ),
                sender = sender,
                pollingValues = pollingValues
            ).getOrThrow()
        }

        suspend fun store_icrc1_canister(
            request: ICRC1Request,
            sender: ICPSigningPrincipal,
            pollingValues: PollingValues = PollingValues()
        ) {
            val icpQuery = ICPQuery(
                methodName = "store_icrc1_canister",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(request)
                ),
                sender = sender,
                pollingValues = pollingValues
            ).getOrThrow()
        }

        suspend fun store_new_icrc1_canisters(
            array: kotlin.Array<ICRC1>,
            sender: ICPSigningPrincipal,
            pollingValues: PollingValues = PollingValues()
        ) {
            val icpQuery = ICPQuery(
                methodName = "store_new_icrc1_canisters",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = array,
                        arrayType = ICRC1::class
                    )
                ),
                sender = sender,
                pollingValues = pollingValues
            ).getOrThrow()
        }
    }

}