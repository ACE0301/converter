package com.ace.converter.prefs

import io.reactivex.Completable
import io.reactivex.Single

interface PreferencesHelper {

    fun saveResult(pairOfCurrency: String, result: Float)

    fun getResult(pairOfCurrency: String): Float?

}