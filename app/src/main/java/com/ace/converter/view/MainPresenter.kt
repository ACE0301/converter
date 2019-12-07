package com.ace.converter.view

import com.ace.converter.base.BasePresenter
import com.ace.converter.network.ApiHolder
import com.ace.converter.prefs.PreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    view: MainView,
    private val appPreferencesHelper: PreferencesHelper
) : BasePresenter<MainView>(view) {

    private var disposableLoadCurrencies: Disposable? = null
    private var disposableGetCurrencyData: Disposable? = null

    override fun onCreate() {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        disposableLoadCurrencies?.dispose()
        disposableLoadCurrencies = ApiHolder.service.getCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.loadCurrencies(it.currencies.keys.toList())
                appPreferencesHelper.savePairs(it.currencies.keys)
            }, {
                it.message
                appPreferencesHelper.getPairs()?.toList()
                    ?.let { list -> view?.loadCurrencies(list) }
            })
    }

    fun updateViewInfo(currencies: String) {
        disposableGetCurrencyData?.dispose()
        disposableGetCurrencyData = ApiHolder.service.getValueCurrencies(currencies, "ultra")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.showResult(it[currencies])
                it[currencies]?.let { value -> appPreferencesHelper.saveResult(currencies, value) }
            }, {
                view?.showResult(appPreferencesHelper.getResult(currencies))
            })
    }

    override fun onDestroy() {
        disposableLoadCurrencies?.dispose()
        disposableGetCurrencyData?.dispose()
    }
}