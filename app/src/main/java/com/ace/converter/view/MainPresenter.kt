package com.ace.converter.view

import com.ace.converter.base.BasePresenter
import com.ace.converter.extentions.getCurrency
import com.ace.converter.network.ApiHolder
import com.ace.converter.prefs.PreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainPresenter(
    view: MainView,
    private val appPreferencesHelper: PreferencesHelper
) : BasePresenter<MainView>(view) {

    private var disposableGetCurrencyData: Disposable? = null

    override fun onCreate() {}

    fun updateViewInfo(currencies: String) {
        disposableGetCurrencyData?.dispose()
        disposableGetCurrencyData = ApiHolder.service.getAbrCurrencies(currencies)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.showResult(it.getCurrency(currencies))
                appPreferencesHelper.saveResult(currencies, it.getCurrency(currencies))
            }, {
                view?.showResult(appPreferencesHelper.getResult(currencies))
            })
    }

    override fun onDestroy() {
        disposableGetCurrencyData?.dispose()
    }

}