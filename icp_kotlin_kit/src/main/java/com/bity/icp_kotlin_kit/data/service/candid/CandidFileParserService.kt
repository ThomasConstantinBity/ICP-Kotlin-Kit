package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.domain.model.candid_file.CandidParsedFile
import com.bity.icp_kotlin_kit.domain.model.candid_file.CandidParsedType
import com.bity.icp_kotlin_kit.util.logger.ICPKitLogger

internal object CandidFileParserService {

    fun parseCandidFile(candidContent: String): CandidParsedFile {

        var string = candidContent.trimStart()

        val candidParsedTypes = mutableListOf<CandidParsedType>()

        while(string.isNotEmpty()) {
            val endIndex = getEndDeclarationIndex(string)
            val candidDeclaration = string.substring(0, endIndex)
            try {
                val candidTypeDefinition = CandidTypeParserService
                    .parseCandidType(candidDeclaration)
                val candidParsedType = CandidParsedType(
                    candidDefinition = candidDeclaration,
                    candidTypeDefinition = candidTypeDefinition
                )
                candidParsedTypes.add(candidParsedType)
            } catch (t: Throwable) {
                ICPKitLogger.logError("Error parsing $candidDeclaration", t)
            }
            string = string.substring(endIndex).trimStart()
        }

        return CandidParsedFile(
            candidParsedTypes = candidParsedTypes
        )

    }

    private fun getEndDeclarationIndex(string: String): Int {
        var brackets = 0
        string.forEachIndexed { index, char ->
            when {
                char == ';' && brackets == 0 -> return index + 1
                char == '{' -> brackets++
                char == '}' -> brackets--
                else -> { }
            }
        }
        return string.length
    }

}