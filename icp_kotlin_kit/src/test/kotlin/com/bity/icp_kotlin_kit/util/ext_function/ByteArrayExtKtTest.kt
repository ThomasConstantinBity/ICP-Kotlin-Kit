package com.bity.icp_kotlin_kit.util.ext_function

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ByteArrayExtKtTest {

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun toInt() {
        val value = "05".hexToByteArray()
        val expectedResult = 5
        assertEquals(expectedResult, value.toInt())
    }
}