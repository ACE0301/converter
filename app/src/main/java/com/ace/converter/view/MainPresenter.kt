package com.ace.converter.view

import com.ace.converter.network.ApiHolder
import com.ace.converter.prefs.PreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
        private val view: MainView,
        private val appPreferencesHelper: PreferencesHelper
) {

    private var disposableLoadCurrencies: Disposable? = null
    private var disposableGetCurrencyData: Disposable? = null

    fun onCreate() {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        disposableLoadCurrencies?.dispose()
        disposableLoadCurrencies = ApiHolder.service.getCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.loadCurrencies(it.currencies.keys.toList())
                    appPreferencesHelper.savePairs(it.currencies.keys)
                }, {
                    it.message
                    appPreferencesHelper.getPairs()?.toList()
                            ?.let { list -> view.loadCurrencies(list) }
                })
    }

    fun updateViewInfo(currencies: String) {
        disposableGetCurrencyData?.dispose()
        disposableGetCurrencyData = ApiHolder.service.getValueCurrencies(currencies, "ultra")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showResult(it[currencies])
                    it[currencies]?.let { value -> appPreferencesHelper.saveResult(currencies, value) }
                }, {
                    view.showResult(appPreferencesHelper.getResult(currencies))
                })
    }

    fun onDestroy() {
        disposableLoadCurrencies?.dispose()
        disposableGetCurrencyData?.dispose()
    }
}