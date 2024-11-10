package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassParameter
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.util.ext_function.idToVariableName
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class IDLTypeText(
    comment: IDLComment? = null,
    isOptional: Boolean = false,
    id: String? = null,
) : IDLType(
    comment = comment,
    id = id,
    isOptional = isOptional
) {
    companion object : ParserNodeDeclaration<IDLTypeText> by reflective()

    override fun typeVariable(className: String?): String = "String"

    override fun getKotlinClassDefinition(): KotlinClassDefinition {
        val variableName = when {
            id != null -> id
            else -> "text"
        }
        return KotlinClassDefinition.Primitive(
            variableName = variableName,
            className = "String"
        )
    }

    override fun getKotlinClassParameter(className: String?): KotlinClassParameter {
        return super.getKotlinClassParameter(className)
    }
}