package com.bity.icp_kotlin_kit.util.ext_function

import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun ByteArray.toHexString(): String =
    joinToString(separator = "") {
        it.toInt()
            .and(0xff)
            .toString(16)
            .padStart(2, '0')
    }

fun ByteArray.toShort(): Short {
    val bb = ByteBuffer.wrap(this)
    bb.order(ByteOrder.BIG_ENDIAN)
    return bb.short
}

fun ByteArray.toInt(): Int =
    toHexString().toInt(16)

fun ByteArray?.toLong(): Long =
    if (this == null || this.isEmpty()) 0 else
        BigInteger(1, this).toLong()