package com.bity.icp_kotlin_kit.plugin.file_generator

import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLType
import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_type.IDLTypeFuncDeclaration

internal object KotlinFunctionGenerator {

    operator fun invoke(
        funId: String,
        idlTypeFunc: IDLTypeFuncDeclaration
    ): String {
        TODO()
    }

    private fun getOutputParamsDeclaration(outputParams: List<IDLType>): String {
        return when(val size = outputParams.size) {
            0 -> "Unit"
            1 -> "(${KotlinClassGenerator.getCorrespondingKotlinClass(outputParams.first())})"
            else -> TODO()
        }
    }
}