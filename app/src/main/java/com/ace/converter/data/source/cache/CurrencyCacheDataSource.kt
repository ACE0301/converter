package com.ace.converter.data.source.cache

import com.ace.converter.ConverterApp
import com.ace.converter.prefs.Preferences
import com.ace.converter.prefs.PreferencesImpl

interface CurrencyCacheDataSource {
    fun savePairs(setPairs: Set<String>)
    fun getPairs(): Set<String>?
    fun saveResult(pairOfCurrency: String, result: Float)
    fun getResult(pairOfCurrency: String): Float?
}

class CurrencyCacheDataSourceImpl(
        private val pref: Preferences = PreferencesImpl(ConverterApp.getAppContext())
) : CurrencyCacheDataSource {
    override fun savePairs(setPairs: Set<String>) {
        pref.savePairs(setPairs)
    }

    override fun getPairs(): Set<String>? {
        return pref.getPairs()
    }

    override fun saveResult(pairOfCurrency: String, result: Float) {
        pref.saveResult(pairOfCurrency, result)
    }

    override fun getResult(pairOfCurrency: String): Float? {
        return pref.getResult(pairOfCurrency)
    }
}