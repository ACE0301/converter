package com.ace.converter.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ace.converter.R
import com.ace.converter.extentions.afterTextChanged
import com.ace.converter.extentions.getIntOrZero
import com.ace.converter.prefs.AppPreferencesHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    companion object {
        const val KEY_QUANTITY = "KEY_QUANTITY"
        const val KEY_SELECTION_ONE = "KEY_SELECTION_ONE"
        const val KEY_SELECTION_TWO = "KEY_SELECTION_TWO"
    }

    private lateinit var presenter: MainPresenter
    private var currencyOne: String? = null
    private var currencyTwo: String? = null
    private var quantity = 0
    private var selectionOne = 0
    private var selectionTwo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = AppPreferencesHelper(this)
        presenter = MainPresenter(this, repository)
        presenter.onCreate()

        spinner_from.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                currencyOne = parent.getItemAtPosition(pos).toString()
                if (etQuantity.text.isNotEmpty()) {
                    presenter.onItemSelected("${currencyOne}_${currencyTwo}")
                }
                quantity = etQuantity.text.toString().getIntOrZero()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinner_to.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                currencyTwo = parent.getItemAtPosition(pos).toString()
                if (etQuantity.text.isNotEmpty()) {
                    presenter.onItemSelected("${currencyOne}_${currencyTwo}")
                }
                quantity = etQuantity.text.toString().getIntOrZero()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        etQuantity.afterTextChanged {
            if (etQuantity.text.isNotEmpty()) {
                presenter.onItemSelected("${currencyOne}_${currencyTwo}")
            }
            quantity = it
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_QUANTITY, quantity)
        outState.putInt(KEY_SELECTION_ONE, spinner_from.selectedItemPosition)
        outState.putInt(KEY_SELECTION_TWO, spinner_to.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        quantity = savedInstanceState.getInt(KEY_QUANTITY)
        selectionOne = savedInstanceState.getInt(KEY_SELECTION_ONE)
        selectionTwo = savedInstanceState.getInt(KEY_SELECTION_TWO)
    }

    override fun loadCurrencies(currencies: List<String>) {
        ArrayAdapter(
                this, android.R.layout.simple_spinner_item, currencies
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_from.adapter = adapter
            spinner_from.setSelection(selectionOne)
            spinner_to.adapter = adapter
            spinner_to.setSelection(selectionTwo)
        }
    }

    override fun showLoading() {
        tvConvertFrom.visibility = View.GONE
        spinner_from.visibility = View.GONE
        tvConvertTo.visibility = View.GONE
        spinner_to.visibility = View.GONE
        tvResult.visibility = View.GONE
        etQuantity.visibility = View.GONE
        progressbar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressbar.visibility = View.GONE
        tvConvertFrom.visibility = View.VISIBLE
        spinner_from.visibility = View.VISIBLE
        tvConvertTo.visibility = View.VISIBLE
        spinner_to.visibility = View.VISIBLE
        tvResult.visibility = View.VISIBLE
        etQuantity.visibility = View.VISIBLE
    }

    override fun showResult(result: Float?) {
        if (result == 0.0f) {
            tvResult.text = getString(R.string.connection)
        } else {
            if (quantity <= 0) {
                tvResult.text = getString(R.string.input_positive)
            } else tvResult.text = "${result?.times(quantity)}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
