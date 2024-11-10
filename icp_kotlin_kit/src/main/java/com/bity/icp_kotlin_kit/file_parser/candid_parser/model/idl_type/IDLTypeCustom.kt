package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassParameter
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.util.ext_fun.kotlinVariableName
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class IDLTypeCustom(
    override val comment: IDLComment? = null,
    override val isOptional: Boolean = false,
    override val id: String? = null,
    val typeDef: String? = null,
    val type: IDLType? = null
) : IDLType(
    comment = comment,
    id = id,
    isOptional = isOptional
) {

    companion object : ParserNodeDeclaration<IDLTypeCustom> by reflective()

    override fun typeVariable(className: String?): String {
        requireNotNull(typeDef)
        return typeDef
    }

    override fun getKotlinClassParameter(className: String?): KotlinClassParameter {
        val typeVariable = type?.typeVariable() ?: typeDef
        requireNotNull(typeVariable)
        return when {
            id != null -> KotlinClassParameter(
                comment = comment,
                id = id,
                isOptional = isOptional,
                typeVariable = typeVariable
            )
            else -> KotlinClassParameter(
                comment = comment,
                id = typeVariable.kotlinVariableName(),
                isOptional = isOptional,
                typeVariable = typeVariable
            )
        }
    }

    override fun getKotlinClassDefinition(): KotlinClassDefinition {
        requireNotNull(typeDef)
        return when {

            id == null -> {
                KotlinClassDefinition.Object(
                    objectName = typeDef
                )
            }

            else -> {
                val className = id
                val kotlinClass = KotlinClassDefinition.Class(
                    className = className
                )
                kotlinClass.params.add(
                    KotlinClassParameter(
                        comment = comment,
                        id = typeDef.kotlinVariableName(),
                        isOptional = isOptional,
                        typeVariable = typeDef
                    )
                )
                return kotlinClass
            }
        }
    }
}