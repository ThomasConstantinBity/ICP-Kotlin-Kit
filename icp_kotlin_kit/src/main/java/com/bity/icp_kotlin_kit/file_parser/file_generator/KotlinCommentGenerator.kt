package com.bity.icp_kotlin_kit.file_parser.file_generator

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLSingleLineComment

internal object KotlinCommentGenerator {

    fun getNullableKotlinComment(comment: IDLComment?): String? {
        comment ?: return null
        return getKotlinComment(comment)
    }

    fun getKotlinComment(comment: IDLComment): String {
        return when (comment) {
            is IDLSingleLineComment ->
                comment.commentLines.joinToString(separator  = "\n") { "// $it" }
        }
    }
}