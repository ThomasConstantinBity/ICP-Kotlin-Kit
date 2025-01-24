package com.bity.icp_kotlin_kit.domain.model.enum

import com.bity.icp_kotlin_kit.data.model.error.DABTokenException

enum class ICPTokenStandard {
    DIP20,
    XTC,
    WICP,
    EXT,
    ICP,
    ROSETTA,
    ICRC1,
    ICRC2,
    DRC20;

    companion object {
        fun valueFromString(string: String): ICPTokenStandard =
            when(string.uppercase()) {
                "DIP20" -> DIP20
                "XTC" -> XTC
                "WICP" -> WICP
                "EXT" -> EXT
                "ICP" -> ICP
                "ROSETTA" -> ROSETTA
                "ICRC1" -> ICRC1
                "ICRC2" -> ICRC2
                "DRC20" -> DRC20
                else -> throw DABTokenException.WrongTokenStandard(string)
            }
    }
}