package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.domain.model.CandidParsedFile
import com.bity.icp_kotlin_kit.domain.service.CandidFileParserService
import com.bity.icp_kotlin_kit.domain.service.CandidTypeParserService
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.CandidType

internal class CandidFileParserServiceImpl(
    private val candidTypeParserService: CandidTypeParserService
) : CandidFileParserService {

    override fun parseCandidFile(candidContent: String): String {

        var string = candidContent.trimStart()
        val candidTypes = mutableListOf<CandidType>()

        while(string.isNotEmpty()) {

            val endIndex = getEndDeclarationIndex(string)
            val candidDeclaration = string.substring(0, endIndex)

            when {

                candidTypeParserService.isCandidTypeDefinition(candidDeclaration) -> {
                    try {
                        val candidTypeDefinition = candidTypeParserService
                            .parseCandidType(candidDeclaration)
                        candidTypes.add(candidTypeDefinition)
                    } catch (t: Throwable) {
                        println("Error parsing $candidDeclaration")
                    }
                }

                else -> throw RuntimeException("Unable to parse $candidDeclaration")
            }

            string = string.substring(endIndex).trimStart()
        }

        val candidParsedFile = CandidParsedFile(
            candidTypes = candidTypes
        )
        TODO()
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