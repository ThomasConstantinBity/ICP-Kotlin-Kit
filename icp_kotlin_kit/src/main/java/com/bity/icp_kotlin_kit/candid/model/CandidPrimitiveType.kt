package com.bity.icp_kotlin_kit.candid.model

enum class CandidPrimitiveType(
    val value: Int
) {
    NULL(-1),
    BOOL(-2),
    NATURAL(-3),
    INTEGER(-4),
    NATURAL8(-5),
    NATURAL16(-6),
    NATURAL32(-7),
    NATURAL64(-8),
    INTEGER8(-9),
    INTEGER16(-10),
    INTEGER32(-11),
    INTEGER64(-12),
    FLOAT32(-13),
    FLOAT64(-14),
    TEXT(-15),
    RESERVED(-16),
    EMPTY(-17),
    OPTION(-18),
    VECTOR(-19),
    RECORD(-20),
    VARIANT(-21),
    FUNCTION(-22),
    SERVICE(-23),
    PRINCIPAL(-24);

    companion object {
        fun candidPrimitiveTypeByValue(value: Int): CandidPrimitiveType? =
            entries.find { it.value == value }
    }
}