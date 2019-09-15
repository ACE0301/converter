package com.ace.converter.extentions


fun String.getIntOrZero(): Int {
    return when (this) {
        "" -> 0
        else -> Integer.parseInt(this)
    }
}
