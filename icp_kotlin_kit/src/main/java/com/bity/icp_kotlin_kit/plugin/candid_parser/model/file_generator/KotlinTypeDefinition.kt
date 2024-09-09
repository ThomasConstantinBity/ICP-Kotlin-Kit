package com.bity.icp_kotlin_kit.plugin.candid_parser.model.file_generator

import com.bity.icp_kotlin_kit.plugin.candid_parser.util.ext_fun.toKotlinMultiLineComment

internal data class KotlinTypeDefinition(
    val className: String,
    val comment: String? = null,
    val candidDefinition: String,
    val classDefinitionType: KotlinClassDefinitionType,
) {
    fun kotlinDefinition(showCandidDefinition: Boolean): String {
        val kotlinDefinition = StringBuilder()

        if(showCandidDefinition)
            kotlinDefinition.appendLine(candidDefinition.toKotlinMultiLineComment())

        comment?.let { comment ->
            kotlinDefinition.appendLine(comment)
        }

        kotlinDefinition.appendLine(classDefinitionType.kotlinDefinition())
        return kotlinDefinition.toString()
    }
}