package com.bity.icp_kotlin_kit.file_parser.candid_parser

import guru.zoroark.tegral.niwen.lexer.matchers.matches
import guru.zoroark.tegral.niwen.lexer.niwenLexer

internal object CandidFileLexer {

    val fileLexer = niwenLexer {
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

    fun debug(input: String) {
        // println(input)
        fileLexer.tokenize(input).forEachIndexed { i, t ->
            println("[$i] - ${t.tokenType} '${t.string}'")
        }
    }
}