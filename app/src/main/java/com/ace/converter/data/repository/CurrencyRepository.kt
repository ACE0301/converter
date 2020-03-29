package com.ace.converter.data.repository

import com.ace.converter.Utils
import com.ace.converter.data.cache.CurrencyCacheDataSource
import com.ace.converter.data.cache.CurrencyCacheDataSourceImpl
import com.ace.converter.data.server.CurrencyRemoteDataSource
import com.ace.converter.data.server.CurrencyRemoteDataSourceImpl
import com.ace.converter.database.DBCurrency
import com.ace.converter.database.DBCurrencyResult
import io.reactivex.Single

interface CurrencyRepository {
    fun getCurrencies(): Single<List<String>>
    fun getValueCurrencies(currencies: String, fromApi: Boolean): Single<Float>
}

class CurrencyRepositoryImpl(
        private val remout: CurrencyRemoteDataSource = CurrencyRemoteDataSourceImpl(),
        private val cache: CurrencyCacheDataSource = CurrencyCacheDataSourceImpl(),
        private val mapper: CurrencyMapper = CurrencyMapperImpl()
) : CurrencyRepository {

    override fun getCurrencies(): Single<List<String>> {
        return if (Utils.isNetworkConnected()) {
            remout.getCurrencies()
                    .flatMap { currencies ->
                        cache.saveCurrencies(currencies.currencies.map { entry -> DBCurrency(currency = entry.value.id) })
                        Single.just(currencies)
                    }
                    .map(mapper::mapCurrenciesResponseToCurrenciesPresentModel)
        } else return cache.getCurrencies().map(mapper::mapDBCurrencyToCurrencyPresentModel)
    }

    override fun getValueCurrencies(currencies: String, fromApi: Boolean): Single<Float> {
        return if (fromApi) {
            remout.getValueCurrencies(currencies)
                    .flatMap { result ->
                        cache.saveResult(DBCurrencyResult(pair = result.keys.first(), result = result.values.first()))
                        Single.just(result)
                    }
                    .map(mapper::mapCurrenciesResultResponseToCurrenciesResultPresentModel)
        } else return cache.getResult(currencies).map { t -> t.result }
    }
}