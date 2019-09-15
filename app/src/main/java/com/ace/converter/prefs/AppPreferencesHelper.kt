package com.ace.converter.prefs

import android.content.Context
import android.content.SharedPreferences
import com.ace.converter.base.context.ContextDelegate


class AppPreferencesHelper(
    contextDelegate: ContextDelegate
) : PreferencesHelper {

    companion object {
        val APP_PREFERENCES = "mysettings"
        private const val DEFAULT_RESULT = 0.0f
    }

    private var preferences = contextDelegate.getContext()?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    var mSettings: SharedPreferences? = preferences

    override fun saveResult(pairOfCurrency: String, result: Float) {
        val editor = mSettings?.edit()
        editor?.putFloat(pairOfCurrency, result)
        editor?.apply()
    }

    override fun getResult(pairOfCurrency: String): Float? {
        return mSettings?.getFloat(pairOfCurrency, DEFAULT_RESULT)
    }
}