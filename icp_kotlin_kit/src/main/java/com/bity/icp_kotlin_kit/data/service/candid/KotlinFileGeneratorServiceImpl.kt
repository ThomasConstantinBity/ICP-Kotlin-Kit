package com.bity.icp_kotlin_kit.data.service.candid

import com.bity.icp_kotlin_kit.domain.service.CandidFileParserService
import com.bity.icp_kotlin_kit.domain.service.KotlinFileGeneratorService

internal class KotlinFileGeneratorServiceImpl(
    private val candidFileParserService: CandidFileParserService
) : KotlinFileGeneratorService {

    private val imports = """
        import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
        import com.bity.icp_kotlin_kit.data.model.ValueToEncode
        import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
        import com.bity.icp_kotlin_kit.data.repository.ICPQuery
        import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
        import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
        import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
        import com.bity.icp_kotlin_kit.domain.request.PollingValues
    """.trimIndent()

    override fun parseAndGetKotlinFile(
        candidFileText: String,
        packageName: String
    ): String {
        val kotlinFile = StringBuilder()
        kotlinFile.appendLine(packageName)
        kotlinFile.appendLine()
        kotlinFile.appendLine(imports)
        kotlinFile.appendLine()

        val candidParsedFile = candidFileParserService.parseCandidFile(candidFileText)
        kotlinFile.appendLine(candidParsedFile.getTypealiasesDefinition())
        return kotlinFile.toString()
    }

}