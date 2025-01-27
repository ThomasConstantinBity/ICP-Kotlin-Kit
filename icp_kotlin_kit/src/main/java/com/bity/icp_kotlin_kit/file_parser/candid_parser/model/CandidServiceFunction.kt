package com.bity.icp_kotlin_kit.file_parser.candid_parser.model

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.reflective

internal data class CandidServiceFunction(
    val comment: IDLComment? = null,
    val functionName: String,
    val inputArgs: List<CandidType> = emptyList(),
    val outputArgs: List<CandidType> = emptyList(),
    val functionType: CandidServiceFunctionType = CandidServiceFunctionType.OneWay
) {

    fun getKotlinFunctionDefinition(): String =
        when(functionType) {
            CandidServiceFunctionType.Query -> getFunctionQueryDefinition()
            CandidServiceFunctionType.OneWay -> getOneWayQueryDefinition()
        }

    private fun getFunctionQueryDefinition(): String {
        val functionDefinition = StringBuilder("suspend fun $functionName(")
        if(inputArgs.isEmpty())
            functionDefinition.append(")")
        else {
            // Define input args
            functionDefinition.appendLine()
            inputArgs.forEach {
                TODO()
                // functionDefinition.appendLine("\t${it.getKotlinValueDefinition()}")
            }
            functionDefinition.append(")")
        }

        // Define return type
        when {
            outputArgs.isEmpty() -> functionDefinition.appendLine(": {")
            outputArgs.size == 1 ->
                functionDefinition.appendLine(": ${outputArgs.first().getKotlinVariableType()} {")
            else -> TODO("Need to support multiple return type")
        }

        // Function body
        /**
         * val icpQuery = ICPQuery(
         *     methodName = <functionName>,
         *     canister = canister
         * )
         */
        functionDefinition.appendLine("\tval icpQuery = ICPQuery(")
        functionDefinition.appendLine("\t\tmethodName = \"$functionName\",")
        functionDefinition.appendLine("\t\tcanister = canister")
        functionDefinition.appendLine("\t)")

        /**
         * val result = icpQuery.query(
         *     values = List<ValueToEncode>
         * ).getOrThrow()
         */
        functionDefinition.appendLine("\tval result = icpQuery.invoke(")
        when {
            inputArgs.isEmpty() -> functionDefinition.appendLine("\t\tvalues = listOf()")
            else -> {
                functionDefinition.appendLine("\t\tvalues = listOf(")
                inputArgs.forEach { input ->
                    val valueToEncodeDefinition = getValueToEncodeDefinition(input)
                    valueToEncodeDefinition.split("\n").forEach {
                        functionDefinition.appendLine("\t\t\t$it")
                    }
                }
                functionDefinition.appendLine("\t\t)")
            }
        }
        functionDefinition.appendLine("\t).getOrThrow()")

        // Return statement
        functionDefinition.appendLine("\t${getReturnStatement()}")

        functionDefinition.appendLine("}")
        return functionDefinition.toString()
    }

    private fun getOneWayQueryDefinition(): String {
        return "\n"
    }

    private fun getValueToEncodeDefinition(candidType: CandidType): String {

        val argName = TODO() // candidType.getVariableName()
        val expectedClass = candidType.getKotlinVariableType()
        val expectedClassNullable = if(candidType.optionalType == OptionalType.None) "false" else "true"

        return """
            ValueToEncode(
                arg = $argName,
                expectedClass = ${expectedClass}::class,
                expectedClassNullable = $expectedClassNullable
            )
        """.trimIndent()
    }

    private fun getReturnStatement(): String {
        val returnStatement = StringBuilder()
        when {
            outputArgs.isEmpty() -> Unit
            outputArgs.size == 1 -> {
                returnStatement.append("return CandidDecoder.")
                when(outputArgs.first().optionalType) {
                    OptionalType.None -> returnStatement.append("decodeNotNull")
                    OptionalType.Optional -> returnStatement.append("decode")
                    OptionalType.DoubleOptional -> TODO("Need to define CandidDecoder function")
                }
                returnStatement.append("(result.first())")
            }
            else -> TODO("Need to define multiple return statement")
        }
        return returnStatement.toString()
    }

    companion object : ParserNodeDeclaration<CandidServiceFunction> by reflective()
}