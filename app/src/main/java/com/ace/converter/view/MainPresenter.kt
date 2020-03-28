package com.ace.converter.view

import com.ace.converter.data.repository.CurrencyRepository
import com.ace.converter.data.repository.CurrencyRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
        private val view: MainView,
        private val repository: CurrencyRepository = CurrencyRepositoryImpl()
) {

    private var disposableLoadCurrencies: Disposable? = null
    private var disposableGetCurrencyData: Disposable? = null

    fun onCreate() {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        disposableLoadCurrencies?.dispose()
        disposableLoadCurrencies = repository.getCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doFinally { view.hideLoading() }
                .subscribe({
                    view.loadCurrencies(it.currencies.keys.toList())
                    repository.savePairs(it.currencies.keys)
                }, {
                    it.message
                    repository.getPairs()?.toList()
                            ?.let { list -> view.loadCurrencies(list) }
                })
    }

    fun onDataChanged(currencies: String) {
        disposableGetCurrencyData?.dispose()
        disposableGetCurrencyData = repository.getValueCurrencies(currencies)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doFinally { view.hideLoading() }
                .subscribe({
                    it[currencies]?.let { value -> repository.saveResult(currencies, value) }
                    view.showResult(it[currencies])
                }, {
                    view.showResult(repository.getResult(currencies))
                })
    }

    fun onDestroy() {
        disposableLoadCurrencies?.dispose()
        disposableGetCurrencyData?.dispose()
    }
}