package com.bity.icp_kotlin_kit.util.ext_function

import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

val Long.bytes: ByteArray
    get() = ByteBuffer
        .allocate(Long.SIZE_BYTES)
        .order(ByteOrder.LITTLE_ENDIAN)
        .putLong(this)
        .array()

fun Long.Companion.readFrom(stream: InputStream): Long {
    val byteArray = ByteArray(SIZE_BYTES)
    stream.read(byteArray, 0, SIZE_BYTES)
    byteArray.reverse()
    return byteArray.toLong()
}

internal fun Long.toICPTimestamp(): ULong =
    toULong().times(1_000_000UL)