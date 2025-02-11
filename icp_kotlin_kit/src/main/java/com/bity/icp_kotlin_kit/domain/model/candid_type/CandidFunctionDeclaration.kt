package com.bity.icp_kotlin_kit.domain.model.candid_type

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
        val functionDefinition = StringBuilder("suspend fun $funName")
        functionDefinition.append(getFunctionInputParamDefinition())
        functionDefinition.appendLine(getFunctionReturnValueDefinition())
        functionDefinition.appendLine(" {")
        functionDefinition.appendLine(getQueryDefinition())
        functionDefinition.appendLine(getQueryCallDefinition())
        functionDefinition.appendLine(getReturnStatement())
        functionDefinition.appendLine("}")
        return functionDefinition.toString()
    }

    /**
     * no input params -> ()
     *
     * one input param ->
     *      (
     *          inputParam1: inputParam1Type
     *      )
     *
     * multiple input params ->
     *      (
     *          inputParam1: inputParam1Type,
     *          inputParam2: inputParam2Type,
     *          ...
     *      )
     */
    private fun getFunctionInputParamDefinition(): String {
        return when {
            inputParameters.isEmpty() && candidFunctionType == CandidFunctionType.Query -> "()"
            inputParameters.isEmpty() && candidFunctionType == CandidFunctionType.None -> {
                """(
                        sender: ICPSigningPrincipal,
                        pollingValues: PollingValues = PollingValues()
                    )
                """.trimIndent()
            }

            inputParameters.isNotEmpty() && candidFunctionType == CandidFunctionType.Query -> {
                val inputVariablesDefinition = inputParameters
                    .joinToString(separator = ",\n\t") {
                        // TODO: remove TODO once variableName is not null
                        val variableName = it.variableName ?: TODO()
                        "$variableName: ${it.getKotlinVariableType()}"
                    }
                """(
                        $inputVariablesDefinition
                    )
                """.trimIndent()
            }

            else -> {
                val inputVariablesDefinition = inputParameters
                    .joinToString(
                        separator = ",\n\t",
                    ) {
                        // TODO: remove TODO once variableName is not null
                        val variableName = it.variableName ?: TODO()
                        "$variableName: ${it.getKotlinVariableType()}"
                    }
                """(
                        $inputVariablesDefinition,
                        sender: ICPSigningPrincipal,
                        pollingValues: PollingValues = PollingValues()
                    )
                """.trimIndent()
            }

        }
    }

    private fun getFunctionReturnValueDefinition(): String {
        return when(outputParameters.size) {
            0 -> ""
            1 -> ": ${outputParameters.first().getKotlinVariableType()}"
            else -> TODO()
        }
    }

    private fun getQueryDefinition(): String {
        return """
            val icpQuery = ICPQuery(
                methodName = "${functionName.replace("\"", "")}",
                canister = canister
            )
                """.trimIndent()
    }

    private fun getQueryCallDefinition(): String {
        return when (candidFunctionType) {
            CandidFunctionType.None ->
                """
                    val result = icpQuery.callAndPoll(
                        values = ${getListOfValuesForQuery()},
                        sender = sender,
                        pollingValues = pollingValues
                    ).getOrThrow()
                """.trimIndent()
            CandidFunctionType.Query ->
                """
                    val result = icpQuery.invoke(
                        values = ${getListOfValuesForQuery()}
                    ).getOrThrow()
                """.trimIndent()
        }
    }

    private fun getListOfValuesForQuery(): String {
        return if(inputParameters.isEmpty()) {
            "listOf()"
        } else {
            // TODO: remove TODO() when variableName is not null
            val variableNames = inputParameters.joinToString { it.variableName ?: TODO() }
            "listOf($variableNames)"
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