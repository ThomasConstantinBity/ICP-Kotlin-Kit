package com.bity.icp_kotlin_kit.plugin.candid_parser

import com.bity.icp_kotlin_kit.plugin.candid_parser.model.IDLTypeDeclaration
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLType
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeBlob
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeCustom
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeFunc
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeInt
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeNat
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeNat64
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeRecord
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeText
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeVariant
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeVec
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeVecRecord
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class CandidTypeParserTest {

    @ParameterizedTest
    @MethodSource("typeDeclaration")
    fun `parse types` (
        input: String,
        expectedResult: IDLType
    ) {
        val typeDeclaration = CandidTypeParser.parseType(input)
        assertEquals(expectedResult, typeDeclaration.type)
        assertEquals(emptyList(), typeDeclaration.comments)
    }

    @ParameterizedTest
    @MethodSource("typeDeclarationWithComments")
    fun `parse types with comments` (
        input: String,
        expectedResult: IDLTypeDeclaration
    ) {
        val typeDeclaration = CandidTypeParser.parseType(input)
        assertEquals(expectedResult.type, typeDeclaration.type)
        assertEquals(expectedResult.comments, typeDeclaration.comments)
    }

    companion object {

        @JvmStatic
        private fun typeDeclaration() = listOf(
            Arguments.of(
                "type AccountIdentifier = blob;",
                IDLTypeBlob("AccountIdentifier")
            ),

            Arguments.of(
                "type AccountIdentifier = opt blob;",
                IDLTypeBlob("AccountIdentifier", true)
            ),

            Arguments.of(
                "type Memo = nat64;",
                IDLTypeNat64("Memo")
            ),

            Arguments.of(
                "type QueryArchiveFn = func (GetBlocksArgs) -> (QueryArchiveResult) query;",
                IDLTypeFunc(
                    typeId = "QueryArchiveFn",
                    inputParams = listOf("GetBlocksArgs"),
                    outputParams = listOf("QueryArchiveResult"),
                    funcType = "query"
                )
            ),

            Arguments.of(
                "type QueryArchiveFn = func (GetBlocksArgs) -> (QueryArchiveResult);",
                IDLTypeFunc(
                    typeId = "QueryArchiveFn",
                    inputParams = listOf("GetBlocksArgs"),
                    outputParams = listOf("QueryArchiveResult"),
                    funcType = null
                )
            ),

            Arguments.of(
                """
                    type Tokens = record {
                        e8s : nat64;
                    };
                """.trimIndent(),
                IDLTypeRecord(
                    typeId = "Tokens",
                    records = listOf(
                        IDLTypeNat64("e8s")
                    )
                )
            ),

            Arguments.of(
                """
                    type Transfer = variant {
                        Mint: record {
                            to: AccountIdentifier;
                            amount: Tokens;
                        };
                        Burn: record {
                             from: AccountIdentifier;
                             amount: Tokens;
                       };
                        Send: record {
                            from: AccountIdentifier;
                            to: AccountIdentifier;
                            amount: Tokens;
                        };
                    };
                """.trimIndent(),
                IDLTypeVariant(
                    typeId = "Transfer",
                    types = listOf(
                        IDLTypeRecord(
                            typeId =  "Mint",
                            records = listOf(
                                IDLTypeCustom(
                                    typeId = "to",
                                    typeDef = "AccountIdentifier"
                                ),
                                IDLTypeCustom(
                                    typeId = "amount",
                                    typeDef = "Tokens"
                                ),
                            )
                        ),
                        IDLTypeRecord(
                            typeId =  "Burn",
                            records = listOf(
                                IDLTypeCustom(
                                    typeId = "from",
                                    typeDef = "AccountIdentifier"
                                ),
                                IDLTypeCustom(
                                    typeId = "amount",
                                    typeDef = "Tokens"
                                ),
                            )
                        ),
                        IDLTypeRecord(
                            typeId =  "Send",
                            records = listOf(
                                IDLTypeCustom(
                                    typeId = "from",
                                    typeDef = "AccountIdentifier"
                                ),
                                IDLTypeCustom(
                                    typeId = "to",
                                    typeDef = "AccountIdentifier"
                                ),
                                IDLTypeCustom(
                                    typeId = "amount",
                                    typeDef = "Tokens"
                                ),
                            )
                        )
                    )
                )
            )
        )

        @JvmStatic
        private fun typeDeclarationWithComments() = listOf(
            Arguments.of(
                """
                    // This is a simple comment
                    type Subaccount = blob;
                """.trimIndent(),
                IDLTypeDeclaration(
                    comments = listOf("This is a simple comment"),
                    type = IDLTypeBlob("Subaccount")
                )
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
                IDLTypeDeclaration(
                    comments = listOf("Generic value in accordance with ICRC-3"),
                    type = IDLTypeVariant(
                        typeId = "Value",
                        types = listOf(
                            IDLTypeBlob("Blob"),
                            IDLTypeText("Text"),
                            IDLTypeNat("Nat"),
                            IDLTypeInt("Int"),
                            IDLTypeVec(
                                typeId = "Array",
                                value = "Value"
                            ),
                            IDLTypeVecRecord(
                                typeId = "Map",
                                value = "{ text; Value }"
                            )
                        )
                    )
                )
            )
        )
    }
}