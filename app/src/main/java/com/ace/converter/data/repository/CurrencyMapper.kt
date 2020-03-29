package com.ace.converter.data.repository

import com.ace.converter.database.DBCurrency
import com.ace.converter.entity.Currencies

interface CurrencyMapper {
    fun mapCurrenciesResponseToCurrenciesPresentModel(currencies: Currencies): List<String>
    fun mapCurrenciesResultResponseToCurrenciesResultPresentModel(map: Map<String, Float>): Float
    fun mapDBCurrencyToCurrencyPresentModel(currencies: List<DBCurrency>): List<String>
}

class CurrencyMapperImpl : CurrencyMapper {
    override fun mapCurrenciesResponseToCurrenciesPresentModel(currencies: Currencies): List<String> {
        return currencies.currencies.map { entry -> entry.value.id }
    }

    override fun mapCurrenciesResultResponseToCurrenciesResultPresentModel(map: Map<String, Float>): Float {
        return map.values.first()
    }

    override fun mapDBCurrencyToCurrencyPresentModel(currencies: List<DBCurrency>): List<String> {
        return currencies.map { dbCurrency -> dbCurrency.currency }
    }
}