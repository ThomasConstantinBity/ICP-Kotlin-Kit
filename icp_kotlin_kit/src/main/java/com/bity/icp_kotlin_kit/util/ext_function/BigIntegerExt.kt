package com.bity.icp_kotlin_kit.util.ext_function

import java.math.BigInteger
import java.nio.ByteBuffer

internal fun BigInteger.to32Bits(): ByteArray {
    val buffer = ByteBuffer.allocate(4)
    buffer.putInt(this.toInt())
    return buffer.array()
}