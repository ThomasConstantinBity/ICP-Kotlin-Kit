package com.bity.icp_kotlin_kit.plugin.file_generator.helper

import com.bity.icp_kotlin_kit.plugin.candid_parser.model.idl_record.IDLRecord
import com.bity.icp_kotlin_kit.plugin.file_generator.KotlinCommentGenerator
import com.bity.icp_kotlin_kit.plugin.file_generator.helper.IDLTypeHelper.kotlinTypeVariable

internal object IDLRecordHelper {

    internal fun idlRecordToKotlinClassVariable(
        idlRecord: IDLRecord
    ): String {
        val kotlinClassVariable = StringBuilder()

        // Add comment
        idlRecord.comment?.let {
            kotlinClassVariable.append(KotlinCommentGenerator.getKotlinComment(it))
        }

        val variableDefinition = StringBuilder("val ${idlRecord.id}: ")
        variableDefinition.append(kotlinTypeVariable(idlRecord.type))
        if(idlRecord.isOptional) variableDefinition.append("?")
        kotlinClassVariable.append(variableDefinition)
        return kotlinClassVariable.toString()
    }
}