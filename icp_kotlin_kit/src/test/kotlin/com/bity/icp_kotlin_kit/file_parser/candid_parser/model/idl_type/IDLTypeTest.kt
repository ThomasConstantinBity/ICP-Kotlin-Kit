package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassParameter
import com.bity.icp_kotlin_kit.file_parser.candid_parser.util.ext_fun.toKotlinFileString
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class IDLTypeTest {

    @ParameterizedTest
    @MethodSource("idlTypeCustom")
    fun `type record class definition`(
        idlRecord: IDLRecord,
        expectedResult: KotlinClassDefinition
    ) {
        val result = idlRecord.getKotlinClassDefinition()
        println(result.kotlinDefinition())
        assertEquals(expectedResult, result)
    }

    @ParameterizedTest
    @MethodSource("idlTypeVariant")
    fun `type variant class definition`(
        idlRecord: IDLTypeVariant,
        expectedResult: KotlinClassDefinition
    ) {
        val result = idlRecord.getKotlinClassDefinition()
        assertEquals(expectedResult, result)
    }

    companion object {

        @JvmStatic
        private fun idlTypeCustom() = listOf(

            /**
             * type Tokens = record {
             *     e8s : nat64;
             * };
             */
            Arguments.of(
                IDLRecord(
                    recordName = "Tokens",
                    types = listOf(
                        IDLTypeNat64(
                            id = "e8s"
                        )
                    )
                ),
                KotlinClassDefinition.Class(
                    className = "Tokens",
                ).apply {
                    params.add(
                        KotlinClassParameter(
                            id = "e8s",
                            isOptional = false,
                            typeVariable = "ULong"
                        )
                    )
                }
            )
        )

        @JvmStatic
        private fun idlTypeVariant() = listOf(
            /**
             * type Transfer = variant {
             *     Mint: record {
             *         to: AccountIdentifier;
             *         amount: Tokens;
             *     };
             *     Burn: record {
             *         from: AccountIdentifier;
             *         amount: Tokens;
             *     };
             *     Send: record {
             *         from: AccountIdentifier;
             *         to: AccountIdentifier;
             *         amount: Tokens;
             *     };
             * };
             */
            Arguments.of(
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
                        )
                    )
                ),
                KotlinClassDefinition.SealedClass(
                    className = "Transfer"
                ).apply {
                    innerClasses.addAll(
                        listOf(
                            KotlinClassDefinition.Class(
                                className = "Mint"
                            ).apply {
                                params.addAll(
                                    listOf(
                                        KotlinClassParameter(
                                            id = "to",
                                            isOptional = false,
                                            typeVariable = "AccountIdentifier"
                                        )
                                    )
                                )
                            }
                        )
                    )
                }
            )
        )
    }
}