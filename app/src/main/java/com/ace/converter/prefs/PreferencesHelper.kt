package com.ace.converter.prefs


interface PreferencesHelper {

    fun savePairs(setPairs: Set<String>)

    fun getPairs(): Set<String>?

    fun saveResult(pairOfCurrency: String, result: Float)

    fun getResult(pairOfCurrency: String): Float?

}