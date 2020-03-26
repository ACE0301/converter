package com.ace.converter.view

interface MainView {
    fun showResult(result: Float?)
    fun loadCurrencies(currencies: List<String>)
    fun showLoading()
    fun hideLoading()
}
