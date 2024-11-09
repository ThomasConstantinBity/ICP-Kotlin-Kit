package com.bity.icp_kotlin_kit.file_parser.candid_parser

import guru.zoroark.tegral.niwen.lexer.TokenType

internal enum class Token: TokenType {
    SingleLineComment,
    Equals,
    LParen,
    RParen,
    LBrace,
    RBrace,
    Semi,
    Comma,
    Colon,
    Arrow,
    Null,
    Vec,
    Record,
    Variant,
    Func,
    ServiceArgs,
    Service,
    Oneway,
    Query,
    CompositeQuery,
    Blob,
    Type,
    Import,
    Opt,
    DoubleOpt,
    Principal,
    Id,
    Text,
    Boolean,

    Float,
    Float64,

    Int,
    Int8,
    Int16,
    Int32,
    Int64,

    Nat,
    Nat8,
    Nat16,
    Nat32,
    Nat64
}
