package com.bity.icp_kotlin_kit.util.ext_function

internal fun String.idToClassName(): String =
    split("_")
        .joinToString("") { splitName ->
            splitName.replaceFirstChar { it.uppercase() }
        }

internal fun String.idToVariableName(): String =
    split("_")
        .joinToString("") { splitName ->
            splitName.replaceFirstChar { it.uppercase() }
        }
        .replaceFirstChar { it.lowercase() }

internal fun String.grouped(separator: String, groupLength: Int): String {
    val stringBuilder = StringBuilder()
    forEachIndexed { index, char ->
        if(index != 0 && index % groupLength == 0) {
            stringBuilder.append(separator)
        }
        stringBuilder.append(char)
    }
    return stringBuilder.toString()
}

internal fun String.fromHex(): ByteArray? {
    require(isValidHex() && length % 2 == 0) {
        return null
    }
    return getByteArray()
}

@Throws(NumberFormatException::class)
internal fun String.getByteArray(): ByteArray {
    var hexWithoutPrefix = this.stripHexPrefix()
    if (hexWithoutPrefix.length % 2 == 1) {
        hexWithoutPrefix = "0$hexWithoutPrefix"
    }
    return hexWithoutPrefix.chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}

internal fun String.stripHexPrefix(): String =
    if (this.startsWith("0x", true)) {
        this.substring(2)
    } else { this }

internal fun String.isValidHex(): Boolean =
    matches(Regex("^(0[xX])?[0-9a-fA-F]{2,}$"))