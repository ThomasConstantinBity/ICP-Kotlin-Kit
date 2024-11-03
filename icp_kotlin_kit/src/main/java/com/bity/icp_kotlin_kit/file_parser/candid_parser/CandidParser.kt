package com.bity.icp_kotlin_kit.file_parser.candid_parser

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLSingleLineComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.IDLFileDeclaration
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_fun.FunType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_service.IDLService
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_service.IDLServiceType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLFun
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLRecord
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeBlob
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeBoolean
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeCustom
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeFloat64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeInt
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeInt16
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeInt32
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeInt64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat16
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat32
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat8
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNull
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypePrincipal
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeText
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeVariant
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeVec
import com.bity.icp_kotlin_kit.file_parser.candid_parser.util.ext_fun.trimCommentLine
import guru.zoroark.tegral.niwen.lexer.matchers.matches
import guru.zoroark.tegral.niwen.lexer.niwenLexer
import guru.zoroark.tegral.niwen.parser.dsl.either
import guru.zoroark.tegral.niwen.parser.dsl.emit
import guru.zoroark.tegral.niwen.parser.dsl.expect
import guru.zoroark.tegral.niwen.parser.dsl.item
import guru.zoroark.tegral.niwen.parser.dsl.lookahead
import guru.zoroark.tegral.niwen.parser.dsl.niwenParser
import guru.zoroark.tegral.niwen.parser.dsl.optional
import guru.zoroark.tegral.niwen.parser.dsl.or
import guru.zoroark.tegral.niwen.parser.dsl.repeated
import guru.zoroark.tegral.niwen.parser.dsl.self

// TODO, add support for end of line comment in order to support multiple comment
// type QueryArchiveResult = variant {
//    Err : null;      // we don't know the values here...
//    // A new line comment
//    Ok : BlockRange;
//
//};
internal object CandidParser {

    private val fileLexer = niwenLexer {
        state {
            matches("//.*") isToken Token.SingleLineComment

            "{" isToken Token.LBrace
            "}" isToken Token.RBrace
            "(" isToken Token.LParen
            ")" isToken Token.RParen
            ":" isToken Token.Colon
            ";" isToken Token.Semi
            "->" isToken Token.Arrow
            "=" isToken Token.Equals
            "," isToken Token.Comma

            "opt" isToken Token.Opt

            "import" isToken Token.Import

            "type" isToken  Token.Type
            "service" isToken Token.Service

            "func" isToken Token.Func
            "vec" isToken Token.Vec
            matches("""\brecord\b""") isToken Token.Record
            "variant" isToken Token.Variant

            "bool" isToken Token.Boolean
            "text" isToken Token.Text
            "null" isToken Token.Null
            "blob" isToken Token.Blob
            matches("""\bint\b""") isToken Token.Int
            matches("""\bint16\b""") isToken Token.Int16
            matches("""\bint32\b""") isToken Token.Int32
            matches("""\bint64\b""") isToken Token.Int64

            matches("""\bnat\b""") isToken Token.Nat
            matches("""\bnat8\b""") isToken Token.Nat8
            matches("""\bnat16\b""") isToken Token.Nat16
            matches("""\bnat32\b""") isToken Token.Nat32
            matches("""\bnat64\b""") isToken Token.Nat64

            matches("""\bfloat64\b""") isToken Token.Float64

            matches("""\bprincipal\b""") isToken Token.Principal
            matches("""\bquery\b(?!_)""") isToken Token.Query
            matches("""\boneway\b(?!_)""") isToken Token.Oneway
            matches("""(")?[a-zA-Z_][a-zA-Z0-9_]*(")?""") isToken Token.Id

            matches("[ \t\r\n]+").ignore
            matches("//[^\n]*").ignore
        }
    }

