package com.bity.icp_kotlin_kit.plugin.candid_parser

import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_comment.IDLSingleLineComment
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLRecord
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeNat
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeNat64
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeRecord
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeVec
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.typeOf
import kotlin.test.assertEquals

internal class CandidRecordParserTest {

    @ParameterizedTest(name = "[{index}] - {0}")
    @MethodSource("singleRecordValueWithoutId")
    fun `record with single value no id` (
        input: String,
        expectedResult: IDLRecord
    ) {
        val typeDeclaration = CandidRecordParser.parseRecord(input)
        assertEquals(expectedResult, typeDeclaration)
    }

    @ParameterizedTest(name = "[{index}] - {0}")
    @MethodSource("singleRecordValueWithId")
    fun `record with single value and id` (
        input: String,
        expectedResult: IDLRecord
    ) {
        val typeDeclaration = CandidRecordParser.parseRecord(input)
        assertEquals(expectedResult, typeDeclaration)
    }

    // TODO
    @Disabled
    @ParameterizedTest(name = "[{index}] - {0}")
    @MethodSource("multipleRecordsValue")
    fun `record with multiple values` (
        input: String,
        expectedResult: IDLRecord
    ) {
        val typeDeclaration = CandidRecordParser.parseRecord(input)
        assertEquals(expectedResult, typeDeclaration)
    }

