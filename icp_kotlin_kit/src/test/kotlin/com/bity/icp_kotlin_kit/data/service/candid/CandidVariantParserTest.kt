package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeVariant
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CandidVariantParserTest {

    private val candidTypeParserService = CandidTypeParserServiceImpl()

    @ParameterizedTest(name = "{index}")
    @MethodSource("candidVariant")
    fun variantDefinition(
        typeDefinition: String,
        expectedGeneratedClass: String
    ) {
        val candidTypeDefinition = candidTypeParserService
            .parseCandidType(typeDefinition) as? CandidTypeVariant
        assertNotNull(candidTypeDefinition)
        assertFalse(candidTypeDefinition.isTypeAlias)
        val kotlinDefinition = candidTypeDefinition.getClassDefinition()
        assertEquals(
            expected = expectedGeneratedClass
                .replace("""\s+|\t+""".toRegex(), " ")
                .trim(),
            actual = kotlinDefinition
                .replace("""\s+|\t+""".toRegex(), " ")
                .trim()
        )
    }

    companion object {

        @JvmStatic
        private fun candidVariant() = listOf(

            Arguments.of(
                """
                    type Account = variant {
                      account_id : text;
                      "principal" : principal;
                      extensible : CandyShared;
                      account : record { owner : principal; sub_account : opt vec nat8 };
                    };
                """.trimIndent(),
                """
                    sealed class Account {
                        class account_id(val account_id: String): Account()
                        class principal(val principal: ICPPrincipalApiModel): Account()
                        class extensible(val extensible: CandyShared): Account()
                        class account(
                            val owner: ICPPrincipalApiModel,
                            val sub_account: kotlin.Array<UByte>?
                        ): Account()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type TransferError = variant {
                        NonExistingTokenId;
                        InvalidRecipient;
                        Unauthorized;
                        TooOld;
                        CreatedInFuture : record { ledger_time: nat64 };
                        Duplicate : record { duplicate_of : nat };
                        GenericError : record { error_code : nat; message : text };
                        GenericBatchError : record { error_code : nat; message : text };
                    };
                """.trimIndent(),
                """
                    sealed class TransferError {
                        object NonExistingTokenId: TransferError()
                        object InvalidRecipient: TransferError()
                        object Unauthorized: TransferError()
                        object TooOld: TransferError()
                        class CreatedInFuture(
                            val ledger_time: ULong
                        ): TransferError()
                        class Duplicate(
                            val duplicate_of: BigInteger
                        ): TransferError()
                        class GenericError(
                            val error_code: BigInteger,
                            val message: String
                        ): TransferError()
                        class GenericBatchError(
                            val error_code: BigInteger,
                            val message: String
                        ): TransferError()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type detail_value = variant {
                        True;
                        False;
                        I64       : int64;
                        U64       : nat64;
                        Vec       : vec detail_value;
                        Slice     : vec nat8;
                        Text      : text;
                        Float     : float64;
                        Principal : principal;
                    };
                """.trimIndent(),
                """
                    sealed class detail_value {
                        object True: detail_value()
                        object False: detail_value()
                        class I64(val i64: Long): detail_value()
                        class U64(val u64: ULong): detail_value()
                        class Vec(val vec: Array<detail_value>): detail_value()
                        class Slice(val slice: Array<UByte>): detail_value()
                        class Text(val text: String): detail_value()
                        class Float(val float: Double): detail_value()
                        class Principal(val principal: ICPPrincipalApiModel): detail_value()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type operation_error = variant {
                        NotAuthorized;
                        NonExistentItem;
                        BadParameters;
                        Unknown : text;
                    };
                """.trimIndent(),
                """
                    sealed class operation_error {
                        object NotAuthorized: operation_error()
                        object NonExistentItem: operation_error()
                        object BadParameters: operation_error()
                        class Unknown(val unknown: String): operation_error()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    type operation_response = variant {
                        Ok  : opt text;
                        Err : operation_error;
                    };
                """.trimIndent(),
                """
                    sealed class operation_response {
                        class Ok(val ok: String?): operation_response()
                        class Err(val err: operation_error): operation_response()
                    }
                """.trimIndent()
            ),

            Arguments.of(
                """
                    // Generic value in accordance with ICRC-3
                    type Value = variant {
                        Blob : blob;
                        Text : text;
                        Nat : nat;
                        Int : int;
                        Array : vec Value;
                        Map : vec record { text; Value };
                    };
                """.trimIndent(),
                """
                    sealed class Value {
                        class Blob(val blob: ByteArray): Value()
                        class Text(val text: String): Value()
                        class Nat(val nat: BigInteger): Value()
                        class Int(val int: BigInteger): Value()
                        class Array(val array: kotlin.Array<Value>): Value()
                        class Map(val map: Array<MapClass>): Value()
                        
                        class MapClass(
                            val textValue: String,
                            val value: Value
                        )
                    }
                """.trimIndent()
            )
        )

    }

}