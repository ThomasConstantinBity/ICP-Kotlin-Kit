package com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.OptionalType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassDefinition
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.file_generator.KotlinClassParameter
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_comment.IDLComment
import com.bity.icp_kotlin_kit.file_parser.file_generator.helper.UnnamedClassHelper
import guru.zoroark.tegral.niwen.parser.ParserNodeDeclaration
import guru.zoroark.tegral.niwen.parser.dsl.subtype
import guru.zoroark.tegral.niwen.parser.reflective

// TODO remove open val, used to test
// TODO remove isOptional
internal sealed class IDLType(
    open val comment: IDLComment?,
    open val isOptional: Boolean,
    open val optionalType: OptionalType = OptionalType.None,
    open val id: String?
) {
    companion object : ParserNodeDeclaration<IDLType> by subtype()

    open fun typeVariable(className: String? = null): String = """TODO("need to implement typeVariable for $this")"""

    open fun getKotlinClassDefinition(): KotlinClassDefinition = TODO("Not implemented for $this")

    /**{
        val objectName = id
        requireNotNull(objectName)
        val kotlinClass = KotlinClassDefinition.Class(
            className = objectName
        )
        kotlinClass.params.add(
            KotlinClassParameter(
                id = IDLTypeHelper.kotlinVariableName(
                    type = this,
                    className = null
                ),
                isOptional = isOptional,
                typeVariable = typeVariable()
            )
        )
        return kotlinClass
    } **/

    open fun getKotlinClassParameter(className: String? = null): KotlinClassParameter {
        val varId = id ?: "TODO()"
        return KotlinClassParameter(
            comment = comment,
            id = varId,
            isOptional = isOptional,
            typeVariable = typeVariable(className)
        )
    }
}

internal data class CandidTypeDefinition(
    val id: String,
    val candidType: CandidType
) {

    fun getKotlinClassDefinition(): String = candidType.getKotlinClassDefinition(id)

    companion object : ParserNodeDeclaration<CandidTypeDefinition> by reflective()
}

internal sealed class CandidType {

    abstract val typeId: String?
    abstract val typeName: String?
    abstract val optionalType: OptionalType

    open fun getInnerClassesToDeclare(): List<CandidType> = emptyList()
    abstract fun getKotlinVariableType(): String
    open fun getVariableName(): String {

        return when {
            typeId != null -> typeId!!.replace("\"", "")
                .replaceFirstChar { it.lowercase() }
            else -> getKotlinVariableType().replaceFirstChar { it.lowercase() }
        }
    }

    open fun getKotlinDefinitionForSealedClass(className: String): String {

        requireNotNull(typeId) {
            throw RuntimeException("Unable to define sealed class for $className for $this")
        }

        val variableName = typeName ?: getVariableName()

        val variableType = when(optionalType) {
            OptionalType.None -> getKotlinVariableType()
            OptionalType.Optional -> "${getKotlinVariableType()}?"
            OptionalType.DoubleOptional -> "List<List<${getKotlinVariableType()}?>>"
        }
        return "class $typeId(val ${variableName}: $variableType): $className()"
    }

    fun getKotlinClassDefinition(className: String): String {
        return when(this) {

            is CandidTypeVariant -> {
                val sealedClassDefinition = StringBuilder()
                sealedClassDefinition.appendLine("sealed class $className {")
                val innerClassesDefinition = candidTypes
                    .joinToString("\n") { "\t${it.getKotlinDefinitionForSealedClass(className)}" }
                sealedClassDefinition.appendLine(innerClassesDefinition)
                sealedClassDefinition.appendLine("}")

                // TODO, need to check for inner classes

                sealedClassDefinition.toString()
            }

            is CandidTypeRecord -> {
                val dataClassDefinition = StringBuilder()
                dataClassDefinition.appendLine("data class $className(")
                candidTypes.forEach {
                    val variableName = it.getVariableName()
                    val variableType = when(it.optionalType) {
                        OptionalType.None -> it.getKotlinVariableType()
                        OptionalType.Optional -> "${it.getKotlinVariableType()}?"
                        OptionalType.DoubleOptional -> "List<List<${it.getKotlinVariableType()}?>>"
                    }
                    dataClassDefinition.appendLine("\t$variableName : $variableType,")
                }
                dataClassDefinition.append(")")

                // TODO, extract and reuse
                val innerClassToDeclare = candidTypes
                    .map { it.getInnerClassesToDeclare() }
                    .flatten()
                if(innerClassToDeclare.isNotEmpty()) {
                    dataClassDefinition.appendLine(" {")
                    val kotlinInnerClasses = innerClassToDeclare.joinToString("\n") {
                        val innerClassName = it.typeName ?: TODO()
                        it.getKotlinClassDefinition(innerClassName)
                    }
                    dataClassDefinition.appendLine("\t$kotlinInnerClasses")
                    dataClassDefinition.appendLine("}")
                } else dataClassDefinition.appendLine()
                return dataClassDefinition.toString()
            }

            else -> TODO()
        }
    }

