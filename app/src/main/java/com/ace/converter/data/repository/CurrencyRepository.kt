package com.ace.converter.data.repository

import com.ace.converter.data.source.cache.CurrencyCacheDataSource
import com.ace.converter.data.source.cache.CurrencyCacheDataSourceImpl
import com.ace.converter.data.source.remote.CurrencyRemoteDataSource
import com.ace.converter.data.source.remote.CurrencyRemoteDataSourceImpl
import com.ace.converter.model.Currencies
import io.reactivex.Single

interface CurrencyRepository {
    fun getCurrencies(): Single<Currencies>
    fun getValueCurrencies(currencies: String): Single<Map<String, Float>>
    fun savePairs(setPairs: Set<String>)
    fun getPairs(): Set<String>?
    fun saveResult(pairOfCurrency: String, result: Float)
    fun getResult(pairOfCurrency: String): Float?
}

class CurrencyRepositoryImpl(
        private val remout: CurrencyRemoteDataSource = CurrencyRemoteDataSourceImpl(),
        private val cache: CurrencyCacheDataSource = CurrencyCacheDataSourceImpl()
) : CurrencyRepository {
    override fun getCurrencies(): Single<Currencies> {
        return remout.getCurrencies()
    }

    override fun getValueCurrencies(currencies: String): Single<Map<String, Float>> {
        return remout.getValueCurrencies(currencies)
    }

    override fun savePairs(setPairs: Set<String>) {
        return cache.savePairs(setPairs)
    }

    override fun getPairs(): Set<String>? {
        return cache.getPairs()
    }

    override fun saveResult(pairOfCurrency: String, result: Float) {
        return cache.saveResult(pairOfCurrency, result)
    }

    override fun getResult(pairOfCurrency: String): Float? {
        return cache.getResult(pairOfCurrency)
    }
}