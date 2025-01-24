package com.bity.icp_kotlin_kit.util.ext_function

import java.io.InputStream
import java.util.Date

// Little-endian = Least significant byte first
internal val ULong.bytes: ByteArray
    get() = this.toLong().bytes

internal fun ULong.Companion.readFrom(stream: InputStream): ULong {
    val byteArray = ByteArray(SIZE_BYTES)
    stream.read(byteArray, 0, SIZE_BYTES)
    byteArray.reverse()
    return byteArray.toLong().toULong()
}

fun ULong.timestampNanosToDate(): Date =
    Date(this.div(1_000_000UL).toLong())