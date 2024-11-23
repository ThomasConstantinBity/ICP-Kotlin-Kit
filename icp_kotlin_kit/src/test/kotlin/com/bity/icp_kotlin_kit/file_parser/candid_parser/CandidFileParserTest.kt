package com.bity.icp_kotlin_kit.file_parser.candid_parser

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLSingleLineComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.IDLFileDeclaration
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_fun.FunType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLFun
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLRecord
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeBlob
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeCustom
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeFloat64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeInt64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat8
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNull
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypePrincipal
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeText
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeVariant
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeVec
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class CandidFileParserTest {

    @Test
    fun `parse mixed`() {
        val input = """
            type EXTTokensResponse = record {
              nat32;
              opt record { locked : opt int; seller : principal; price : nat64 };
              opt vec nat8;
            };
        """.trimIndent()
        val fileDeclaration = CandidFileParser.parseFile(input)
    }

    @MethodSource("func")
    @ParameterizedTest
    fun `parse func`(
        input: String,
        expectedResult: IDLFileDeclaration
    ) {
        val fileDeclaration = CandidFileParser.parseFile(input)
        assertEquals(expectedResult, fileDeclaration)
    }

    @MethodSource("vec")
    @ParameterizedTest
    fun `parse vec`(
        input: String,
        expectedResult: IDLFileDeclaration
    ) {
        val fileDeclaration = CandidFileParser.parseFile(input)
        assertEquals(expectedResult, fileDeclaration)
    }

    @MethodSource("vecRecord")
    @ParameterizedTest
    fun `parse vec record`(
        input: String,
        expectedResult: IDLFileDeclaration
    ) {
        val fileDeclaration = CandidFileParser.parseFile(input)
        assertEquals(expectedResult, fileDeclaration)
    }

    @Disabled("Need to support different type of comment")
    @MethodSource("record")
    @ParameterizedTest
    fun `parse record`(
        input: String,
        expectedResult: IDLFileDeclaration
    ) {
        val fileDeclaration = CandidFileParser.parseFile(input)
        assertEquals(expectedResult, fileDeclaration)
    }

    @MethodSource("typeAlias")
    @ParameterizedTest
    fun `type alias`(
        input: String,
        expectedResult: IDLFileDeclaration
    ) {
        val fileDeclaration = CandidFileParser.parseFile(input)
        assertEquals(expectedResult, fileDeclaration)
    }

    @MethodSource("variant")
    @ParameterizedTest
    fun `parse variant`(
        input: String,
        expectedResult: IDLFileDeclaration
    ) {
        val fileDeclaration = CandidFileParser.parseFile(input)
        assertEquals(expectedResult, fileDeclaration)
    }

    @MethodSource("service")
    @ParameterizedTest
    fun `parse service`(
        input: String,
        expectedResult: IDLFileDeclaration
    ) {
        val fileDeclaration = CandidFileParser.parseFile(input)
        assertEquals(expectedResult, fileDeclaration)
    }

    companion object {

        @JvmStatic
        private fun vec() = listOf(
            Arguments.of(
                "type Ledger = vec Block;",
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeVec(
                            vecDeclaration = "Ledger",
                            vecType = IDLTypeCustom(
                                typeDef = "Block"
                            )
                        )
                    )
                )
            )
        )

        @JvmStatic
        private fun vecRecord() = listOf(
            Arguments.of(
                "type Map = vec record { text; Value };",
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeVec(
                            vecDeclaration = "Map",
                            vecType = IDLRecord(
                                types = listOf(
                                    IDLTypeText(),
                                    IDLTypeCustom(
                                        typeDef = "Value"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )

        @JvmStatic
        private fun record() = listOf(
            Arguments.of(
                """
                    type Tokens = record {
                        e8s : nat64;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "Tokens",
                            types = listOf(
                                IDLTypeNat64(
                                    id = "e8s"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type TimeStamp = record {
                        timestamp_nanos: nat64;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "TimeStamp",
                            types = listOf(
                                IDLTypeNat64(
                                    id = "timestamp_nanos"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type Transaction = record {
                        operation: opt Transfer;
                        memo: Memo;
                        created_at_time: TimeStamp;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "Transaction",
                            types = listOf(
                                IDLTypeCustom(
                                    id = "operation",
                                    isOptional = true,
                                    typeDef = "Transfer"
                                ),
                                IDLTypeCustom(
                                    id = "memo",
                                    typeDef = "Memo"
                                ),
                                IDLTypeCustom(
                                    id = "created_at_time",
                                    typeDef = "TimeStamp"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type Block = record {
                        parent_hash: opt Hash;
                        transaction: Transaction;
                        timestamp: TimeStamp;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "Block",
                            types = listOf(
                                IDLTypeCustom(
                                    id = "parent_hash",
                                    isOptional = true,
                                    typeDef = "Hash"
                                ),
                                IDLTypeCustom(
                                    id = "transaction",
                                    typeDef = "Transaction"
                                ),
                                IDLTypeCustom(
                                    id = "timestamp",
                                    typeDef = "TimeStamp"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type TransferArgs = record {
                        // Transaction memo.
                        // See comments for the `Memo` type.
                        memo: Memo;
                        // The amount that the caller wants to transfer to the destination address.
                        amount: Tokens;
                        // The amount that the caller pays for the transaction.
                        // Must be 10000 e8s.
                        fee: Tokens;
                        from_subaccount: opt SubAccount;
                        to: AccountIdentifier;
                        created_at_time: opt TimeStamp;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "TransferArgs",
                            types = listOf(
                                IDLTypeCustom(
                                    comment = IDLSingleLineComment(
                                        listOf("Transaction memo.",
                                            "See comments for the `Memo` type."
                                        )
                                    ),
                                    id = "memo",
                                    typeDef = "Memo"
                                ),
                                IDLTypeCustom(
                                    comment = IDLSingleLineComment(
                                        listOf("The amount that the caller wants to transfer to the destination address.")
                                    ),
                                    id = "amount",
                                    typeDef = "Tokens"
                                ),
                                IDLTypeCustom(
                                    comment = IDLSingleLineComment(
                                        listOf("The amount that the caller pays for the transaction.", "Must be 10000 e8s.")
                                    ),
                                    id = "fee",
                                    typeDef = "Tokens"
                                ),
                                IDLTypeCustom(
                                    id = "from_subaccount",
                                    isOptional = true,
                                    typeDef = "SubAccount"
                                ),
                                IDLTypeCustom(
                                    id = "to",
                                    typeDef = "AccountIdentifier"
                                ),
                                IDLTypeCustom(
                                    id = "created_at_time",
                                    isOptional = true,
                                    typeDef = "TimeStamp"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type GetBlocksArgs = record {
                        // The index of the first block to fetch.
                        start : BlockIndex;
                        // Max number of blocks to fetch.
                        length : nat64;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "GetBlocksArgs",
                            types = listOf(
                                IDLTypeCustom(
                                    comment = IDLSingleLineComment(listOf("The index of the first block to fetch.")),
                                    id = "start",
                                    typeDef = "BlockIndex"
                                ),
                                IDLTypeNat64(
                                    comment = IDLSingleLineComment(listOf("Max number of blocks to fetch.")),
                                    id = "length"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type BlockRange = record {
                        blocks : vec Block;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "BlockRange",
                            types = listOf(
                                IDLTypeVec(
                                    id = "blocks",
                                    vecType = IDLTypeCustom(
                                        typeDef = "Block"
                                    )
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type QueryBlocksResponse = record {
                        chain_length : nat64;
                        certificate : opt blob;
                        blocks : vec Block;
                        first_block_index : BlockIndex;
                        archived_blocks : vec record {
                            start : BlockIndex;
                            length : nat64;
                            callback : QueryArchiveFn;
                        };
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "QueryBlocksResponse",
                            types = listOf(
                                IDLTypeNat64(
                                    id = "chain_length"
                                ),
                                IDLTypeBlob(
                                    id = "certificate",
                                    isOptional = true
                                ),
                                IDLTypeVec(
                                    id = "blocks",
                                    vecType = IDLTypeCustom(
                                        typeDef = "Block"
                                    )
                                ),
                                IDLTypeCustom(
                                    id = "first_block_index",
                                    typeDef = "BlockIndex"
                                ),
                                IDLTypeVec(
                                    id = "archived_blocks",
                                    vecType = IDLRecord(
                                        types = listOf(
                                            IDLTypeCustom(
                                                id = "start",
                                                typeDef = "BlockIndex"
                                            ),
                                            IDLTypeNat64(
                                                id = "length"
                                            ),
                                            IDLTypeCustom(
                                                id = "callback",
                                                typeDef = "QueryArchiveFn"
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type Archive = record {
                        canister_id: principal;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "Archive",
                            types = listOf(
                                IDLTypePrincipal(
                                    id = "canister_id"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type Archives = record {
                        archives: vec Archive;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "Archives",
                            types = listOf(
                                IDLTypeVec(
                                    id = "archives",
                                    vecType = IDLTypeCustom(
                                        typeDef = "Archive"
                                    )
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type AccountBalanceArgs = record {
                        account: AccountIdentifier;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "AccountBalanceArgs",
                            types = listOf(
                                IDLTypeCustom(
                                    id = "account",
                                    typeDef = "AccountIdentifier"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type TransferArg = record {
                        from_subaccount: opt blob; // The subaccount to transfer the token from
                        to : Account;
                        token_id : nat;
                        memo : opt blob;
                        created_at_time : opt nat64;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "TransferArg",
                            types = listOf(
                                IDLTypeBlob(
                                    comment = IDLSingleLineComment(
                                        listOf("The subaccount to transfer the token from")
                                    ),
                                    id = "from_subaccount",
                                    isOptional = true
                                ),
                                IDLTypeCustom(
                                    id = "to",
                                    typeDef = "Account"
                                ),
                                IDLTypeNat(
                                    id = "token_id"
                                ),
                                IDLTypeBlob(
                                    id = "memo",
                                    isOptional = true
                                ),
                                IDLTypeNat64(
                                    id = "created_at_time",
                                    isOptional = true
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type add_token_input = record {
                        name        : text;
                        description : text;
                        thumbnail   : text;
                        frontend    : opt text;
                        principal_id : principal;
                        details     : vec record { text; detail_value }
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLRecord(
                            recordName = "add_token_input",
                            types = listOf(
                                IDLTypeText(
                                    id = "name"
                                ),
                                IDLTypeText(
                                    id = "description"
                                ),
                                IDLTypeText(
                                    id = "thumbnail"
                                ),
                                IDLTypeText(
                                    id = "frontend",
                                    isOptional = true
                                ),
                                IDLTypePrincipal(
                                    id = "principal_id"
                                ),
                                IDLTypeVec(
                                    id = "details",
                                    vecType = IDLRecord(
                                        types = listOf(
                                            IDLTypeText(),
                                            IDLTypeCustom(
                                                typeDef = "detail_value"
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )

        @JvmStatic
        private fun typeAlias() = listOf(
            Arguments.of(
                "type AccountIdentifier = blob;",
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeCustom(
                            typeDef = "AccountIdentifier",
                            type = IDLTypeBlob()
                        )
                    )
                )
            ),

            Arguments.of(
                "type Memo = nat64;",
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeCustom(
                            typeDef = "Memo",
                            type = IDLTypeNat64()
                        )
                    )
                )
            ),

            Arguments.of(
                "type SubAccount = blob;",
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeCustom(
                            typeDef = "SubAccount",
                            type = IDLTypeBlob()
                        )
                    )
                )
            ),

            Arguments.of(
                "type Hash = blob;",
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeCustom(
                            typeDef = "Hash",
                            type = IDLTypeBlob()
                        )
                    )
                )
            ),

            Arguments.of(
                "type BlockIndex = nat64;",
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeCustom(
                            typeDef = "BlockIndex",
                            type = IDLTypeNat64()
                        )
                    )
                )
            )
        )

        @JvmStatic
        private fun variant() = listOf(
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
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeVariant(
                            variantDeclaration = "Transfer",
                            types = listOf(
                                IDLRecord(
                                    recordName = "Mint",
                                    types = listOf(
                                        IDLTypeCustom(
                                            id = "to",
                                            typeDef = "AccountIdentifier"
                                        ),
                                        IDLTypeCustom(
                                            id = "amount",
                                            typeDef = "Tokens"
                                        )
                                    )
                                ),
                                IDLRecord(
                                    recordName = "Burn",
                                    types = listOf(
                                        IDLTypeCustom(
                                            id = "from",
                                            typeDef = "AccountIdentifier"
                                        ),
                                        IDLTypeCustom(
                                            id = "amount",
                                            typeDef = "Tokens"
                                        )
                                    )
                                ),
                                IDLRecord(
                                    recordName = "Send",
                                    types = listOf(
                                        IDLTypeCustom(
                                            id = "from",
                                            typeDef = "AccountIdentifier"
                                        ),
                                        IDLTypeCustom(
                                            id = "to",
                                            typeDef = "AccountIdentifier"
                                        ),
                                        IDLTypeCustom(
                                            id = "amount",
                                            typeDef = "Tokens"
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type TransferError = variant {
                        // The fee that the caller specified in the transfer request was not the one that ledger expects.
                        // The caller can change the transfer fee to the `expected_fee` and retry the request.
                        BadFee : record { expected_fee : Tokens; };
                        // The account specified by the caller doesn't have enough funds.
                        InsufficientFunds : record { balance: Tokens; };
                        TxTooOld : record { allowed_window_nanos: nat64 };
                        TxCreatedInFuture : null;
                        TxDuplicate : record { duplicate_of: BlockIndex; }
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeVariant(
                            variantDeclaration = "TransferError",
                            types = listOf(
                                IDLRecord(
                                    comment = IDLSingleLineComment(
                                        listOf(
                                            "The fee that the caller specified in the transfer request was not the one that ledger expects.",
                                            "The caller can change the transfer fee to the `expected_fee` and retry the request."
                                        )
                                    ),
                                    recordName = "BadFee",
                                    types = listOf(
                                        IDLTypeCustom(
                                            id = "expected_fee",
                                            typeDef = "Tokens"
                                        )
                                    )
                                ),
                                IDLRecord(
                                    comment = IDLSingleLineComment(
                                        listOf("The account specified by the caller doesn't have enough funds.")
                                    ),
                                    recordName = "InsufficientFunds",
                                    types = listOf(
                                        IDLTypeCustom(
                                            id = "balance",
                                            typeDef = "Tokens"
                                        )
                                    )
                                ),
                                IDLRecord(
                                    recordName = "TxTooOld",
                                    types = listOf(
                                        IDLTypeNat64(
                                            id = "allowed_window_nanos"
                                        )
                                    )
                                ),
                                IDLTypeNull(
                                    nullDefinition = "TxCreatedInFuture"
                                ),
                                IDLRecord(
                                    recordName = "TxDuplicate",
                                    types = listOf(
                                        IDLTypeCustom(
                                            id = "duplicate_of",
                                            typeDef = "BlockIndex"
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type TransferResult = variant {
                        Ok : BlockIndex;
                        Err : TransferError;
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeVariant(
                            variantDeclaration = "TransferResult",
                            types = listOf(
                                IDLTypeCustom(
                                    id = "Ok",
                                    typeDef = "BlockIndex"
                                ),
                                IDLTypeCustom(
                                    id = "Err",
                                    typeDef = "TransferError"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    type QueryArchiveResult = variant {
                        Ok : BlockRange;
                        Err : null;      // we don't know the values here...
                    };
                """.trimIndent(),
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeVariant(
                            variantDeclaration = "QueryArchiveResult",
                            types = listOf(
                                IDLTypeCustom(
                                    id = "Ok",
                                    typeDef = "BlockRange"
                                ),
                                IDLTypeNull(
                                    comment = IDLSingleLineComment(listOf("we don't know the values here...")),
                                    nullDefinition = "Err"
                                )
                            )
                        )
                    )
                )
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
                IDLFileDeclaration(
                    types = listOf(
                        IDLTypeVariant(
                            variantDeclaration = "detail_value",
                            types = listOf(
                                IDLTypeCustom(
                                    typeDef = "True"
                                ),
                                IDLTypeCustom(
                                    typeDef = "False"
                                ),
                                IDLTypeInt64(
                                    id = "I64"
                                ),
                                IDLTypeNat64(
                                    id = "U64"
                                ),
                                IDLTypeVec(
                                    id = "Vec",
                                    vecType = IDLTypeCustom(
                                        typeDef = "detail_value"
                                    )
                                ),
                                IDLTypeVec(
                                    id = "Slice",
                                    vecType = IDLTypeNat8()
                                ),
                                IDLTypeText(
                                    id = "Text"
                                ),
                                IDLTypeFloat64(
                                    id = "Float"
                                ),
                                IDLTypePrincipal(
                                    id = "Principal"
                                )
                            )
                        )
                    )
                )
            )
        )

        @JvmStatic
        private fun func() = listOf(
            Arguments.of(
                "type QueryArchiveFn = func (GetBlocksArgs) -> (QueryArchiveResult) query;",
                IDLFileDeclaration(
                    types = listOf(
                        IDLFun(
                            funcName = "QueryArchiveFn",
                            inputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "GetBlocksArgs"
                                )
                            ),
                            outputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "QueryArchiveResult"
                                )
                            ),
                            funType = FunType.Query
                        )
                    )
                )
            )
        )

        @JvmStatic
        private fun service() = listOf(
            Arguments.of(
                """
                    service : {
                        // Queries blocks in the specified range.
                        query_blocks : (GetBlocksArgs) -> (QueryBlocksResponse) query;
                        
                        // Returns the existing archive canisters information.
                        archives : () -> (Archives) query;
                        
                        // Get the amount of ICP on the specified account.
                        account_balance : (AccountBalanceArgs) -> (Tokens) query;
                        
                        transfer : (TransferArgs) -> (TransferResult);
                    }
                """.trimIndent(),
                IDLFileDeclaration(
                    services = listOf(
                        IDLFun(
                            comment = IDLSingleLineComment(listOf("Queries blocks in the specified range.")),
                            id = "query_blocks",
                            inputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "GetBlocksArgs"
                                )
                            ),
                            outputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "QueryBlocksResponse"
                                )
                            ),
                            funType = FunType.Query
                        ),
                        IDLFun(
                            comment = IDLSingleLineComment(listOf("Returns the existing archive canisters information.")),
                            id = "archives",
                            outputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "Archives"
                                )
                            ),
                            funType = FunType.Query
                        ),
                        IDLFun(
                            comment = IDLSingleLineComment(listOf("Get the amount of ICP on the specified account.")),
                            id = "account_balance",
                            inputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "AccountBalanceArgs"
                                )
                            ),
                            outputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "Tokens"
                                )
                            ),
                            funType = FunType.Query
                        ),
                        IDLFun(
                            id = "transfer",
                            inputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "TransferArgs"
                                )
                            ),
                            outputArgs = listOf(
                                IDLTypeCustom(
                                    typeDef = "TransferResult"
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    service : {
                        icrc7_transfer : (vec TransferArg) -> (vec opt TransferResult);
                    }
                """.trimIndent(),
                IDLFileDeclaration(
                    services = listOf(
                        IDLFun(
                            id = "icrc7_transfer",
                            inputArgs = listOf(
                                IDLTypeVec(
                                    vecType = IDLTypeCustom(
                                        typeDef = "TransferArg"
                                    )
                                )
                            ),
                            outputArgs = listOf(
                                IDLTypeVec(
                                    vecType = IDLTypeCustom(
                                        isOptional = true,
                                        typeDef = "TransferResult"
                                    )
                                )
                            )
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    service : {
                        icrc7_tokens_of : (account : Account, prev : opt nat, take : opt nat) -> (vec nat) query;
                    }
                """.trimIndent(),
                IDLFileDeclaration(
                    services = listOf(
                        IDLFun(
                            id = "icrc7_tokens_of",
                            inputArgs = listOf(
                                IDLTypeCustom(
                                    id = "account",
                                    typeDef = "Account"
                                ),
                                IDLTypeNat(
                                    id = "prev",
                                    isOptional = true
                                ),
                                IDLTypeNat(
                                    id = "take",
                                    isOptional = true
                                )
                            ),
                            outputArgs = listOf(
                                IDLTypeVec(
                                    vecType = IDLTypeNat()
                                )
                            ),
                            funType = FunType.Query
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    service : {
                        icrc7_token_metadata : (token_ids : vec nat) -> (vec record { nat; opt record { text; Value } }) query;
                    }
                """.trimIndent(),
                IDLFileDeclaration(
                    services = listOf(
                        IDLFun(
                            id = "icrc7_token_metadata",
                            inputArgs = listOf(
                                IDLTypeVec(
                                    id = "token_ids",
                                    vecType = IDLTypeNat()
                                )
                            ),
                            outputArgs = listOf(
                                IDLTypeVec(
                                    vecType = IDLRecord(
                                        types = listOf(
                                            IDLTypeNat(),
                                            IDLRecord(
                                                isOptional = true,
                                                types = listOf(
                                                    IDLTypeText(),
                                                    IDLTypeCustom(
                                                        typeDef = "Value"
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            ),
                            funType = FunType.Query
                        )
                    )
                )
            ),

            Arguments.of(
                """
                    service : {
                        icrc7_collection_metadata : () -> (vec record { text; Value } ) query;
                    }
                """.trimIndent(),
                IDLFileDeclaration(
                    services = listOf(
                        IDLFun(
                            id = "icrc7_collection_metadata",
                            outputArgs = listOf(
                                IDLTypeVec(
                                    vecType = IDLRecord(
                                        types = listOf(
                                            IDLTypeText(),
                                            IDLTypeCustom(
                                                typeDef = "Value"
                                            )
                                        )
                                    )
                                )
                            ),
                            funType = FunType.Query
                        )
                    )
                )
            )
        )
    }
}