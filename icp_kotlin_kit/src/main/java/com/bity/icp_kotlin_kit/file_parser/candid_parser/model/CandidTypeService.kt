package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidTypeService(
    override val typeId: String? = null,
    val serviceFunctions: List<CandidFunctionDeclaration>
) : CandidType() {

    override val variableName: String = ""
    override val optionalType: OptionalType = OptionalType.None
    override fun getKotlinType(variableName: String?): String = TODO()

    override fun getServiceDefinition(serviceName: String?): String {
        val name = typeId?.split("_")?.joinToString("") { it }
            ?: serviceName
            ?: "Service"
        val serviceDefinition = StringBuilder(
            """
                class $name(
                    private val canister: ICPPrincipal
                ) {
            """.trimIndent()
        )
        val functionsDefinition = serviceFunctions.joinToString(
            prefix = "\n\t",
            separator = "\n\n\t"
        ) { it.getFunctionDefinition() }
        serviceDefinition.appendLine(functionsDefinition)
        serviceDefinition.appendLine("}")
        return serviceDefinition.toString()
    }

    companion object : ParserNodeDeclaration<CandidTypeService> by reflective()
}