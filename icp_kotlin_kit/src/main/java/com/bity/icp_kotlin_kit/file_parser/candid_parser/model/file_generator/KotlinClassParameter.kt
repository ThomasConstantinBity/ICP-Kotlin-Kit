package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.file_generator.KotlinCommentGenerator

internal data class KotlinClassParameter(
    private val comment: IDLComment? = null,
    val id: String,
    val isOptional: Boolean,
    private val typeVariable: String
) {

    val typeDeclaration = if (isOptional) "$typeVariable?" else typeVariable

    fun constructorDefinition(): String {
        val kotlinDefinition = StringBuilder()
        comment?.let {
            kotlinDefinition.appendLine(
                KotlinCommentGenerator.getKotlinComment(it)
            )
        }
        kotlinDefinition.append("val $id: $typeDeclaration")
        return kotlinDefinition.toString()
    }

    fun functionInputArgument(): String = "$id: $typeDeclaration"

    fun valueToEncodeDefinition(): String {
        return if(isOptional)
            """
                ValueToEncode(
                    arg = $id,
                    expectedClass = ${typeVariable}::class,
                    expectedClassNullable = true
                )
            """.trimIndent() else
            "ValueToEncode($id)"
    }
}