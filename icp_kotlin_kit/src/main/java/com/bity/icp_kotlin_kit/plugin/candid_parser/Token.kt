package com.bity.icp_kotlin_kit.plugin.candid_parser

import guru.zoroark.tegral.niwen.lexer.TokenType

internal enum class Token: TokenType {
    SingleLineComment,
    StartComment,
    Equals,
    LParen,
    RParen,
    LBrace,
    RBrace,
    Semi,
    Comma,
    Dot,
    Colon,
    Arrow,
    Null,
    Vec,
    VecRecord,
    Record,
    Variant,
    Func,
    Service,
    Oneway,
    Query,
    CompositeQuery,
    Blob,
    Type,
    Import,
    Opt,
    TestEqual,
    NotEqual,
    NotDecode,
    Principal,
    Id,
    StartString,
    Text,
    Sign,
    Decimal,
    Hex,
    Float,
    Boolean,

    Int,

    Nat,
    Nat64
}
