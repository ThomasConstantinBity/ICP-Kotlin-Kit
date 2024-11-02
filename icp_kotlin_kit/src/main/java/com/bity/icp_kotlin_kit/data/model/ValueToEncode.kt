package com.bity.icp_kotlin_kit.data.model

import kotlin.reflect.KClass

class ValueToEncode(
    val arg: Any?,
    val expectedClass: KClass<*>,
    val expectedClassNullable: Boolean
) {
    constructor(arg: Any): this(
        arg = arg,
        expectedClass = arg::class,
        expectedClassNullable = false
    )
}