package com.bity.icp_kotlin_kit.domain.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
import com.bity.icp_kotlin_kit.domain.request.PollingValues

/**
 * File generated using ICP Kotlin Kit Plugin
 */

/**
 * type detail_value = variant {
 *     True;
 *     False;
 *     I64       : int64;
 *     U64       : nat64;
 *     Vec       : vec detail_value;
 *     Slice     : vec nat8;
 *     Text      : text;
 *     Float     : float64;
 *     Principal : principal;
 * };
 */
sealed class detail_value {
    data object True : detail_value()
    data object False : detail_value()
    class I64(val long: Long): detail_value()
    class U64(val uLong: ULong): detail_value()
    class Vec(val values: Array<detail_value>): detail_value()
    class Slice(val values: Array<UByte>): detail_value()
    class Text(val string: String): detail_value()
    class Float(val double: Double): detail_value()
    class Principal(val iCPPrincipal: ICPPrincipalApiModel): detail_value()
}

/**
 * type add_token_input = record {
 *     name        : text;
 *     description : text;
 *     thumbnail   : text;
 *     frontend    : opt text;
 *     principal_id : principal;
 *     details     : vec record { text; detail_value }
 * };
 */
class add_token_input(
    val name: String,
    val description: String,
    val thumbnail: String,
    val frontend: String?,
    val principal_id: ICPPrincipalApiModel,
    val details: Array<_Class1>
) {
    class _Class1(
        val string: String,
        val detail_value: detail_value
    )
}

/**
 * type token = record {
 *     name        : text;
 *     description : text;
 *     thumbnail   : text;
 *     frontend    : opt text;
 *     principal_id : principal;
 *     submitter: principal;
 *     last_updated_by: principal;
 *     last_updated_at: nat64;
 *     details     : vec record { text; detail_value }
 * };
 */
class token(
    val name: String,
    val description: String,
    val thumbnail: String,
    val frontend: String?,
    val principal_id: ICPPrincipalApiModel,
    val submitter: ICPPrincipalApiModel,
    val last_updated_by: ICPPrincipalApiModel,
    val last_updated_at: ULong,
    val details: Array<_Class1>
) {
    class _Class1(
        val string: String,
        val detail_value: detail_value
    )
}

/**
 * type operation_error = variant {
 *     NotAuthorized;
 *     NonExistentItem;
 *     BadParameters;
 *     Unknown : text;
 * };
 */
sealed class operation_error {
    data object NotAuthorized : operation_error()
    data object NonExistentItem : operation_error()
    data object BadParameters : operation_error()
    class Unknown(val string: String): operation_error()
}

/**
 * type operation_response = variant {
 *     Ok  : opt text;
 *     Err : operation_error;
 * };
 */
sealed class operation_response {
    class Ok(val string: String?): operation_response()
    class Err(val operation_error: operation_error): operation_response()
}

class TokensService(
    private val canister: ICPPrincipal
) {
    // DRS Methods
    /**
     * "name"   : () -> (text) query;
     */
    suspend fun name (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): String {
        val icpQuery = ICPQuery(
            methodName = "name",
            canister = canister
        )
        val result = icpQuery.invoke(
            values = listOf(),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()
        return CandidDecoder.decodeNotNull(result)
    }

    /**
     * "get"    : (token_id: principal) -> (opt token) query;
     */
    suspend fun get (
        token_id: ICPPrincipalApiModel,
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): token? {
        val icpQuery = ICPQuery(
            methodName = "get",
            canister = canister
        )
        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = token_id,
                    expectedClass = ICPPrincipalApiModel::class,
                    expectedClassNullable = false
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()
        return CandidDecoder.decode(result)
    }

    /**
     * "add"    : (trusted_source: opt principal, token: add_token_input) -> (operation_response);
     */
    suspend fun add (
        trusted_source: ICPPrincipalApiModel?,
        token: add_token_input,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): operation_response {
        val icpQuery = ICPQuery(
            methodName = "add",
            canister = canister
        )
        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = trusted_source,
                    expectedClass = ICPPrincipalApiModel::class,
                    expectedClassNullable = true
                ),
                ValueToEncode(
                    arg = token,
                    expectedClass = add_token_input::class,
                    expectedClassNullable = false
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = ICPRequestCertification.Certified
        ).getOrThrow()
        return CandidDecoder.decodeNotNull(result)
    }

    /**
     * "remove" : (trusted_source: opt principal, token_id: principal) -> (operation_response);
     */
    suspend fun remove (
        trusted_source: ICPPrincipalApiModel?,
        token_id: ICPPrincipalApiModel,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): operation_response {
        val icpQuery = ICPQuery(
            methodName = "remove",
            canister = canister
        )
        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = trusted_source,
                    expectedClass = ICPPrincipalApiModel::class,
                    expectedClassNullable = true
                ),
                ValueToEncode(
                    arg = token_id,
                    expectedClass = ICPPrincipalApiModel::class,
                    expectedClassNullable = false
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = ICPRequestCertification.Certified
        ).getOrThrow()
        return CandidDecoder.decodeNotNull(result)
    }



    // Canister methods
    /**
     * "get_all"  : () -> (vec token) query;
     */
    suspend fun get_all (
        certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): Array<token> {
        val icpQuery = ICPQuery(
            methodName = "get_all",
            canister = canister
        )
        val result = icpQuery.invoke(
            values = listOf(),
            sender = sender,
            pollingValues = pollingValues,
            certification = certification
        ).getOrThrow()
        return CandidDecoder.decodeNotNull(result)
    }

    /**
     * "add_admin" : (admin: principal) -> (operation_response);
     */
    suspend fun add_admin (
        admin: ICPPrincipalApiModel,
        sender: ICPSigningPrincipal? = null,
        pollingValues: PollingValues = PollingValues()
    ): operation_response {
        val icpQuery = ICPQuery(
            methodName = "add_admin",
            canister = canister
        )
        val result = icpQuery.invoke(
            values = listOf(
                ValueToEncode(
                    arg = admin,
                    expectedClass = ICPPrincipalApiModel::class,
                    expectedClassNullable = false
                )
            ),
            sender = sender,
            pollingValues = pollingValues,
            certification = ICPRequestCertification.Certified
        ).getOrThrow()
        return CandidDecoder.decodeNotNull(result)
    }
}
