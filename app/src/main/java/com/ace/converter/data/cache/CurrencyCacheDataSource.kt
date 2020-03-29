package com.ace.converter.data.cache

import com.ace.converter.ConverterApp
import com.ace.converter.database.CurrencyDatabase
import com.ace.converter.database.DBCurrency
import com.ace.converter.database.DBCurrencyResult
import io.reactivex.Single

interface CurrencyCacheDataSource {
    fun saveCurrencies(currencies: List<DBCurrency>)
    fun getCurrencies(): Single<List<DBCurrency>>
    fun saveResult(result: DBCurrencyResult)
    fun getResult(pair: String): Single<DBCurrencyResult>
}

class CurrencyCacheDataSourceImpl: CurrencyCacheDataSource {
    override fun saveCurrencies(currencies: List<DBCurrency>) {
        CurrencyDatabase.getInstance(ConverterApp.getAppContext()).currencyDatabase.clear()
        CurrencyDatabase.getInstance(ConverterApp.getAppContext()).currencyDatabase.insert(currencies)
    }

    override fun getCurrencies(): Single<List<DBCurrency>> {
        return CurrencyDatabase.getInstance(ConverterApp.getAppContext()).currencyDatabase.getAllCurrencies()
    }

    override fun saveResult(result: DBCurrencyResult) {
        CurrencyDatabase.getInstance(ConverterApp.getAppContext()).currencyDatabaseResult.clear()
        CurrencyDatabase.getInstance(ConverterApp.getAppContext()).currencyDatabaseResult.insert(result)
    }

    override fun getResult(pair: String): Single<DBCurrencyResult> {
        return CurrencyDatabase.getInstance(ConverterApp.getAppContext()).currencyDatabaseResult.get(pair)
    }
}