    companion object : ParserNodeDeclaration<CandidType> by subtype()
}

internal data class CandidTypeVariant(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val candidTypes: List<CandidType>
): CandidType() {

    override fun getKotlinDefinitionForSealedClass(className: String): String =
        throw RuntimeException("Unable to generate sealed class for CandidTypeVariant")

    override fun getKotlinVariableType(): String {
        TODO("Not yet implemented")
    }

    override fun getVariableName(): String {
        TODO("Not yet implemented")
    }

    companion object : ParserNodeDeclaration<CandidTypeVariant> by reflective()
}

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

internal data class CandidTypeCustom(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val typeDefinition: String
): CandidType() {

    override fun getKotlinVariableType(): String = typeDefinition

    override fun getKotlinDefinitionForSealedClass(className: String): String {
        return when {
            typeId == null -> "object $typeDefinition: $className()"
            else -> "class $typeId(val $typeDefinition : $typeDefinition): $className()"
        }
    }

    companion object : ParserNodeDeclaration<CandidTypeCustom> by reflective()
}

internal data class CandidTypeText(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinVariableType(): String = "String"

    companion object : ParserNodeDeclaration<CandidTypeText> by reflective()
}

internal data class CandidTypePrincipal(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinVariableType(): String = "ICPPrincipal"

    companion object : ParserNodeDeclaration<CandidTypePrincipal> by reflective()
}

internal data class CandidTypeInt64(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinVariableType(): String = "Long"

    companion object : ParserNodeDeclaration<CandidTypeInt64> by reflective()
}

internal data class CandidTypeNat8(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinVariableType(): String = "UByte"


    companion object : ParserNodeDeclaration<CandidTypeNat8> by reflective()
}

internal data class CandidTypeNat64(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinVariableType(): String = "ULong"

    companion object : ParserNodeDeclaration<CandidTypeNat64> by reflective()
}

internal data class CandidTypeFloat(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
): CandidType() {

    override fun getKotlinVariableType(): String = "Double"

    companion object : ParserNodeDeclaration<CandidTypeFloat> by reflective()
}

internal data class CandidTypeVec(
    override val typeId: String? = null,
    override val typeName: String? = null,
    override val optionalType: OptionalType = OptionalType.None,
    val vecType: CandidType
): CandidType() {

    override fun getKotlinVariableType(): String {
        val arrayDefinition = if(typeId == "Array") "kotlin.Array" else "Array"
        return "$arrayDefinition<${vecType.getKotlinVariableType()}>"
    }

    override fun getInnerClassesToDeclare(): List<CandidType> {
        return vecType.getInnerClassesToDeclare()
    }

    companion object : ParserNodeDeclaration<CandidTypeVec> by reflective()
}

internal data class CandidService(
    val functions: List<CandidServiceFunction>
) {

    fun getKotlinClassDefinition(): String {
        return """
            class Service(private val canister: ICPPrincipal) {
                TODO()
            }
        """.trimIndent()
    }

    companion object : ParserNodeDeclaration<CandidService> by reflective()
}

internal data class CandidServiceFunction(
    val comment: IDLComment? = null,
    val functionName: String,
    val inputArgs: List<CandidType> = emptyList(),
    val outputArgs: List<CandidType> = emptyList(),
    val functionType: CandidServiceFunctionType = CandidServiceFunctionType.Query
) {
    companion object : ParserNodeDeclaration<CandidServiceFunction> by reflective()
}

enum class CandidServiceFunctionType {
    Query,
    OneWay
}