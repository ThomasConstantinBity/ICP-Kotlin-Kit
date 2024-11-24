package com.bity.icp_kotlin_kit.file_parser.candid_parser

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.CandidParsedType
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.IDLFileDeclaration
import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_type.CandidService

// TODO, add support for end of line comment in order to support multiple comment
// type QueryArchiveResult = variant {
//    Err : null;      // we don't know the values here...
//    // A new line comment
//    Ok : BlockRange;
//
//};

internal object CandidFileParser {


    fun parseFile(input: String): IDLFileDeclaration {

        var service: CandidService? = null
        val candidParsedTypes = mutableListOf<CandidParsedType>()

        var string = input.trimStart()
        while (string.isNotEmpty()) {
            when {

                string.startsWith("type") -> {
                    val typeDefinitionEndIndex = getEndDeclarationIndex(string)
                    val typeDefinition = string.substring(0, typeDefinitionEndIndex)
                    val candidTypeDefinition = CandidTypeParser.parseCandidType(typeDefinition)
                    candidParsedTypes.add(
                        CandidParsedType(
                            candidTypeDefinition = candidTypeDefinition,
                            candidDefinition = typeDefinition
                        )
                    )
                    string = string.substring(typeDefinitionEndIndex).trimStart()
                }

                string.startsWith("service") -> {
                    val serviceDefinitionEndIndex = getEndDeclarationIndex(string)
                    val serviceDeclaration = string.substring(0, serviceDefinitionEndIndex)
                    service = CandidServiceParser.parseCandidService(serviceDeclaration)
                    string = string.substring(serviceDefinitionEndIndex).trimStart()
                }

                else -> throw RuntimeException("Unable to parse $string")
            }

        }

        return IDLFileDeclaration(
            candidParsedTypes = candidParsedTypes,
            service = service
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