    companion object {

        @JvmStatic
        private fun singleRecordValueWithoutId() = listOf(
            Arguments.of(
                "record { nat64; }",
                IDLRecord(
                    types = listOf(
                        IDLTypeNat64()
                    )
                )
            ),

            Arguments.of(
                "record { nat64; } // record comment",
                IDLRecord(
                    comment = IDLSingleLineComment(listOf("record comment")),
                    types = listOf(
                        IDLTypeNat64()
                    )
                )
            ),

            Arguments.of(
                "record { nat64; record { nat64; nat; }} // record comment",
                IDLRecord(
                    comment = IDLSingleLineComment(listOf("record comment")),
                    types = listOf(
                        IDLTypeNat64(),
                        IDLRecord(
                            types = listOf(
                                IDLTypeNat64(),
                                IDLTypeNat()
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                "record { nat64; record {record { nat64; nat; }}} // record comment",
                IDLRecord(
                    comment = IDLSingleLineComment(listOf("record comment")),
                    types = listOf(
                        IDLTypeNat64(),
                        IDLRecord(
                            types = listOf(
                                IDLRecord(
                                    types = listOf(
                                        IDLTypeNat64(),
                                        IDLTypeNat()
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )

        @JvmStatic
        private fun singleRecordValueWithId() = listOf(

            Arguments.of(
                "record { e8s : nat64; }",
                IDLRecord(
                    types = listOf(
                        IDLTypeNat64(
                            id = "e8s",
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    record { 
                        // Comment to describe value
                        e8s : nat64; 
                    }
                """.trimIndent(),
                IDLRecord(
                    types = listOf(
                        IDLTypeNat64(
                            comment = IDLSingleLineComment(listOf("Comment to describe value")),
                            id = "e8s",
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    record {
                        timestamp: nat64; // timestamp comment
                        // time comment
                        time: nat64;
                    }
                """.trimIndent(),
                IDLRecord(
                    types = listOf(
                        IDLTypeNat64(
                            id = "timestamp",
                            comment = IDLSingleLineComment(listOf("timestamp comment"))
                        ),
                        IDLTypeNat64(
                            id = "time",
                            comment = IDLSingleLineComment(listOf("time comment"))
                        )
                    )
                )
            )

            /*Arguments.of(
                """
                    record { 
                        // Comment to describe value
                        // on multiple
                        // lines
                        e8s : nat64; 
                    }
                """.trimIndent(),
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            id = "e8s",
                            type = IDLTypeNat64(),
                            comment = IDLSingleLineComment(listOf("Comment to describe value", "on multiple", "lines"))
                        )
                    )
                )
            ),*/

            /*Arguments.of(
                """
                    record {
                        e8s : nat64; // Comment to describe value
                    }
                """.trimIndent(),
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            id = "e8s",
                            type = IDLTypeNat64(),
                            comment = IDLSingleLineComment(listOf("Comment to describe value"))
                        )
                    )
                )
            ),*/

            /*Arguments.of(
                """
                    record {
                        e8s : nat64; //         Comment with multiple spaces
                    }
                """.trimIndent(),
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            id = "e8s",
                            type = IDLTypeNat64(),
                            comment = IDLSingleLineComment(listOf("Comment with multiple spaces"))
                        )
                    )
                )
            ),*/

            /*Arguments.of(
                """
                    record {
                        // A prefix of the requested block range.
                        // The index of the first block is equal to [GetBlocksArgs.from].
                        //
                        // Note that the number of blocks might be less than the requested
                        // [GetBlocksArgs.len] for various reasons, for example:
                        //
                        // 1. The query might have hit the replica with an outdated state
                        //    that doesn't have the full block range yet.
                        // 2. The requested range is too large to fit into a single reply.
                        //
                        // NOTE: the list of blocks can be empty if:
                        // 1. [GetBlocksArgs.len] was zero.
                        // 2. [GetBlocksArgs.from] was larger than the last block known to the canister.
                        blocks : vec Block;
                    }
                """.trimIndent(),
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            comment = IDLSingleLineComment(
                                listOf(
                                    "A prefix of the requested block range.",
                                    "The index of the first block is equal to [GetBlocksArgs.from].",
                                    "",
                                    "Note that the number of blocks might be less than the requested",
                                    "[GetBlocksArgs.len] for various reasons, for example:",
                                    "",
                                    "1. The query might have hit the replica with an outdated state",
                                    "that doesn't have the full block range yet.",
                                    "2. The requested range is too large to fit into a single reply.",
                                    "",
                                    "NOTE: the list of blocks can be empty if:",
                                    "1. [GetBlocksArgs.len] was zero.",
                                    "2. [GetBlocksArgs.from] was larger than the last block known to the canister."
                                )
                            ),
                            id = "blocks",
                            type = IDLTypeVec(
                                vecDeclaration = "vec Block"
                            )
                        )
                    )
                )
            )*/
        )

        @JvmStatic
        private fun multipleRecordsValue() = listOf(
            Arguments.of()

            /*Arguments.of(
                "record { nat; opt record { text; Value } }",
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            type = IDLTypeNat()
                        ),
                        IDLRecord(
                            isOptional = true,
                            type = IDLTypeRecord(
                                recordDeclaration = "record { text; Value }"
                            )
                        )
                    )
                )
            ),*/

            /*Arguments.of(
                "record { owner : principal; subaccount : opt Subaccount }",
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            id = "owner",
                            type = IDLTypePrincipal()
                        ),
                        IDLRecord(
                            id = "subaccount",
                            isOptional = true,
                            type = IDLTypeCustom("Subaccount")
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    record {
                        providerId : nat64;
                        chainId: opt nat64;
                        "service": opt RpcService;
                        primary : opt bool;
                    }
                """.trimIndent(),
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            id = "providerId",
                            type = IDLTypeNat64()
                        ),
                        IDLRecord(
                            id = "chainId",
                            isOptional = true,
                            type = IDLTypeNat64()
                        ),
                        IDLRecord(
                            id = "\"service\"",
                            isOptional = true,
                            type = IDLTypeCustom("RpcService")
                        ),
                        IDLRecord(
                            id = "primary",
                            isOptional = true,
                            type = IDLTypeBoolean()
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    record {
                        from_subaccount: opt blob; // The subaccount to transfer the token from
                        to : Account;
                        token_id : nat; // token_id comment
                        memo : opt blob;
                        created_at_time : opt nat64; // timestamp
                    }
                """.trimIndent(),
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("The subaccount to transfer the token from")),
                            id = "from_subaccount",
                            isOptional = true,
                            type = IDLTypeBlob()
                        ),
                        IDLRecord(
                            id = "to",
                            type = IDLTypeCustom("Account")
                        ),
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("token_id comment")),
                            id = "token_id",
                            type = IDLTypeNat()
                        ),
                        IDLRecord(
                            isOptional = true,
                            id = "memo",
                            type = IDLTypeBlob()
                        ),
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("timestamp")),
                            isOptional = true,
                            id = "created_at_time",
                            type = IDLTypeNat64()
                        )
                    )
                )
            ),*/

            /*Arguments.of(
                """
                    record {
                        // Transaction memo.
                        // See comments for the `Memo` type.
                        memo: Memo;
                        // The amount that the caller wants to transfer to the destination address.
                        amount: Tokens;
                        // The amount that the caller pays for the transaction.
                        // Must be 10000 e8s.
                        fee: Tokens;
                        // The subaccount from which the caller wants to transfer funds.
                        // If null, the ledger uses the default (all zeros) subaccount to compute the source address.
                        // See comments for the `SubAccount` type.
                        from_subaccount: opt SubAccount;
                        // The destination account.
                        // If the transfer is successful, the balance of this address increases by `amount`.
                        to: AccountIdentifier;
                        // The point in time when the caller created this request.
                        // If null, the ledger uses current ICP time as the timestamp.
                        created_at_time: opt TimeStamp;
                    }
                """.trimIndent(),
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("Transaction memo.", "See comments for the `Memo` type.")),
                            id = "memo",
                            type = IDLTypeCustom("Memo")
                        ),
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("The amount that the caller wants to transfer to the destination address.")),
                            id = "amount",
                            type = IDLTypeCustom("Tokens")
                        ),
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("The amount that the caller pays for the transaction.", "Must be 10000 e8s.")),
                            id = "fee",
                            type = IDLTypeCustom("Tokens")
                        ),
                        IDLRecord(
                            comment = IDLSingleLineComment(
                                listOf(
                                    "The subaccount from which the caller wants to transfer funds.",
                                    "If null, the ledger uses the default (all zeros) subaccount to compute the source address.",
                                    "See comments for the `SubAccount` type."
                                )
                            ),
                            id = "from_subaccount",
                            isOptional = true,
                            type = IDLTypeCustom("SubAccount")
                        ),
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("The destination account.", "If the transfer is successful, the balance of this address increases by `amount`.")),
                            id = "to",
                            type = IDLTypeCustom("AccountIdentifier")
                        ),
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("The point in time when the caller created this request.", "If null, the ledger uses current ICP time as the timestamp.")),
                            id = "created_at_time",
                            isOptional = true,
                            type = IDLTypeCustom("TimeStamp")
                        )
                    )
                )
            ),*/

            /*Arguments.of(
                """
                    record {
                        from_subaccount: opt blob; // The subaccount to transfer the token from
                        to : Account;
                        token_id : nat;
                        memo : opt blob;
                        created_at_time : opt nat64;
                    }
                """.trimIndent(),
                IDLRecordDeclaration(
                    records = listOf(
                        IDLRecord(
                            comment = IDLSingleLineComment(listOf("The subaccount to transfer the token from")),
                            id = "from_subaccount",
                            isOptional = true,
                            type = IDLTypeBlob()
                        ),
                        IDLRecord(
                            id = "to",
                            type = IDLTypeCustom("Account")
                        ),
                        IDLRecord(
                            id = "token_id",
                            type = IDLTypeNat()
                        ),
                        IDLRecord(
                            isOptional = true,
                            id = "memo",
                            type = IDLTypeBlob()
                        ),
                        IDLRecord(
                            isOptional = true,
                            id = "created_at_time",
                            type = IDLTypeNat64()
                        )
                    )
                )
            )*/
        )
    }
}