package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.domain.model.candid_file.CandidParsedFile
import com.bity.icp_kotlin_kit.domain.model.candid_file.CandidParsedType
import com.bity.icp_kotlin_kit.domain.service.CandidFileParserService
import com.bity.icp_kotlin_kit.domain.service.CandidTypeParserService

internal class CandidFileParserServiceImpl(
    private val candidTypeParserService: CandidTypeParserService
) : CandidFileParserService {

    override fun parseCandidFile(candidContent: String): CandidParsedFile {

        var string = candidContent.trimStart()

        val candidParsedTypes = mutableListOf<CandidParsedType>()

        while(string.isNotEmpty()) {
            val endIndex = getEndDeclarationIndex(string)
            val candidDeclaration = string.substring(0, endIndex)
            try {
                val candidTypeDefinition = candidTypeParserService
                    .parseCandidType(candidDeclaration)
                val candidParsedType = CandidParsedType(
                    candidDefinition = candidDeclaration,
                    candidTypeDefinition = candidTypeDefinition
                )
                candidParsedTypes.add(candidParsedType)
            } catch (t: Throwable) {
                println("Error parsing $candidDeclaration")
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