package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.util.ext_function.idToClassName
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class IDLTypeVariant(
    override val comment: IDLComment? = null,
    override val isOptional: Boolean = false,
    override val id: String? = null,
    val variantDeclaration: String? = null,
    val types: List<IDLType>
) : IDLType(
    comment = comment,
    id = id,
    isOptional = isOptional
) {
    companion object : ParserNodeDeclaration<IDLTypeVariant> by reflective()

    override fun getKotlinClassDefinition(): KotlinClassDefinition {
        val className = when {
            variantDeclaration != null -> variantDeclaration
            id != null -> id.idToClassName()
            else -> throw RuntimeException("Unable to define sealed class name")
        }
        val kotlinSealedClass = KotlinClassDefinition.SealedClass(
            className = className
        )
        val innerClasses = types.map {
            val innerClass = it.getKotlinClassDefinition()
            innerClass.inheritedClass = kotlinSealedClass
            innerClass
        }
        kotlinSealedClass.innerClasses.addAll(innerClasses)
        return kotlinSealedClass
    }
}