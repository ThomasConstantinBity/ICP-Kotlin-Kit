package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal class CandidFunctionDeclaration(
    val functionName: String,
    val inputParameters: List<CandidType>,
    val outputParameters: List<CandidType>,
    val candidFunctionType: CandidFunctionType = CandidFunctionType.None,
) {

    fun getFunctionDefinition(): String {
        val funName = functionName.replace("\"", "")
        val functionDefinition = StringBuilder("suspend fun $funName(")
        functionDefinition.append(getFunctionInputParamDefinition())
        functionDefinition.appendLine(getFunctionReturnValueDefinition())
        functionDefinition.appendLine(" {")
        functionDefinition.appendLine(getQueryDefinition())
        functionDefinition.appendLine(getQueryCallDefinition())
        functionDefinition.appendLine(getReturnStatement())
        functionDefinition.appendLine("}")
        return functionDefinition.toString()
    }

    private fun getFunctionInputParamDefinition(): String {
        val inputParametersDefinition = StringBuilder()

        val inputVariables = inputParameters.joinToString(
            prefix = "\n\t",
            separator = "\n\t",
            postfix = "\n"
        ) {
            // TODO: remove TODO() once variableName is not null
            val variableName = it.variableName ?: TODO()
            "$variableName: ${it.getKotlinVariableType()}"
        }
        inputParametersDefinition.append(inputVariables.trim())

        when (candidFunctionType) {
            CandidFunctionType.None -> TODO()
            CandidFunctionType.Query -> { }
        }

        inputParametersDefinition.append(")")
        return inputParametersDefinition.toString().trim()
    }

    private fun getFunctionReturnValueDefinition(): String {
        return when(outputParameters.size) {
            0 -> ""
            1 -> ": ${outputParameters.first().getKotlinVariableType()}"
            else -> TODO()
        }
    }

    private fun getQueryDefinition(): String {
        return when(candidFunctionType) {
            CandidFunctionType.None -> TODO()
            CandidFunctionType.Query ->
                """
                    val icpQuery = ICPQuery(
                        methodName = $functionName,
                        canister = canister
                    )
                """.trimIndent()
        }
    }

    private fun getQueryCallDefinition(): String {
        return when (candidFunctionType) {
            CandidFunctionType.None -> TODO()
            CandidFunctionType.Query ->
                """
                    val result = icpQuery.invoke(
                        values = listOf()
                    ).getOrThrow()
                """.trimIndent()
        }
    }

    private fun getReturnStatement(): String {
        return when(outputParameters.size) {
            0 -> TODO()
            1 -> getReturnStatementForType(outputParameters.first())
            else -> TODO()
        }
    }

    private fun getReturnStatementForType(candidType: CandidType): String {
        return when(candidType.optionalType) {
            OptionalType.None -> "return CandidDecoder.decodeNotNull(result.first())"
            OptionalType.Optional -> "return CandidDecoder.decode(result.first())"
            OptionalType.DoubleOptional -> TODO()
        }
    }

    companion object : ParserNodeDeclaration<CandidFunctionDeclaration> by reflective()
}