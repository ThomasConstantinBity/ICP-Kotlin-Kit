package com.bity.icp_kotlin_kit.file_parser.candid_parser

import com.bity.icp_kotlin_kit.file_parser.candid_parser.model.idl_file.IDLFileDeclaration

// TODO, add support for end of line comment in order to support multiple comment
// type QueryArchiveResult = variant {
//    Err : null;      // we don't know the values here...
//    // A new line comment
//    Ok : BlockRange;
//
//};

internal object CandidFileParser {


    fun parseFile(input: String): IDLFileDeclaration {

        // TODO check if input starts with comment

        var string = input.trimStart()
        val comments: List<String> = mutableListOf()
        while (string.isNotEmpty()) {
            when {

                string.startsWith("type") -> {
                    val typeDefinitionEndIndex = getEndDeclarationIndex(string)
                    val typeDefinition = string.substring(0, typeDefinitionEndIndex)
                    CandidTypeParser.parseCandidType(typeDefinition)
                    string = string.substring(typeDefinitionEndIndex).trimStart()
                }

                string.startsWith("service") -> {
                    val serviceDefinitionEndIndex = getEndDeclarationIndex(string)
                    val serviceDeclaration = string.substring(0, serviceDefinitionEndIndex)
                    CandidServiceParser.parseCandidService(serviceDeclaration)
                    string = string.substring(serviceDefinitionEndIndex).trimStart()
                }

                else -> throw RuntimeException("Unable to parse $string")
            }

        }

        TODO()

        // debug(input)
        // return fileParser.parse(fileLexer.tokenize(input))
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

