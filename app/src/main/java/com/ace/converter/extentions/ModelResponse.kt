package com.ace.converter.extentions

import com.ace.converter.model.ModelResponce

fun ModelResponce.getCurrency(currency: String): Float {
    return when (currency) {
        "EUR_RUB" -> EUR_RUB
        "EUR_USD" -> EUR_USD
        "USD_RUB" -> USD_RUB
        "USD_EUR" -> USD_EUR
        "RUB_EUR" -> RUB_EUR
        "RUB_USD" -> RUB_USD
        else -> 1.0f
    }
}