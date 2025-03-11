package com.bity.icp_kotlin_kit.domain.model.candid_file

import com.bity.icp_kotlin_kit.domain.model.candid_type.CandidTypeService

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
            .filter { it.candidTypeDefinition !is CandidTypeService }
            .forEach {
                val commentedCandidDefinition = getCommentedCandidDefinition(it.candidDefinition)
                classes.appendLine(commentedCandidDefinition)
                classes.appendLine(it.candidTypeDefinition.getClassDefinition())
                classes.appendLine()
            }
        return classes.toString()
    }

    fun getServiceDefinition() : String {
        return candidParsedTypes
            .filter { it.candidTypeDefinition is CandidTypeService }
            .joinToString("\n") { it.candidTypeDefinition.getServiceDefinition() }
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