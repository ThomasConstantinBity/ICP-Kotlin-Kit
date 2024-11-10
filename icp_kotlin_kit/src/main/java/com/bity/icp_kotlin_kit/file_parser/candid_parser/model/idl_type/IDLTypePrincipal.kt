package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class IDLTypePrincipal(
    override val comment: IDLComment? = null,
    override val id: String? = null,
    override val isOptional: Boolean = false,
) : IDLType(
    comment = comment,
    id = id,
    isOptional = isOptional
) {

    companion object : ParserNodeDeclaration<IDLTypePrincipal> by reflective()

    override fun typeVariable(className: String?): String = "ICPPrincipal"

    override fun getKotlinClassDefinition(): KotlinClassDefinition {

        val variableName = when {
            id != null -> id.replace("\"", "")
            else -> "principal"
        }

        return KotlinClassDefinition.Primitive(
            variableName = variableName,
            className = "ICPPrincipal"
        )
    }
}