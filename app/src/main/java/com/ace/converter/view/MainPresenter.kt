package com.ace.converter.view

import com.ace.converter.Utils
import com.ace.converter.domain.CurrencyInteractor
import com.ace.converter.domain.CurrencyInteractorImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
        private val view: MainView,
        private val interactor: CurrencyInteractor = CurrencyInteractorImpl()
) {

    private var disposableGetCurrencies: Disposable? = null
    private var disposableGetCurrencyData: Disposable? = null

    fun onCreate() {
        getCurrencies()
    }

    private fun getCurrencies() {
        disposableGetCurrencies = interactor.getCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doFinally { view.hideLoading() }
                .subscribe({
                    view.showCurrencies(it)
                }, {
                    view.showException(it.message)
                })
    }

    fun onDataChanged(currencies: String) {
        disposableGetCurrencyData?.dispose()
        disposableGetCurrencyData = interactor.getValueCurrencies(currencies, Utils.isNetworkConnected())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doFinally { view.hideLoading() }
                .subscribe({
                    //it?.let { value -> interactor.saveResult(currencies, value) }
                    view.showResult(it)
                }, {
                    view.showException(it.message)
                    //view.showResult(repository.getResult(currencies))
                })
    }

    fun onDestroy() {
        disposableGetCurrencies?.dispose()
        disposableGetCurrencyData?.dispose()
    }
}