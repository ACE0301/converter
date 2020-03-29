package com.ace.converter.entity

import com.google.gson.annotations.SerializedName

data class Currencies(
        @SerializedName("results")
        val currencies: Map<String, Currency>
)

data class Currency(
        val currencyName: String,
        val currencySymbol: String,
        val id: String
)