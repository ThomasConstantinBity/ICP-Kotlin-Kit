package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.domain.service.CandidTypeParserService
import com.bity.icp_kotlin_kit.file_parser.candid_parser.CandidFileLexer.fileLexer
import com.bity.icp_kotlin_kit.file_parser.candid_parser.Token
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidFunctionDeclaration
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidFunctionType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeBool
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeCustom
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeFloat
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeFloat64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeInt
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeInt16
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeInt32
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeInt64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeInt8
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeNat
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeNat16
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeNat32
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeNat64
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeNat8
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypePrincipal
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeRecord
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeService
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeText
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeVariant
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidTypeVec
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.OptionalType
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

internal class CandidTypeParserServiceImpl : CandidTypeParserService {

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
                expect(CandidTypeInt64) storeIn self()
            } or {
                expect(CandidTypeNat8) storeIn self()
            } or {
                expect(CandidTypeNat64) storeIn self()
            } or {
                expect(CandidTypeFloat) storeIn self()
            } or {
                expect(CandidTypeService) storeIn self()
            }
            /*
                expect(CandidTypeInt) storeIn self()
                expect(CandidTypeInt8) storeIn self()
                expect(CandidTypeInt16) storeIn self()
                expect(CandidTypeInt32) storeIn self()
                expect(CandidTypeNat) storeIn self()
                expect(CandidTypeNat16) storeIn self()
                expect(CandidTypeNat32) storeIn self()
                expect(CandidTypeFloat64) storeIn self()
            */

            optional {
                expect(Token.Semi)
            }
        }

        CandidTypeCustom {
            /*either {
                expect(Token.Id) storeIn CandidTypeCustom::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    }
                }
                expect(Token.Id) storeIn CandidTypeCustom::customTypeDefinition
            } or {
                expect(Token.Type)
                expect(Token.Id) storeIn CandidTypeCustom::typeId
                expect(Token.Equals)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    }
                }
                expect(Token.Id) storeIn CandidTypeCustom::customTypeDefinition
                lookahead {
                    either {
                        expect(Token.RParen)
                    } or {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/

            either {
                optional {
                    expect(Token.Id) storeIn CandidTypeCustom::typeId
                    expect(Token.Colon)
                }
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    }
                }
                expect(Token.Id) storeIn CandidTypeCustom::customTypeDefinition
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RParen)
                    }
                }
            } or {
                expect(Token.Type)
                expect(Token.Id) storeIn CandidTypeCustom::typeId
                expect(Token.Equals)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeCustom::optionalType
                    }
                }
                expect(Token.Id) storeIn CandidTypeCustom::customTypeDefinition
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
                expect(Token.Id) storeIn CandidTypeCustom::customTypeDefinition
                lookahead {
                    either {
                        expect(Token.RBrace)
                    } or {
                        expect(Token.RParen)
                    } or {
                        expect(Token.Semi)
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
                lookahead {
                    either {
                        expect(Token.RBrace)
                    } or {
                        expect(Token.RParen)
                    } or {
                        expect(Token.Semi)
                    }
                }
            }
            /*either {
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
            }*/
        }

        CandidTypeBool {
            /*either {
                expect(Token.Id) storeIn CandidTypeBool::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                    }
                }
                expect(Token.Boolean)
            } or {
                expect(Token.Type)
                expect(Token.Id) storeIn CandidTypeBool::typeId
                expect(Token.Equals)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                    }
                }
                expect(Token.Boolean)

            }*/

            expect(Token.Type)
            expect(Token.Id) storeIn CandidTypeBool::typeId
            expect(Token.Equals)
            optional {
                either {
                    expect(Token.Opt)
                    emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                } or {
                    expect(Token.DoubleOpt)
                    emit(OptionalType.Optional) storeIn CandidTypeBool::optionalType
                }
            }
            expect(Token.Boolean)

        }

        CandidTypePrincipal {
            either {
                expect(Token.Type)
                expect(Token.Id) storeIn CandidTypePrincipal::typeId
                expect(Token.Equals)
                emit(true) storeIn CandidTypePrincipal::isTypeAlias
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
            } or {
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
            expect(Token.Type)
            expect(Token.Id) storeIn CandidTypeVariant::typeId
            expect(Token.Equals)
            expect(Token.Variant)
            expect(Token.LBrace)
            repeated(min = 1) {
                expect(CandidType) storeIn item
                optional { expect(Token.Semi) }
            } storeIn CandidTypeVariant::candidTypes
            expect(Token.RBrace)
            /*either {
                expect(Token.Variant)
                expect(Token.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional { expect(Token.Semi) }
                } storeIn CandidTypeVariant::candidTypes
                expect(Token.RBrace)
                expect(Token.Semi)
            } or {
                expect(Token.Id) storeIn CandidTypeVariant::typeId
                expect(Token.Colon)
                expect(Token.Variant)
                expect(Token.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional { expect(Token.Semi) }
                } storeIn CandidTypeVariant::candidTypes
                expect(Token.RBrace)
                expect(Token.Semi)
            }*/
        }

        CandidTypeRecord {
            either {
                expect(Token.Type)
                expect(Token.Id) storeIn CandidTypeRecord::typeId
                expect(Token.Equals)
                expect(Token.Record)
                expect(Token.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional {
                        expect(Token.Semi)
                    }
                } storeIn CandidTypeRecord::candidTypes
                expect(Token.RBrace)
            } or {
                expect(Token.Record)
                expect(Token.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional {
                        expect(Token.Semi)
                    }
                } storeIn CandidTypeRecord::candidTypes
                expect(Token.RBrace)
            }
            /*either {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeRecord::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeRecord::optionalType
                    }
                }
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
            } or {
                expect(Token.Id) storeIn CandidTypeRecord::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeRecord::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeRecord::optionalType
                    }
                }
                expect(Token.Record)
                expect(Token.LBrace)
                repeated(min = 1) {
                    expect(CandidType) storeIn item
                    optional {
                        expect(Token.Semi)
                    }
                } storeIn CandidTypeRecord::candidTypes
                expect(Token.RBrace)
                lookahead {
                    expect(Token.Semi)
                }
            }*/
        }

        CandidTypeInt {
            /*either {
                expect(Token.Id) storeIn CandidTypeInt::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    }
                }
                expect(Token.Int)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt::optionalType
                    }
                }
                expect(Token.Int)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeInt8 {
            /*either {
                expect(Token.Id) storeIn CandidTypeInt8::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt8::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt8::optionalType
                    }
                }
                expect(Token.Int8)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt8::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt8::optionalType
                    }
                }
                expect(Token.Int8)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeInt16 {
            /*either {
                expect(Token.Id) storeIn CandidTypeInt16::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt16::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt16::optionalType
                    }
                }
                expect(Token.Int16)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt16::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt16::optionalType
                    }
                }
                expect(Token.Int16)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeInt32 {
            /*either {
                expect(Token.Id) storeIn CandidTypeInt32::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt32::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt32::optionalType
                    }
                }
                expect(Token.Int32)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt32::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeInt32::optionalType
                    }
                }
                expect(Token.Int32)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeInt64 {
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
            /*either {
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
            } or {
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
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeNat8 {
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
            /*either {
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
            } or {
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
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeNat16 {
            /* either {
                expect(Token.Id) storeIn CandidTypeNat16::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat16::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat16::optionalType
                    }
                }
                expect(Token.Nat16)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat16::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat16::optionalType
                    }
                }
                expect(Token.Nat16)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeNat32 {
            /*either {
                expect(Token.Id) storeIn CandidTypeNat32::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    }
                }
                expect(Token.Nat32)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat32::optionalType
                    }
                }
                expect(Token.Nat32)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeNat64 {
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
            /*either {

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
                expect(Token.Nat64)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeNat {
            /*either {
                expect(Token.Id) storeIn CandidTypeNat::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    }
                }
                expect(Token.Nat)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeNat::optionalType
                    }
                }
                expect(Token.Nat)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeFloat {
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

        CandidTypeFloat64 {
            /*either {
                expect(Token.Id) storeIn CandidTypeFloat64::typeId
                expect(Token.Colon)
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat64::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat64::optionalType
                    }
                }
                expect(Token.Float64)
            } or {
                optional {
                    either {
                        expect(Token.Opt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat64::optionalType
                    } or {
                        expect(Token.DoubleOpt)
                        emit(OptionalType.Optional) storeIn CandidTypeFloat64::optionalType
                    }
                }
                expect(Token.Float64)
                lookahead {
                    either {
                        expect(Token.Semi)
                    } or {
                        expect(Token.RBrace)
                    }
                }
            }*/
        }

        CandidTypeVec {
            either {
                expect(Token.Type)
                expect(Token.Id) storeIn CandidTypeVec::typeId
                expect(Token.Equals)
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
            } or {
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
            } or {
                expect(Token.Vec)
                expect(CandidType) storeIn CandidTypeVec::vecType
                lookahead {
                    expect(Token.RParen)
                }
            }
        }

        CandidTypeService {
            either {
                expect(Token.Service)
                expect(Token.Colon)
                expect(Token.LBrace)
                repeated(min = 0) {
                    expect(CandidFunctionDeclaration) storeIn item
                } storeIn CandidTypeService::serviceFunctions
                expect(Token.RBrace)
            }
        }

        CandidFunctionDeclaration {
            expect(Token.Id) storeIn CandidFunctionDeclaration::functionName
            expect(Token.Colon)

            expect(Token.LParen)
            repeated(min = 0) {
                expect(CandidType) storeIn item
                optional {
                    expect(Token.Comma)
                }
            } storeIn CandidFunctionDeclaration::inputParameters
            expect(Token.RParen)

            expect(Token.Arrow)

            expect(Token.LParen)
            repeated(min = 0) {
                expect(CandidType) storeIn item
                optional {
                    expect(Token.Comma)
                }
            } storeIn CandidFunctionDeclaration::outputParameters
            expect(Token.RParen)

            optional {
                either {
                    expect(Token.Query)
                    emit(CandidFunctionType.Query) storeIn CandidFunctionDeclaration::candidFunctionType
                } or {
                    expect(Token.Oneway)
                    emit(CandidFunctionType.None) storeIn CandidFunctionDeclaration::candidFunctionType
                }
            }
            expect(Token.Semi)
        }
    }

    override fun parseCandidType(candidType: String): CandidType {
        /* fileLexer.tokenize(candidType).forEachIndexed { i, t ->
            println("[${i}] -> ${t.tokenType}(${t.string})")
        } */
        /*typeParser.parseWithDebugger(fileLexer.tokenize(candidType))
            .debuggerResult.apply { println(this) }*/
        return typeParser.parse(fileLexer.tokenize(candidType))
    }

}