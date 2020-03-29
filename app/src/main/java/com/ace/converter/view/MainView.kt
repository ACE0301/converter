package com.ace.converter.view

import com.ace.converter.entity.Currency

interface MainView {
    fun showResult(result: Float?)
    fun showCurrencies(currencies: List<String>)
    fun showLoading()
    fun hideLoading()
    fun showException(message: String?)
}
