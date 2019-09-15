package com.ace.converter.extentions

fun String.getInt(): Int {
    return when (this) {
        "" -> 0
        else -> Integer.parseInt(this)
    }
}
