package com.bity.icp_kotlin_kit.file_parser.candid_parser

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.OptionalType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLSingleLineComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.IDLFileDeclaration
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_fun.FunType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLService
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_service.IDLServiceType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeCustom
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeFloat
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeInt64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeNat64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeNat8
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypePrincipal
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeRecord
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeText
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeVariant
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidTypeVec
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
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeInt8
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat16
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat32
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNat8
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeNull
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypePrincipal
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeService
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeText
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeVariant
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.IDLTypeVec
import com.bity.icp_kotlin_kit.file_parser.candid_parser.util.ext_fun.trimCommentLine
import guru.zoroark.tegral.niwen.lexer.Lexer
import guru.zoroark.tegral.niwen.lexer.matchers.matches
import guru.zoroark.tegral.niwen.lexer.matchers.repeated
import guru.zoroark.tegral.niwen.lexer.niwenLexer
import guru.zoroark.tegral.niwen.parser.NiwenParser
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

            matches("""\bopt\s+opt\b""") isToken Token.DoubleOpt
            matches("""\bopt\b""") isToken Token.Opt

            matches("""\bimport\b""") isToken Token.Import

            matches("""\btype\b""") isToken  Token.Type
            matches("""\bservice\b""") isToken Token.Service

            matches("""\bvec\b""") isToken Token.Vec
            matches("""\bfunc\b""") isToken Token.Func
            matches("""\brecord\b""") isToken Token.Record
            matches("""\bvariant\b""") isToken Token.Variant

            matches("""\bnull\b""") isToken Token.Null
            matches("""\bbool\b""") isToken Token.Boolean
            matches("""\btext\b""") isToken Token.Text
            matches("""\bblob\b""") isToken Token.Blob

            matches("""\bint\b""") isToken Token.Int
            matches("""\bint8\b""") isToken Token.Int8
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

    private val typeParser = niwenParser {

        CandidTypeDefinition root {
            expect(Token.Type)
            expect(Token.Id) storeIn CandidTypeDefinition::id
            expect(Token.Equals)
            expect(CandidType) storeIn CandidTypeDefinition::candidType
        }

        CandidType {
            either {
                expect(CandidTypeVariant) storeIn self()
            } or {
                expect(CandidTypeRecord) storeIn self()
            } or {
                expect(CandidTypeCustom) storeIn self()
            } or {
                expect(CandidTypeText) storeIn self()
            } or {
                expect(CandidTypePrincipal) storeIn self()
            } or {
                expect(CandidTypeInt64) storeIn self()
            } or {
                expect(CandidTypeNat8) storeIn self()
            } or {
                expect(CandidTypeNat64) storeIn self()
            } or {
                expect(CandidTypeFloat) storeIn self()
            } or {
                expect(CandidTypeVec) storeIn self()
            }
        }

        CandidTypeCustom {
            // TODO, add optional type
            either {
                expect(Token.Id) storeIn CandidTypeCustom::typeDefinition
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            } or {
                expect(Token.Id) storeIn CandidTypeCustom::typeId
                expect(Token.Colon)
                expect(Token.Id) storeIn CandidTypeCustom::typeDefinition
            }
        }

        CandidTypeText {
            either {
                expect(Token.Id) storeIn CandidTypeText::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeText::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeText::optionalType
                    }
                }
                expect(Token.Text)
            } or {
                expect(Token.Text)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }
        }

        CandidTypePrincipal {
            either {
                expect(Token.Id) storeIn CandidTypePrincipal::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypePrincipal::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypePrincipal::optionalType
                    }
                }
                expect(Token.Principal)
            }
        }

        CandidTypeVariant {
            expect(Token.Variant)
            expect(Token.LBrace)
            repeated(min = 1) {
                expect(CandidType) storeIn item
                expect(Token.Semi)
            } storeIn CandidTypeVariant::candidTypes
            expect(Token.RBrace)
            expect(Token.Semi)
        }

        CandidTypeRecord {
            expect(Token.Record)
            expect(Token.LBrace)
            repeated(min = 1) {
                expect(CandidType) storeIn item
                optional {
                    expect(Token.Semi)
                }
            } storeIn CandidTypeRecord::candidTypes
            expect(Token.RBrace)
            optional {
                expect(Token.Semi)
            }
        }

        CandidTypeInt64 {
            either {
                expect(Token.Id) storeIn CandidTypeInt64::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt64::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt64::optionalType
                    }
                }
                expect(Token.Int64)
            }
        }

        CandidTypeNat8 {
            either {
                expect(Token.Id) storeIn CandidTypeNat8::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat8::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat8::optionalType
                    }
                }
                expect(Token.Nat8)
            }
        }

        CandidTypeNat64 {
            either {
                expect(Token.Id) storeIn CandidTypeNat64::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat64::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat64::optionalType
                    }
                }
                expect(Token.Nat64)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat64::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat64::optionalType
                    }
                }
                expect(Token.Nat8)
                lookahead {
                    expect(Token.Semi)
                }
            }
        }

        CandidTypeFloat {
            either {
                expect(Token.Id) storeIn CandidTypeFloat::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat::optionalType
                    }
                }
                expect(Token.Float64)
            }
        }

        CandidTypeVec {
            either {
                expect(Token.Id) storeIn CandidTypeVec::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeVec::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeVec::optionalType
                    }
                }
                expect(Token.Vec)
                expect(CandidType) storeIn CandidTypeVec::vecType
            }
        }
    }

    /* private val typeParser = niwenParser {
        IDLType root {
            // TODO, comment
            expect(Token.Type)
            expect(Token.Id) storeIn
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
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeNat32::isOptional
                }
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

        IDLTypeInt8 {
            optional {
                expect(IDLComment) storeIn IDLTypeInt8::comment
            }

            either {
                expect(Token.Id) storeIn IDLTypeInt8::id
                expect(Token.Colon)
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt8::isOptional
                }
                expect(Token.Int8)

                optional {
                    expect(Token.Semi)
                }
            } or {
                optional {
                    expect(Token.Opt)
                    emit(true) storeIn IDLTypeInt8::isOptional
                }
                expect(Token.Int8)
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
                    either {
                        expect(Token.Opt)
                        emit(true) storeIn IDLTypeVec::isOptional
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.DoubleOptional) storeIn IDLTypeVec::optionalType
                    }
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
                optional {
                    expect(Token.Comma)
                }
            } storeIn IDLFun::inputArgs
            expect(Token.RParen)

            expect(Token.Arrow)

            // Output args declaration
            expect(Token.LParen)
            repeated {
                expect(IDLType) storeIn item
                optional {
                    expect(Token.Comma)
                }
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

        IDLTypeService {
            expect(Token.Type)
            expect(Token.Id) storeIn IDLTypeService::id
            expect(Token.Equals)
            expect(Token.Service)
            expect(Token.LBrace)

            repeated(min = 1) {
                expect(IDLFun) storeIn item
            }

            expect(Token.RBrace)
            expect(Token.Semi)
        }
    } */

    fun parseFile(input: String): IDLFileDeclaration {

        var string = input.trimStart()
        val comments: List<String> = mutableListOf()
        while (string.isNotEmpty()) {
            when {

                string.startsWith("type") -> {
                    val typeDefinitionEndIndex = getEndDeclarationIndex(string)
                    val typeDefinition = string.substring(0, typeDefinitionEndIndex)
                    parseCandidType(typeDefinition)
                    string = string.substring(typeDefinitionEndIndex).trimStart()
                }

                string.startsWith("service") -> {
                    val serviceDefinitionEndIndex = getEndDeclarationIndex(string)
                    val serviceDeclaration = string.substring(0, serviceDefinitionEndIndex)
                    string = string.substring(serviceDefinitionEndIndex).trimStart()
                }

                else -> throw RuntimeException("Unable to parse $string")
            }

        }

        TODO()

        // debug(input)
        // return fileParser.parse(fileLexer.tokenize(input))
    }

    private fun parseCandidType(typeDefinition: String) {
        debug(typeDefinition)
        val result = typeParser.parse(fileLexer.tokenize(typeDefinition))
        println(result.getKotlinClassDefinition())
    }
    private fun getEndDeclarationIndex(string: String): Int {
        var brackets = 0
        string.forEachIndexed { index, char ->
            when {
                char == ';' && brackets == 0 -> return index + 1
                char == '{' -> brackets++
                char == '}' -> brackets--
                else -> { }
            }
        }
        return string.length
    }

    // TODO delete
    private fun debug(input: String) {
        // println(input)
        fileLexer.tokenize(input).forEachIndexed { i, t ->
            println("[$i] - ${t.tokenType} '${t.string}'")
        }
    }
}