package com.bity.icp_kotlin_kit.plugin.file_generator

import com.bity.icp_kotlin_kit.plugin.candid_parser.CandidServiceParser
import com.bity.icp_kotlin_kit.plugin.candid_parser.CandidVecParser
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.file_generator.KotlinClassDefinitionType
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.file_generator.KotlinClassParameter
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_file.IDLFileService
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_service.IDLService
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLFun
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLType
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeBlob
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeBoolean
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeCustom
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeFuncDeclaration
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeInt
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeNat
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeNat64
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeNull
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypePrincipal
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeRecord
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeText
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeVariant
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeVec
import com.bity.icp_kotlin_kit.plugin.candid_parser.util.CandidServiceParamParser
import com.bity.icp_kotlin_kit.plugin.candid_parser.util.ext_fun.kotlinVariableName
import com.bity.icp_kotlin_kit.plugin.file_generator.helper.IDLTypeHelper

internal class IDLFileServiceConverter(
    private val fileName: String,
    private val idlFileService: IDLFileService,
) {
    private val generatedClasses = hashMapOf<String, KotlinClassDefinitionType>()

    fun getKotlinServiceDefinition(): KotlinClassDefinitionType {
        val serviceDeclaration = CandidServiceParser.parseService( idlFileService.serviceDefinition)
        val serviceClass = KotlinClassDefinitionType.Class(
            className = "${fileName}Service"
        )
        val params = mutableListOf(
            KotlinClassParameter(
                id = "canister",
                typeVariable = "ICPPrincipal",
                isOptional = false,
                comment = KotlinCommentGenerator.getNullableKotlinComment(idlFileService.comment)
            )
        )
        serviceDeclaration.initArgsDeclaration?.let {
            TODO("Support Service init args")
        }
        serviceClass.params.addAll(params)

        val classFunctions = serviceDeclaration.services.map {
            idlServiceToKotlinClass(it)
        }
        serviceClass.innerClasses.addAll(classFunctions)
        return serviceClass
    }

    private fun idlServiceToKotlinClass(idlService: IDLService): KotlinClassDefinitionType {
        val icpQuery = KotlinClassDefinitionType.ICPQuery(
            comment = idlService.comment,
            queryName = idlService.id,
            inputParamsDeclaration = idlService.inputParamsDeclaration,
            outputParamsDeclaration = idlService.outputParamsDeclaration
        )

        val innerClasses = mutableListOf<KotlinClassDefinitionType>()
        val inputArgs = CandidServiceParamParser
            .parseServiceParam(idlService.inputParamsDeclaration)
            .params
            .map {
                kotlinClassParam(it)
            }
        icpQuery.inputArgs.addAll(inputArgs)

        val outputArgs = CandidServiceParamParser
            .parseServiceParam(idlService.outputParamsDeclaration)
            .params
            .map {
                if(shouldDeclareInnerClass(it)) {
                    println()
                }
                kotlinClassParam(it)
            }
        icpQuery.outputArgs.addAll(outputArgs)
        icpQuery.innerClasses.addAll(innerClasses)
        return icpQuery
    }

    private fun kotlinClassParam(idlType: IDLType): KotlinClassParameter {
        val typeVariable = IDLTypeHelper.kotlinTypeVariable(idlType)
        return KotlinClassParameter(
            comment = null,
            id = idlType.id ?: typeVariable.kotlinVariableName(),
            isOptional = idlType.isOptional,
            typeVariable = typeVariable
        )
    }

    private fun shouldDeclareInnerClass(idlType: IDLType): Boolean =
        when(idlType) {
            is IDLTypeInt,
            is IDLTypeNat,
            is IDLTypeNat64,
            is IDLTypeBlob,
            is IDLTypePrincipal,
            is IDLTypeBoolean,
            is IDLTypeText,
            is IDLTypeCustom -> false
            is IDLTypeRecord -> true

            is IDLFun -> TODO()
            is IDLTypeFuncDeclaration -> TODO()
            is IDLTypeVariant -> TODO()
            is IDLTypeNull -> TODO()

            is IDLTypeVec -> {
                val idlVec = CandidVecParser.parseVec(idlType.vecDeclaration)
                shouldDeclareInnerClass(idlVec.type)
            }
        }
}