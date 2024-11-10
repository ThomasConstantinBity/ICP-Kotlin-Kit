package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.OptionalType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassParameter
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.file_generator.helper.IDLTypeHelper
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.dsl.subtype

// TODO remove open val, used to test
// TODO remove isOptional
internal sealed class IDLType(
    open val comment: IDLComment?,
    open val isOptional: Boolean,
    open val optionalType: OptionalType = OptionalType.None,
    open val id: String?
) {
    companion object : ParserNodeDeclaration<IDLType> by subtype()

    open fun typeVariable(className: String? = null): String = """TODO("need to implement typeVariable for $this")"""

    open fun getKotlinClassDefinition(): KotlinClassDefinition = TODO("Not implemented for $this")

    /**{
        val objectName = id
        requireNotNull(objectName)
        val kotlinClass = KotlinClassDefinition.Class(
            className = objectName
        )
        kotlinClass.params.add(
            KotlinClassParameter(
                id = IDLTypeHelper.kotlinVariableName(
                    type = this,
                    className = null
                ),
                isOptional = isOptional,
                typeVariable = typeVariable()
            )
        )
        return kotlinClass
    } **/

    open fun getKotlinClassParameter(className: String? = null): KotlinClassParameter {
        val varId = id ?: "TODO()"
        return KotlinClassParameter(
            comment = comment,
            id = varId,
            isOptional = isOptional,
            typeVariable = typeVariable(className)
        )
    }
}