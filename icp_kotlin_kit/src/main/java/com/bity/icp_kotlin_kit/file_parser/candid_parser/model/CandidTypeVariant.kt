package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeVariant(
    override val typeId: String? = null,
    override val variableName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val candidTypes: List<CandidType>,
): CandidType() {

    override val kotlinType: String
        get() = TODO()

    override fun isKotlinTypealiasDefinition(): Boolean = false

    override fun getKotlinDefinition(): String {
        requireNotNull(typeId)
        val sealedClassDefinition = StringBuilder("sealed class $typeId {")
        val classesDefinition = candidTypes.joinToString(
            prefix = "\n\t",
            separator = "\n\t",
            postfix = "\n"
        ) {
            it.getClassDefinitionForSealedClass(typeId)
        }
        sealedClassDefinition.append(classesDefinition)
        sealedClassDefinition.appendLine("}")
        return sealedClassDefinition.toString()
    }

    companion object : ParserNodeDeclaration<CandidTypeVariant> by reflective()
}