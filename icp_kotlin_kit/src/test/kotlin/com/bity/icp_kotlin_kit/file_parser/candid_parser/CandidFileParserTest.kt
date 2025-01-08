package com.bity.icp_kotlin_kit.file_parser.candid_parser

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.IDLFileDeclaration
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

            )
        )

        @JvmStatic
        private fun vecRecord() = listOf(
            Arguments.of(
                "type Map = vec record { text; Value };",

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

            ),

            Arguments.of(
                """
                    type TimeStamp = record {
                        timestamp_nanos: nat64;
                    };
                """.trimIndent(),

            ),

            Arguments.of(
                """
                    type Transaction = record {
                        operation: opt Transfer;
                        memo: Memo;
                        created_at_time: TimeStamp;
                    };
                """.trimIndent(),

            ),

            Arguments.of(
                """
                    type Block = record {
                        parent_hash: opt Hash;
                        transaction: Transaction;
                        timestamp: TimeStamp;
                    };
                """.trimIndent(),

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

            ),

            Arguments.of(
                """
                    type BlockRange = record {
                        blocks : vec Block;
                    };
                """.trimIndent(),

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

            ),

            Arguments.of(
                """
                    type Archive = record {
                        canister_id: principal;
                    };
                """.trimIndent(),

            ),

            Arguments.of(
                """
                    type Archives = record {
                        archives: vec Archive;
                    };
                """.trimIndent(),

            ),

            Arguments.of(
                """
                    type AccountBalanceArgs = record {
                        account: AccountIdentifier;
                    };
                """.trimIndent(),

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

            )
        )

        @JvmStatic
        private fun typeAlias() = listOf(
            Arguments.of(
                "type AccountIdentifier = blob;",

            ),

            Arguments.of(
                "type Memo = nat64;",

            ),

            Arguments.of(
                "type SubAccount = blob;",

            ),

            Arguments.of(
                "type Hash = blob;",

            ),

            Arguments.of(
                "type BlockIndex = nat64;",

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
            ),

            Arguments.of(
                """
                    type TransferResult = variant {
                        Ok : BlockIndex;
                        Err : TransferError;
                    };
                """.trimIndent(),

            ),

            Arguments.of(
                """
                    type QueryArchiveResult = variant {
                        Ok : BlockRange;
                        Err : null;      // we don't know the values here...
                    };
                """.trimIndent(),

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

            )
        )

        @JvmStatic
        private fun func() = listOf(
            Arguments.of(
                "type QueryArchiveFn = func (GetBlocksArgs) -> (QueryArchiveResult) query;",

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
            ),

            Arguments.of(
                """
                    service : {
                        icrc7_transfer : (vec TransferArg) -> (vec opt TransferResult);
                    }
                """.trimIndent(),

            ),

            Arguments.of(
                """
                    service : {
                        icrc7_tokens_of : (account : Account, prev : opt nat, take : opt nat) -> (vec nat) query;
                    }
                """.trimIndent(),
            ),

            Arguments.of(
                """
                    service : {
                        icrc7_token_metadata : (token_ids : vec nat) -> (vec record { nat; opt record { text; Value } }) query;
                    }
                """.trimIndent(),
            ),

            Arguments.of(
                """
                    service : {
                        icrc7_collection_metadata : () -> (vec record { text; Value } ) query;
                    }
                """.trimIndent(),
            )
        )
    }
}