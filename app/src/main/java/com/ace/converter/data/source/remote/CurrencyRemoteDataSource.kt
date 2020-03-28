package com.ace.converter.data.source.remote

import com.ace.converter.network.ApiHolder
import com.ace.converter.model.Currencies
import io.reactivex.Single

interface CurrencyRemoteDataSource {
    fun getCurrencies(): Single<Currencies>
    fun getValueCurrencies(currencies: String): Single<Map<String, Float>>
}

class CurrencyRemoteDataSourceImpl : CurrencyRemoteDataSource {
    override fun getCurrencies(): Single<Currencies> {
        return ApiHolder.service.getCurrencies()
    }

    override fun getValueCurrencies(currencies: String): Single<Map<String, Float>> {
        return ApiHolder.service.getValueCurrencies(currencies, "ultra")
    }
}

