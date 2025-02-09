package com.bity.icp_kotlin_kit.domain.model.candid_file

internal class CandidParsedFile internal constructor(
    private val candidParsedTypes: List<CandidParsedType>
) {

    fun getTypealiasesDefinition() : String {
        val typealiases = StringBuilder()
        candidParsedTypes.filter { it.candidTypeDefinition.isTypeAlias }
            .forEach {
                val commentedCandidDefinition = getCommentedCandidDefinition(it.candidDefinition)
                typealiases.appendLine(commentedCandidDefinition)
                typealiases.appendLine(it.candidTypeDefinition.getTypealiasDefinition())
                typealiases.appendLine()
            }
        return typealiases.toString()
    }

    fun getClassesDefinition() : String {
        val classes = StringBuilder()
        candidParsedTypes.filter { !it.candidTypeDefinition.isTypeAlias }
            .forEach {
                val commentedCandidDefinition = getCommentedCandidDefinition(it.candidDefinition)
                classes.appendLine(commentedCandidDefinition)
                classes.appendLine(it.candidTypeDefinition.getClassDefinition())
                classes.appendLine()
            }
        return classes.toString()
    }

    private fun getCommentedCandidDefinition(candidDefinition: String): String {
        return candidDefinition
            .split("\n")
            .joinToString(
                prefix = "/**\n",
                separator = "\n",
                postfix = "\n */"
            ) { line -> " * $line" }
    }

}