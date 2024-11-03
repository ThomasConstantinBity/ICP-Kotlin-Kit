package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
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
        require(variantDeclaration != null || id != null)
        val kotlinClass = KotlinClassDefinition.SealedClass(
            className = variantDeclaration ?: id!!.uppercase()
        )
        val innerClasses = types.map {
            val innerClass = it.getKotlinClassDefinition()
            innerClass.inheritedClass = kotlinClass
            innerClass
        }
        kotlinClass.innerClasses.addAll(innerClasses)
        return kotlinClass
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as IDLTypeVariant

        if (variantDeclaration != other.variantDeclaration) return false
        if (types != other.types) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (variantDeclaration?.hashCode() ?: 0)
        result = 31 * result + types.hashCode()
        return result
    }


}