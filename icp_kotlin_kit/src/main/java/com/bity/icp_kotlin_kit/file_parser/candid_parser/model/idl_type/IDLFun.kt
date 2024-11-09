package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.OptionalType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_fun.FunType
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class IDLFun(
    override val comment: IDLComment? = null,
    override val isOptional: Boolean = false,
    override val optionalType: OptionalType = OptionalType.None,
    override val id: String? = null,
    val funcName: String? = null,
    val inputArgs: List<IDLType> = emptyList(),
    val outputArgs: List<IDLType> = emptyList(),
    val funType: FunType? = null
) : IDLType(
    comment = comment,
    id = id,
    isOptional = isOptional,
    optionalType = optionalType
) {
    companion object : ParserNodeDeclaration<IDLFun> by reflective()

    override fun typeVariable(className: String?): String {
        requireNotNull(className)
        return className
    }

    override fun getKotlinClassDefinition(): KotlinClassDefinition {
        requireNotNull(funcName)
        return KotlinClassDefinition.Function(
            functionName = funcName,
            inputArgs = inputArgs.map { it.getKotlinClassParameter() },
            outputArgs = outputArgs.map { it.getKotlinClassParameter() },
            funType = funType
        )
    }
}