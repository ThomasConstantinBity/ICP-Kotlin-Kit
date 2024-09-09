package com.bity.icp_kotlin_kit.plugin.candid_parser.model.file_generator

import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLType
import com.bity.icp_kotlin_kit.plugin.file_generator.helper.IDLTypeHelper

// TODO remove =  null
internal sealed class KotlinClassDefinitionType(
    val name: String,
    val innerClasses: MutableList<KotlinClassDefinitionType> = mutableListOf()
) {

    class TypeAlias(
        val typeAliasId: String,
        val className: String?,
        val type: IDLType
    ): KotlinClassDefinitionType(typeAliasId) {
        override fun kotlinDefinition(): String =
            "typealias $typeAliasId = ${IDLTypeHelper.kotlinTypeVariable(type, className)}"
    }

    /*class Array(
        val isOptional: Boolean,
        val customContainedClass: KotlinClassDefinitionType?
    ): KotlinClassDefinitionType("Array") {
        override fun kotlinDefinition(): String {
            return "Array<TODO()>"
        }
    }*/

    class Function(
        val functionName: String,
        val inputArgs: List<KotlinClassDefinitionType>,
        val outputArgs: List<KotlinClassDefinitionType>
    ): KotlinClassDefinitionType(functionName) {

        override fun kotlinDefinition(): String {
            return "typealias $functionName = () -> UInt // TODO"
        }
    }

    class SealedClass(
        val className: String,
    ): KotlinClassDefinitionType(className) {

        var inheritedClass: KotlinClassDefinitionType? = null
        // val innerClasses = mutableListOf<KotlinClassDefinitionType>()

        override fun kotlinDefinition(): String {
            return """
                sealed class $className {
                    ${innerClasses.joinToString("\n") { it.kotlinDefinition() }}
                    
                    companion object {
                        internal fun init(candidValue: CandidValue): $className = TODO()
                    }
                }
            """.trimIndent()
        }
    }

    class Object(
        val objectName: String,
        // parent: KotlinClassDefinitionType?
    ): KotlinClassDefinitionType(objectName) {

        var inheritedClass: KotlinClassDefinitionType? = null

        override fun kotlinDefinition(): String {
            return inheritedClass?.let {
                "data object $objectName : ${it.name}()"
            } ?: "object $objectName"
        }
    }

    class Class(
        val className: String,
    ): KotlinClassDefinitionType(className) {

        var params: MutableList<KotlinClassParameter> = mutableListOf()
        var inheritedClass: KotlinClassDefinitionType? = null

        override fun kotlinDefinition(): String {
            val kotlinDefinition = StringBuilder("data class $className(")
            kotlinDefinition.appendLine(
                params.joinToString(
                    separator = ",\n",
                    prefix = "\n"
                ) { it.kotlinDefinition() }
            )

            val closingLine = inheritedClass?.let {
                ") : ${it.name}() {\n"
            } ?: ") {\n"
            kotlinDefinition.appendLine(closingLine)

            // Add inner classes
            innerClasses
                .filter { it !is TypeAlias }
                .forEach {
                kotlinDefinition.appendLine(it.kotlinDefinition())
            }

            // add internal constructor
            kotlinDefinition.appendLine(
                "internal constructor(candidRecord: CandidValue.Record): this("
            )
            kotlinDefinition.appendLine(
                params.joinToString(",\n") { it.kotlinVariableConstructor() }
            )

            kotlinDefinition.appendLine(")")
            kotlinDefinition.appendLine("}")
            return kotlinDefinition.toString()
        }

    }

    open fun kotlinDefinition(): String = TODO()
}