package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.util.CandidFileLexer.fileLexer
import com.bity.icp_kotlin_kit.domain.model.TokenLexer
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidFunctionDeclaration
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidFunctionType
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidType
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeBlob
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeBool
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeCustom
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeFloat
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeFloat64
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeInt
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeInt16
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeInt32
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeInt64
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeInt8
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeNat
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeNat16
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeNat32
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeNat64
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeNat8
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypePrincipal
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeRecord
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeService
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeText
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeVariant
import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeVec
import com.bity.icp_kotlin_kit.domain.model.candid_type.OptionalType
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

internal object CandidTypeParserService {

    private val typeParser = niwenParser {

        CandidType root {

            either {
                expect(CandidTypeRecord) storeIn self()
            } or {
                expect(CandidTypeCustom) storeIn self()
            } or {
                expect(CandidTypeText) storeIn self()
            } or {
                expect(CandidTypeBool) storeIn self()
            } or {
                expect(CandidTypePrincipal) storeIn self()
            } or {
                expect(CandidTypeVec) storeIn self()
            } or {
                expect(CandidTypeVariant) storeIn self()
            } or {
                expect(CandidTypeInt8) storeIn self()
            } or {
                expect(CandidTypeInt16) storeIn self()
            } or {
                expect(CandidTypeInt32) storeIn self()
            } or {
                expect(CandidTypeInt64) storeIn self()
            } or {
                expect(CandidTypeInt) storeIn self()
            } or {
                expect(CandidTypeNat8) storeIn self()
            } or {
                expect(CandidTypeNat16) storeIn self()
            } or{
                expect(CandidTypeNat32) storeIn self()
            } or {
                expect(CandidTypeNat64) storeIn self()
            } or {
                expect(CandidTypeNat) storeIn self()
            } or {
                expect(CandidTypeFloat) storeIn self()
            } or {
                expect(CandidTypeFloat64) storeIn self()
            } or {
                expect(CandidTypeService) storeIn self()
            } or {
                expect(CandidTypeBlob) storeIn self()
            }
            optional {
                expect(TokenLexer.Semi)
            }
        }

        CandidTypeCustom {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeCustom::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    }
                }
                expect(TokenLexer.Id) storeIn CandidTypeCustom::customTypeDefinition
                lookahead {
                    either {
                        expect(TokenLexer.Semi)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.Colon)
                    }
                }
            } or {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeCustom::typeId
                expect(TokenLexer.Equals)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    }
                }
                expect(TokenLexer.Id) storeIn CandidTypeCustom::customTypeDefinition
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    }
                }
                expect(TokenLexer.Id) storeIn CandidTypeCustom::customTypeDefinition
                lookahead {
                    either {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    }
                }
            }

        }

        CandidTypeText {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeText::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeText::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeText::optionalType
                    }
                }
                expect(TokenLexer.Text)
            } or {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeText::typeId
                expect(TokenLexer.Equals)
                emit(true) storeIn CandidTypeText::isTypeAlias
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeText::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeText::optionalType
                    }
                }
                expect(TokenLexer.Text)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeText::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeText::optionalType
                    }
                }
                expect(TokenLexer.Text)
                lookahead {
                    either {
                        expect(TokenLexer.Colon)
                    } or {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    }
                }
            }
        }

        CandidTypeBlob {
            either {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeBlob::typeId
                expect(TokenLexer.Equals)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeBlob::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeBlob::optionalType
                    }
                }
                expect(TokenLexer.Blob)
            } or {
                expect(TokenLexer.Id) storeIn CandidTypeBlob::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeBlob::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeBlob::optionalType
                    }
                }
                expect(TokenLexer.Blob)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeBlob::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeBlob::optionalType
                    }
                }
                expect(TokenLexer.Blob)
            }
        }

        CandidTypeBool {
            either {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeBool::typeId
                expect(TokenLexer.Equals)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                    }
                }
                expect(TokenLexer.Boolean)
            } or {
                expect(TokenLexer.Id) storeIn CandidTypeBool::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                    }
                }
                expect(TokenLexer.Boolean)
            } or {
                expect(TokenLexer.Boolean)
                lookahead {
                    either {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    } or {
                        expect(TokenLexer.Colon)
                    }
                }
            }

        }

        CandidTypePrincipal {
            either {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypePrincipal::typeId
                expect(TokenLexer.Equals)
                emit(true) storeIn CandidTypePrincipal::isTypeAlias
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypePrincipal::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypePrincipal::optionalType
                    }
                }
                expect(TokenLexer.Principal)
            } or {
                expect(TokenLexer.Id) transform { it.replace("\"", "") } storeIn CandidTypePrincipal::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypePrincipal::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypePrincipal::optionalType
                    }
                }
                expect(TokenLexer.Principal)
            } or {
                expect(TokenLexer.Principal)
                lookahead {
                    either {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    } or {
                        expect(TokenLexer.Colon)
                    }
                }
            }
        }

        CandidTypeVariant {
            either {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeVariant::typeId
                expect(TokenLexer.Equals)
                expect(TokenLexer.Variant)
                expect(TokenLexer.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional { expect(TokenLexer.Semi) }
                } storeIn CandidTypeVariant::candidTypes
                expect(TokenLexer.RBrace)
            } or {
                expect(TokenLexer.Id) storeIn CandidTypeVariant::variableName
                expect(TokenLexer.Colon)
                expect(TokenLexer.Variant)
                expect(TokenLexer.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional { expect(TokenLexer.Semi) }
                } storeIn CandidTypeVariant::candidTypes
                expect(TokenLexer.RBrace)
            }
        }

        CandidTypeRecord {
            either {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeRecord::typeId
                expect(TokenLexer.Equals)
                expect(TokenLexer.Record)
                expect(TokenLexer.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional {
                        expect(TokenLexer.Semi)
                    }
                } storeIn CandidTypeRecord::candidTypes
                expect(TokenLexer.RBrace)
            } or {
                expect(TokenLexer.Record)
                expect(TokenLexer.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional {
                        expect(TokenLexer.Semi)
                    }
                } storeIn CandidTypeRecord::candidTypes
                expect(TokenLexer.RBrace)
            } or {
                expect(TokenLexer.Id) storeIn CandidTypeRecord::typeId
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeRecord::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeRecord::optionalType
                    }
                }
                expect(TokenLexer.Record)
                expect(TokenLexer.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional {
                        expect(TokenLexer.Semi)
                    }
                } storeIn CandidTypeRecord::candidTypes
                expect(TokenLexer.RBrace)
            }
        }

        CandidTypeInt {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeInt::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    }
                }
                expect(TokenLexer.Int)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    }
                }
                expect(TokenLexer.Int)
                lookahead {
                    either {
                        expect(TokenLexer.Colon)
                    } or {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    }
                }
            } or {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeInt::typeId
                expect(TokenLexer.Equals)
                emit(true) storeIn CandidTypeInt::isTypeAlias
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    }
                }
                expect(TokenLexer.Int)
            }
        }

        CandidTypeInt8 {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeInt8::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt8::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt8::optionalType
                    }
                }
                expect(TokenLexer.Int8)
            }
        }

        CandidTypeInt16 {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeInt16::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt16::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt16::optionalType
                    }
                }
                expect(TokenLexer.Int16)
            }
        }

        CandidTypeInt32 {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeInt32::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(com.bity.icp_kotlin_kit.domain.model.candid_type.OptionalType.Optional) storeIn CandidTypeInt32::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(com.bity.icp_kotlin_kit.domain.model.candid_type.OptionalType.Optional) storeIn CandidTypeInt32::optionalType
                    }
                }
                expect(TokenLexer.Int32)
            }
        }

        CandidTypeInt64 {
            expect(TokenLexer.Id) storeIn CandidTypeInt64::variableName
            expect(TokenLexer.Colon)
            optional {
                either {
                    expect(TokenLexer.Opt)
                    emit(OptionalType.Optional) storeIn CandidTypeInt64::optionalType
                } or {
                    expect(TokenLexer.DoubleOpt)
                    emit(OptionalType.Optional) storeIn CandidTypeInt64::optionalType
                }
            }
            expect(TokenLexer.Int64)
        }

        CandidTypeNat8 {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeNat8::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat8::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat8::optionalType
                    }
                }
                expect(TokenLexer.Nat8)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat8::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat8::optionalType
                    }
                }
                expect(TokenLexer.Nat8)
            }
        }

        CandidTypeNat16 {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeNat16::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat16::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat16::optionalType
                    }
                }
                expect(TokenLexer.Nat16)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat16::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat16::optionalType
                    }
                }
                expect(TokenLexer.Nat16)
                lookahead {
                    either {
                        expect(TokenLexer.Colon)
                    } or {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    }
                }
            }
        }

        CandidTypeNat32 {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeNat32::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    }
                }
                expect(TokenLexer.Nat32)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    }
                }
                expect(TokenLexer.Nat32)
                lookahead {
                    either {
                        expect(TokenLexer.Colon)
                    } or {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    }
                }
            } or {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeNat32::typeId
                expect(TokenLexer.Equals)
                emit(true) storeIn CandidTypeNat32::isTypeAlias
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    }
                }
                expect(TokenLexer.Nat32)
            }
        }

        CandidTypeNat64 {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeNat64::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat64::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat64::optionalType
                    }
                }
                expect(TokenLexer.Nat64)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat64::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat64::optionalType
                    }
                }
                expect(TokenLexer.Nat64)
                lookahead {
                    either {
                        expect(TokenLexer.Colon)
                    } or {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    } or {
                        expect(TokenLexer.Comma)
                    }
                }
            }
        }

        CandidTypeNat {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeNat::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    }
                }
                expect(TokenLexer.Nat)
            } or {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeNat::typeId
                expect(TokenLexer.Equals)
                emit(true) storeIn CandidTypeNat::isTypeAlias
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    }
                }
                expect(TokenLexer.Nat)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    }
                }
                expect(TokenLexer.Nat)
                lookahead {
                    either {
                        expect(TokenLexer.Colon)
                    } or {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.Semi)
                    }
                }
            }
        }

        CandidTypeFloat {
            expect(TokenLexer.Id) storeIn CandidTypeFloat::variableName
            expect(TokenLexer.Colon)
            optional {
                either {
                    expect(TokenLexer.Opt)
                    emit(OptionalType.Optional) storeIn CandidTypeFloat::optionalType
                } or {
                    expect(TokenLexer.DoubleOpt)
                    emit(OptionalType.Optional) storeIn CandidTypeFloat::optionalType
                }
            }
            expect(TokenLexer.Float64)
        }

        CandidTypeFloat64 {
            either {
                expect(TokenLexer.Id) storeIn CandidTypeFloat64::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat64::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat64::optionalType
                    }
                }
                expect(TokenLexer.Float64)
            } or {
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat64::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat64::optionalType
                    }
                }
                expect(TokenLexer.Float64)
                lookahead {
                    either {
                        expect(TokenLexer.Semi)
                    } or {
                        expect(TokenLexer.RParen)
                    } or {
                        expect(TokenLexer.RBrace)
                    } or {
                        expect(TokenLexer.Colon)
                    }
                }
            }
        }

        CandidTypeVec {
            either {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeVec::typeId
                expect(TokenLexer.Equals)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeVec::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeVec::optionalType
                    }
                }
                expect(TokenLexer.Vec)
                expect(CandidType) storeIn CandidTypeVec::vecType
            } or {
                expect(TokenLexer.Id) storeIn CandidTypeVec::variableName
                expect(TokenLexer.Colon)
                optional {
                    either {
                        expect(TokenLexer.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeVec::optionalType
                    } or {
                        expect(TokenLexer.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeVec::optionalType
                    }
                }
                expect(TokenLexer.Vec)
                expect(CandidType) storeIn CandidTypeVec::vecType
            } or {
                expect(TokenLexer.Vec)
                expect(CandidType) storeIn CandidTypeVec::vecType
                lookahead {
                    expect(TokenLexer.RParen)
                }
            }
        }

        CandidTypeService {
            either {
                expect(TokenLexer.Service)
                expect(TokenLexer.Colon)
                expect(TokenLexer.LBrace)
                repeated(min = 0) {
                    expect(CandidFunctionDeclaration) storeIn item
                } storeIn CandidTypeService::serviceFunctions
                expect(TokenLexer.RBrace)
            } or {
                expect(TokenLexer.Type)
                expect(TokenLexer.Id) storeIn CandidTypeService::typeId
                expect(TokenLexer.Equals)
                expect(TokenLexer.Service)
                expect(TokenLexer.LBrace)
                repeated(min = 0) {
                    expect(CandidFunctionDeclaration) storeIn item
                } storeIn CandidTypeService::serviceFunctions
                expect(TokenLexer.RBrace)
            }
        }

        CandidFunctionDeclaration {
            expect(TokenLexer.Id) storeIn CandidFunctionDeclaration::functionName
            expect(TokenLexer.Colon)

            expect(TokenLexer.LParen)
            repeated(min = 0) {
                expect(CandidType) storeIn item
                optional {
                    expect(TokenLexer.Comma)
                }
            } storeIn CandidFunctionDeclaration::inputParameters
            expect(TokenLexer.RParen)

            expect(TokenLexer.Arrow)

            expect(TokenLexer.LParen)
            repeated(min = 0) {
                expect(CandidType) storeIn item
                optional {
                    expect(TokenLexer.Comma)
                }
            } storeIn CandidFunctionDeclaration::outputParameters
            expect(TokenLexer.RParen)

            optional {
                either {
                    expect(TokenLexer.Query)
                    emit(CandidFunctionType.Query) storeIn CandidFunctionDeclaration::candidFunctionType
                } or {
                    expect(TokenLexer.Oneway)
                    emit(CandidFunctionType.None) storeIn CandidFunctionDeclaration::candidFunctionType
                }
            }
            expect(TokenLexer.Semi)
        }
    }

    fun parseCandidType(candidType: String): CandidType {
         fileLexer.tokenize(candidType).forEachIndexed { i, t ->
            println("[${i}] -> ${t.tokenType}(${t.string})")
        }
        /*typeParser.parseWithDebugger(fileLexer.tokenize(candidType))
            .debuggerResult.apply { println(this) }*/
        return typeParser.parse(fileLexer.tokenize(candidType))
    }

}