    private val fileParser = niwenParser {
        IDLFileDeclaration root {

            // TODO, need to add imports

            optional {
                expect(IDLComment) storeIn IDLFileDeclaration::comment
            }

            optional {
                repeated<IDLFileDeclaration, IDLType>(min = 1) {
                    expect(IDLType) storeIn item
                } storeIn IDLFileDeclaration::types
            }

            optional {
                expect(Token.Service)
                expect(Token.Colon)
                optional {
                    expect(Token.LParen)
                    repeated<IDLFileDeclaration, IDLType> {
                        expect(IDLType) storeIn item
                    } storeIn IDLFileDeclaration::serviceConstructors
                    expect(Token.RParen)
                    expect(Token.Arrow)
                }
                expect(Token.LBrace)
                repeated<IDLFileDeclaration, IDLFun>(min = 1) {
                    expect(IDLFun) storeIn item
                } storeIn IDLFileDeclaration::services
                expect(Token.RBrace)
            }
            optional{
                expect(Token.Semi)
            }
        }

        IDLService {
            optional {
                expect(IDLComment) storeIn IDLService::comment
            }
            expect(Token.Id) storeIn IDLService::id
            expect(Token.Colon)
            expect(Token.ServiceArgs) storeIn IDLService::inputParamsDeclaration
            expect(Token.Arrow)
            expect(Token.ServiceArgs) storeIn IDLService::outputParamsDeclaration
            optional {
                expect(Token.Query)
                emit(IDLServiceType.Query) storeIn IDLService::serviceType
            }
            expect(Token.Semi)
        }

        /**
         * Comment
         */
        IDLComment {
            // TODO, add different comment support
            either {
                expect(IDLSingleLineComment) storeIn self()
            }
        }
        IDLSingleLineComment {
            repeated(min = 1) {
                expect(Token.SingleLineComment) transform { it.trimCommentLine() } storeIn item
            } storeIn IDLSingleLineComment::commentLines
        }

        IDLType {
            either {
                expect(IDLRecord) storeIn self()
            } or {
                expect(IDLTypeNat64) storeIn self()
            } or {
                expect(IDLTypeBlob) storeIn self()
            } or {
                expect(IDLTypeCustom) storeIn self()
            } or {
                expect(IDLTypeVariant) storeIn self()
            } or {
                expect(IDLTypeVec) storeIn self()
            } or {
                expect(IDLTypeNull) storeIn self()
            } or {
                expect(IDLFun) storeIn self()
            } or {
                expect(IDLTypePrincipal) storeIn self()
            } or {
                expect(IDLTypeText) storeIn self()
            } or {
                expect(IDLTypeNat) storeIn self()
            } or {
                expect(IDLTypeInt) storeIn self()
            } or {
                expect(IDLTypeBoolean) storeIn self()
            } or {
                expect(IDLTypeInt64) storeIn self()
            } or {
                expect(IDLTypeNat8) storeIn self()
            } or {
                expect(IDLTypeFloat64) storeIn self()
            } or {
                expect(IDLTypeInt16) storeIn self()
            } or {
                expect(IDLTypeNat16) storeIn self()
            } or {
                expect(IDLTypeNat32) storeIn self()
            } or {
                expect(IDLTypeInt32) storeIn self()
            }
        }

        IDLRecord {

            optional {
                expect(IDLComment) storeIn IDLRecord::comment
            }

            either {
                expect(Token.Type)
                expect(Token.Id) storeIn IDLRecord::recordName
                expect(Token.Equals)
                expect(Token.Record)
                expect(Token.LBrace)
            } or {
                expect(Token.Id) storeIn IDLRecord::recordName
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLRecord::isOptional
                }
                expect(Token.Record)
                expect(Token.LBrace)
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLRecord::isOptional
                }
                expect(Token.Record)
                expect(Token.LBrace)
            }
            repeated(min = 0) {
                expect(IDLType) storeIn item
                optional { expect(Token.Semi) }
            } storeIn IDLRecord::types
            expect(Token.RBrace)
            optional {
                expect(Token.Semi)
            }
        }

        IDLTypeVariant {

            optional {
                expect(IDLComment) storeIn IDLTypeVariant::comment
            }

            either {
                expect(Token.Type)
                expect(Token.Id) storeIn IDLTypeVariant::variantDeclaration
                expect(Token.Equals)
            } or {
                expect(Token.Id) storeIn IDLTypeVariant::id
                expect(Token.Colon)
            }

            expect(Token.Variant)
            expect(Token.LBrace)
            repeated(min = 1) {
                expect(IDLType) storeIn item
                optional{
                    expect(Token.Semi)
                }
            } storeIn IDLTypeVariant::types
            expect(Token.RBrace)
            expect(Token.Semi)
        }

        IDLTypeCustom {

            optional {
                expect(IDLComment) storeIn IDLTypeCustom::comment
            }

            either {
                expect(Token.Type)
                expect(Token.Id) storeIn IDLTypeCustom::typeDef
                expect(Token.Equals)
                expect(IDLType) storeIn IDLTypeCustom::type
                optional {
                    expect (IDLComment) storeIn IDLTypeCustom::comment
                }
                optional { expect(Token.Semi) }
            } or {
                expect(Token.Id) storeIn IDLTypeCustom::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeCustom::isOptional
                }
                expect(Token.Id) storeIn IDLTypeCustom::typeDef
                optional {
                    expect (IDLComment) storeIn IDLTypeCustom::comment
                }
                optional {
                    expect(Token.Semi)
                }

                optional {
                    expect(IDLComment) storeIn IDLTypeCustom::comment
                }
            } or {
                expect(Token.Id) storeIn IDLTypeCustom::typeDef
                optional {
                    expect (IDLComment) storeIn IDLTypeCustom::comment
                }
                expect(Token.Semi)
            } or {
                expect(Token.Id) storeIn IDLTypeCustom::typeDef
                lookahead {
                    either {
                        expect(Token.Comma)
                    } or {
                        expect(Token.RParen)
                    }
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeCustom::isOptional
                }
                expect(Token.Id) storeIn IDLTypeCustom::typeDef
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.Comma)
                    } or {
                        expect(Token.RBrace)
                    } or {
                        expect(Token.RParen)
                    }
                }
            }
        }

        IDLTypeBlob {

            optional {
                expect(IDLComment) storeIn IDLTypeBlob::comment
            }

            either {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeBlob::isOptional
                }
                expect(Token.Blob)
            } or {
                expect(Token.Id) storeIn IDLTypeBlob::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeBlob::isOptional
                }
                expect(Token.Blob)
                optional {
                    expect(Token.Semi)
                }
            }

            optional {
                expect(IDLComment) storeIn IDLTypeBlob::comment
            }
        }

        IDLTypeBoolean {
            optional {
                expect(IDLComment) storeIn IDLTypeBoolean::comment
            }
            either {
                expect(Token.Id) storeIn IDLTypeBoolean::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeBoolean::isOptional
                }
                expect(Token.Boolean)
                optional {
                    expect(Token.Semi)
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeBoolean::isOptional
                }
                expect(Token.Boolean)
            }
        }

        IDLTypeNat {

            optional {
                expect(IDLComment) storeIn IDLTypeNat::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeNat::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeNat::isOptional
                }
                expect(Token.Nat)
                lookahead {
                    either {
                        expect(Token.Comma)
                    } or {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    } or {
                        expect(Token.RParen)
                    }
                }
                optional {
                    expect(Token.Semi)
                }
                optional {
                    expect(IDLComment) storeIn IDLTypeNat::comment
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeNat::isOptional
                }
                expect(Token.Nat)
                optional {
                    expect(Token.Semi)
                }
            }
        }

        IDLTypeNat8 {
            optional {
                expect(IDLComment) storeIn IDLTypeNat8::comment
            }
            either {
                expect(Token.Id) storeIn IDLTypeNat8::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeNat8::isOptional
                }
                expect(Token.Nat8)
                optional {
                    expect(Token.Semi)
                }
                optional {
                    expect(IDLComment) storeIn IDLTypeNat8::comment
                }
            } or {
                expect(Token.Nat8)
            }
        }

        IDLTypeNat16 {
            optional {
                expect(IDLComment) storeIn IDLTypeNat16::comment
            }
            either {
                expect(Token.Id) storeIn IDLTypeNat16::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeNat16::isOptional
                }
                expect(Token.Nat16)
                optional {
                    expect(Token.Semi)
                }
                optional {
                    expect(IDLComment) storeIn IDLTypeNat16::comment
                }
            } or {
                expect(Token.Nat16)
            }
        }

        IDLTypeNat32 {
            optional {
                expect(IDLComment) storeIn IDLTypeNat32::comment
            }
            either {
                expect(Token.Id) storeIn IDLTypeNat32::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeNat32::isOptional
                }
                expect(Token.Nat32)
                optional {
                    expect(Token.Semi)
                }
                optional {
                    expect(IDLComment) storeIn IDLTypeNat32::comment
                }
            } or {
                expect(Token.Nat32)
            }
        }

        IDLTypeNat64 {
            optional {
                expect(IDLComment) storeIn IDLTypeNat64::comment
            }
            either {
                expect(Token.Id) storeIn IDLTypeNat64::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeNat64::isOptional
                }
                expect(Token.Nat64)
                optional {
                    expect(Token.Semi)
                }
            } or {
                expect(Token.Nat64)
            }
        }

        IDLTypeInt {
            optional {
                expect(IDLComment) storeIn IDLTypeInt::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeInt::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt::isOptional
                }
                expect(Token.Int)
                optional {
                    expect(Token.Semi)
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt::isOptional
                }
                expect(Token.Int)
            }
        }

        IDLTypeInt16 {
            optional {
                expect(IDLComment) storeIn IDLTypeInt16::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeInt16::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt16::isOptional
                }
                expect(Token.Int16)

                optional {
                    expect(Token.Semi)
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt16::isOptional
                }
                expect(Token.Int16)
            }
        }

        IDLTypeInt32 {
            optional {
                expect(IDLComment) storeIn IDLTypeInt32::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeInt32::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt32::isOptional
                }
                expect(Token.Int32)

                optional {
                    expect(Token.Semi)
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt32::isOptional
                }
                expect(Token.Int32)
            }
        }

        IDLTypeInt64 {
            optional {
                expect(IDLComment) storeIn IDLTypeInt64::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeInt64::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt64::isOptional
                }
                expect(Token.Int64)

                optional {
                    expect(Token.Semi)
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt64::isOptional
                }
                expect(Token.Int64)
            }
        }

        IDLTypeFloat64 {
            optional {
                expect(IDLComment) storeIn IDLTypeFloat64::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeFloat64::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeFloat64::isOptional
                }
                expect(Token.Float64)

                optional {
                    expect(Token.Semi)
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeFloat64::isOptional
                }
                expect(Token.Float64)
            }
        }

        IDLTypeText {
            optional {
                expect(IDLComment) storeIn IDLTypeText::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeText::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeText::isOptional
                }
                expect(Token.Text)
                optional {
                    expect(Token.Semi)
                }
                optional {
                    expect(IDLComment) storeIn IDLTypeText::comment
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeText::isOptional
                }
                expect(Token.Text)
                optional {
                    expect(Token.Semi)
                }
            }
        }

        IDLTypeVec {

            optional {
                expect(IDLComment) storeIn IDLTypeVec::comment
            }

            either {
                expect(Token.Type)
                expect(Token.Id) storeIn IDLTypeVec::vecDeclaration
                expect(Token.Equals)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeVec::isOptional
                }
                expect(Token.Vec)
                expect(IDLType) storeIn IDLTypeVec::vecType
            } or {
                expect(Token.Id) storeIn IDLTypeVec::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeVec::isOptional
                }
                expect(Token.Vec)
                expect(IDLType) storeIn IDLTypeVec::vecType
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeVec::isOptional
                }
                expect(Token.Vec)
                expect(IDLType) storeIn IDLTypeVec::vecType
            }
        }

        IDLTypeNull {

            optional {
                expect(IDLComment) storeIn IDLTypeNull::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeNull::nullDefinition
                expect(Token.Colon)
                expect(Token.Null)
                expect(Token.Semi)
            } or {
                expect(Token.Null)
                lookahead {
                    either {
                        expect(Token.Comma)
                    } or {
                        expect(Token.RParen)
                    }
                }
            }

            optional {
                expect(IDLComment) storeIn IDLTypeNull::comment
            }
        }

        IDLFun {

            optional {
                expect(IDLComment) storeIn IDLFun::comment
            }

            either {
                expect(Token.Type)
                expect(Token.Id) transform { it.replace("\"", "") } storeIn IDLFun::funcName
                expect(Token.Equals)
                expect(Token.Func)
            } or {
                expect(Token.Id) transform { it.replace("\"", "") } storeIn IDLFun::id
                expect(Token.Colon)
                optional {
                    expect(Token.Func)
                }
            }

            // Input args declaration
            expect(Token.LParen)
            repeated {
                expect(IDLType) storeIn item
                optional { expect(Token.Comma) }
            } storeIn IDLFun::inputArgs
            expect(Token.RParen)

            expect(Token.Arrow)

            // Output args declaration
            expect(Token.LParen)
            repeated {
                expect(IDLType) storeIn item
                optional { expect(Token.Comma) }
            } storeIn IDLFun::outputArgs
            expect(Token.RParen)

            optional {
                either {
                    expect(Token.Query)
                    emit(FunType.Query) storeIn IDLFun::funType
                } or {
                    expect(Token.Oneway)
                    emit(FunType.OneWay) storeIn IDLFun::funType
                }
            }
            expect(Token.Semi)
        }

        IDLTypePrincipal {
            either {
                expect(Token.Id) storeIn IDLTypePrincipal::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypePrincipal::isOptional
                }
                expect(Token.Principal)
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypePrincipal::isOptional
                }
                expect(Token.Principal)
            } or {
                expect(Token.Principal)
                lookahead {
                    either {
                        expect(Token.Comma)
                    } or {
                        expect(Token.RParen)
                    }
                }
            }
            optional {
                expect(Token.Semi)
            }
        }
    }

    fun parseFile(input: String): IDLFileDeclaration {
        // debug(input)
        return fileParser.parse(fileLexer.tokenize(input))
    }

    // TODO delete
    private fun debug(input: String) {
        println(input)
        fileLexer.tokenize(input).forEachIndexed { i, t ->
            println("[$i] - ${t.tokenType} '${t.string}'")
        }
    }
}