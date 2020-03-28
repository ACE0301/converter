package com.ace.converter.prefs

import android.content.Context
import android.content.SharedPreferences


interface Preferences {
    fun savePairs(setPairs: Set<String>)
    fun getPairs(): Set<String>?
    fun saveResult(pairOfCurrency: String, result: Float)
    fun getResult(pairOfCurrency: String): Float?
}

class PreferencesImpl(
        context: Context?
) : Preferences {

    companion object {
        const val APP_PREFERENCES = "currencies"
        private const val DEFAULT_RESULT = 0.0f
        private val DEFAULT_PAIRS = setOf<String>()
        private const val CURRENCIES = "CURRENCIES"
    }

    private var preferences =
            context?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    private val pref: SharedPreferences? = preferences

    override fun savePairs(setPairs: Set<String>) {
        val editor = pref?.edit()
        editor?.putStringSet(CURRENCIES, setPairs)
        editor?.apply()
    }

    override fun getPairs(): Set<String>? {
        return pref?.getStringSet(CURRENCIES, DEFAULT_PAIRS)
    }

    override fun saveResult(pairOfCurrency: String, result: Float) {
        val editor = pref?.edit()
        editor?.putFloat(pairOfCurrency, result)
        editor?.apply()
    }

    override fun getResult(pairOfCurrency: String): Float? {
        return pref?.getFloat(pairOfCurrency, DEFAULT_RESULT)
    }
}