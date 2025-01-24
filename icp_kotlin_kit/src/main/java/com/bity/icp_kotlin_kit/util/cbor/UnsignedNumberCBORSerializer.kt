package com.bity.icp_kotlin_kit.util.cbor

object UnsignedNumberCBORSerializer {

    /**
     * Positive Integer (0-23):
     *  Tag: None (No tag needed for integers from 0 to 23).
     *
     * One-Byte Unsigned Integer (24-255):
     *  Tag: 0x18
     *
     * Two-Byte Unsigned Integer (256-65535):
     *  Tag: 0x19
     *
     * Four-Byte Unsigned Integer (65536-4294967295):
     *  Tag: 0x1A
     *
     * Eight-Byte Unsigned Integer (4294967296 and above):
     *  Tag: 0x1B
     *
     */
    fun serialize(value: ULong): String =
        when (value) {
            in 0UL .. 23UL -> value.toString(16).padStart(2, '0')
            in 24UL .. 255UL -> "18${value.toString(16).padStart(2, '0')}"
            in 256UL .. 65535UL -> "19${value.toString(16).padStart(4, '0')}"
            in 65536UL .. 4294967295UL -> "1a${value.toString(16).padStart(8, '0')}"
            else -> "1b${value.toString(16).padStart(16, '0')}"
        }
}