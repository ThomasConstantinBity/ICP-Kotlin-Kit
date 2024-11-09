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

    // TODO, make fun abstract
    open fun typeVariable(className: String? = null): String =
        IDLTypeHelper.kotlinTypeVariable(this, className)
    open fun getKotlinClassDefinition(): KotlinClassDefinition {
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
    }
    open fun getKotlinClassParameter(className: String? = null): KotlinClassParameter {
        val varId = id ?: IDLTypeHelper.kotlinVariableName(this, className)
        return KotlinClassParameter(
            comment = comment,
            id = varId,
            isOptional = isOptional,
            typeVariable = typeVariable(className)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IDLType

        if (comment != other.comment) return false
        if (isOptional != other.isOptional) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = comment?.hashCode() ?: 0
        result = 31 * result + isOptional.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }
}