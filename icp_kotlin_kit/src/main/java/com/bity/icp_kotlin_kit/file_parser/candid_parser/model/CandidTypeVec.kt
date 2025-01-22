package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeVec(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val vecType: CandidType
): CandidType() {

    override fun shouldDeclareInnerClass(): Boolean =
        vecType.shouldDeclareInnerClass()

    override fun getKotlinDefinition(candidTypeDefinitionId: String): String {
        val typealiasBaseDefinition = "typealias $candidTypeDefinitionId = "
        val className = vecType.getKotlinClassName(candidTypeDefinitionId)
        val typealiasDefinition = when(optionalType) {
            OptionalType.None -> "$typealiasBaseDefinition Array<$className>"
            OptionalType.Optional -> "$typealiasBaseDefinition Array<${className}>?"
            OptionalType.DoubleOptional -> TODO()
        }
        return if(vecType.shouldDeclareInnerClass()) {
            val classDefinition = vecType.getKotlinDefinition(className)
            return "$typealiasDefinition\n$classDefinition"
        } else typealiasDefinition
    }

    override fun getKotlinVariableType(): String {
        val arrayDefinition = if(typeId == "Array") "kotlin.Array" else "Array"
        return "$arrayDefinition<${vecType.getKotlinVariableType()}>"
    }

    override fun getInnerClassesToDeclare(): List<CandidType> {
        return vecType.getInnerClassesToDeclare()
    }

    companion object : ParserNodeDeclaration<CandidTypeVec> by reflective()
}