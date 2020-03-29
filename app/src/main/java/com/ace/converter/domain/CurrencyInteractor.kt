package com.ace.converter.domain

import com.ace.converter.data.repository.CurrencyRepository
import com.ace.converter.data.repository.CurrencyRepositoryImpl
import io.reactivex.Single

interface CurrencyInteractor {
    fun getCurrencies(): Single<List<String>>
    fun getValueCurrencies(pair: String, fromApi: Boolean): Single<Float>
}

class CurrencyInteractorImpl(
        private val repository: CurrencyRepository = CurrencyRepositoryImpl()
) : CurrencyInteractor {

    override fun getCurrencies(): Single<List<String>> {
        return repository.getCurrencies()
    }

    override fun getValueCurrencies(pair: String, fromApi: Boolean): Single<Float> {
        return repository.getValueCurrencies(pair, fromApi)
    }
}