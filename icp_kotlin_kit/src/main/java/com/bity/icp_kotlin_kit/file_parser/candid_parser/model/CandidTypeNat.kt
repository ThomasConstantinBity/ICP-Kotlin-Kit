package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeNat(
    override val typeId: String,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
) : CandidType() {

    override fun shouldDeclareInnerClass(): Boolean = false

    override fun getKotlinVariableType(): String = "BigInteger"

    override fun getKotlinDefinitionForSealedClass(className: String): String {
        val sealedClassName = typeId ?: throw RuntimeException("Unable to get sealed class name for $this")
        val variableName = if(typeName != null) TODO() else "natValue"
        return "data class ${sealedClassName}(val $variableName: BigInteger): $className()"
    }

    companion object : ParserNodeDeclaration<CandidTypeNat> by reflective()
}