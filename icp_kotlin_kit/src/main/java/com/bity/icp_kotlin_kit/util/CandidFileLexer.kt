package com.bity.icp_kotlin_kit.util

import com.bity.icp_kotlin_kit.domain.model.TokenLexer
import guru.zoroark.tegral.niwen.lexer.matchers.matches
import guru.zoroark.tegral.niwen.lexer.niwenLexer

internal object CandidFileLexer {

    val fileLexer = niwenLexer {
        state {
            matches("//.*").ignore // isToken Token.SingleLineComment

            "{" isToken TokenLexer.LBrace
            "}" isToken TokenLexer.RBrace
            "(" isToken TokenLexer.LParen
            ")" isToken TokenLexer.RParen
            ":" isToken TokenLexer.Colon
            ";" isToken TokenLexer.Semi
            "->" isToken TokenLexer.Arrow
            "=" isToken TokenLexer.Equals
            "," isToken TokenLexer.Comma

            matches("""\bopt\s+opt\b""") isToken TokenLexer.DoubleOpt
            matches("""\bopt\b""") isToken TokenLexer.Opt

            matches("""\bimport\b""") isToken TokenLexer.Import

            matches("""\btype\b""") isToken TokenLexer.Type
            matches("""\bservice\b""") isToken TokenLexer.Service

            matches("""\bvec\b""") isToken TokenLexer.Vec
            matches("""\bfunc\b""") isToken TokenLexer.Func
            matches("""\brecord\b""") isToken TokenLexer.Record
            matches("""\bvariant\b""") isToken TokenLexer.Variant

            matches("""\bnull\b""") isToken TokenLexer.Null
            matches("""\bbool\b""") isToken TokenLexer.Boolean
            matches("""\btext\b""") isToken TokenLexer.Text
            matches("""\bblob\b""") isToken TokenLexer.Blob

            matches("""\bint\b""") isToken TokenLexer.Int
            matches("""\bint8\b""") isToken TokenLexer.Int8
            matches("""\bint16\b""") isToken TokenLexer.Int16
            matches("""\bint32\b""") isToken TokenLexer.Int32
            matches("""\bint64\b""") isToken TokenLexer.Int64

            matches("""\bnat\b""") isToken TokenLexer.Nat
            matches("""\bnat8\b""") isToken TokenLexer.Nat8
            matches("""\bnat16\b""") isToken TokenLexer.Nat16
            matches("""\bnat32\b""") isToken TokenLexer.Nat32
            matches("""\bnat64\b""") isToken TokenLexer.Nat64

            matches("""\bfloat64\b""") isToken TokenLexer.Float64

            matches("""\bprincipal\b""") isToken TokenLexer.Principal
            matches("""\bquery\b(?!_)""") isToken TokenLexer.Query
            matches("""\boneway\b(?!_)""") isToken TokenLexer.Oneway
            matches("""(")?[a-zA-Z_][a-zA-Z0-9_]*(")?""") isToken TokenLexer.Id

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