package com.ace.converter.prefs


interface PreferencesHelper {

    fun saveResult(pairOfCurrency: String, result: Float)

    fun getResult(pairOfCurrency: String): Float?

}