package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import com.bity.icp_kotlin_kit.file_parser.file_generator.helper.UnnamedClassHelper
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidTypeRecord(
    override val typeId: String? = null,
    override var typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val candidTypes: List<CandidType>
): CandidType() {

    override fun getInnerClassesToDeclare(): List<CandidType> {
        return listOf(this)
    }

    override fun getKotlinVariableType(): String {

        when {
            typeName == null -> {
                val unnamedClassName = UnnamedClassHelper.getUnnamedClassName()
                typeName = unnamedClassName
                return unnamedClassName
            }

            else -> TODO()
        }
    }

    companion object : ParserNodeDeclaration<CandidTypeRecord> by reflective()
}