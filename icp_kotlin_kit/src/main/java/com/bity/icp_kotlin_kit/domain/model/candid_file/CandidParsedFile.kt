package com.bity.icp_kotlin_kit.domain.model.candid_file

internal class CandidParsedFile internal constructor(
    private val candidParsedTypes: List<CandidParsedType>
) {

    fun getTypealiasesDefinition() : String {
        val typealiases = StringBuilder()
        candidParsedTypes.filter { it.candidTypeDefinition.isTypeAlias }
            .forEach {
                val commentedCandidDefinition = it.candidDefinition
                    .split("\n")
                    .joinToString("\n *")
                typealiases.appendLine(
                    """
                        /**
                        $commentedCandidDefinition
                         */
                        ${it.candidTypeDefinition.getTypealiasDefinition()}
                    """.trimIndent()
                )
            }
        return typealiases.toString()
    }

}