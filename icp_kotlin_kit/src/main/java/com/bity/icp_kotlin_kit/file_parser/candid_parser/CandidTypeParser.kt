package com.bity.icp_kotlin_kit.file_parser.candid_parser

import com.bity.icp_kotlin_kit.file_parser.candid_parser.CandidParserCommon.debug
import com.bity.icp_kotlin_kit.file_parser.candid_parser.CandidParserCommon.fileLexer
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.OptionalType
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

internal object CandidTypeParser {

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
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    }
                }
                expect(Token.Id) storeIn CandidTypeCustom::typeDefinition
                lookahead {
                    either {
                        expect(Token.RParen)
                    }
                }
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
                    } or {
                        expect(Token.RParen)
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

    fun parseCandidType(typeDefinition: String): CandidTypeDefinition {
        // debug(typeDefinition)
        return typeParser.parse(fileLexer.tokenize(typeDefinition))
    }

}