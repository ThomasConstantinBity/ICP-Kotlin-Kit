package com.bity.icp_kotlin_kit.data.model

import kotlin.reflect.KClass

class ValueToEncode(
    val arg: Any?,
    val expectedClass: KClass<*>,
    val arrayType: KClass<*>? = null,
    val arrayTypeNullable: Boolean = false,
    val expectedClassNullable: Boolean
) {
    constructor(
        arg: Any,
        arrayType: KClass<*>? = null,
        arrayTypeNullable: Boolean = false
    ): this(
        arg = arg,
        expectedClass = arg::class,
        arrayType = arrayType,
        arrayTypeNullable = arrayTypeNullable,
        expectedClassNullable = false
    )
}