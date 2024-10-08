package com.bity.icp_kotlin_kit.util.ext_function

fun List<ByteArray>.joinedData(): ByteArray =
    if(this.isEmpty()) byteArrayOf()
    else this.reduce { acc, bytes -> acc + bytes }