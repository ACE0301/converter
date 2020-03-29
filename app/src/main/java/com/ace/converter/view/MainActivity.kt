package com.ace.converter.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ace.converter.R
import com.ace.converter.entity.Currency
import com.ace.converter.extentions.afterTextChanged
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
        presenter = MainPresenter(this)
        presenter.onCreate()

        spinner_from.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                currencyOne = parent.getItemAtPosition(pos).toString()
                onDataChanged("${currencyOne}_${currencyTwo}")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinner_to.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                currencyTwo = parent.getItemAtPosition(pos).toString()
                onDataChanged("${currencyOne}_${currencyTwo}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        etQuantity.afterTextChanged {
            quantity = it
            onDataChanged("${currencyOne}_${currencyTwo}")
        }
    }

    private fun onDataChanged(currencies: String) {
        if (quantity > 0) {
            presenter.onDataChanged(currencies)
        } else tvResult.text = ""
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

    override fun showCurrencies(currencies: List<String>) {
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

    override fun showResult(result: Float?) {
        if (result == 0.0f) {
            tvResult.text = getString(R.string.connection)
        } else {
            tvResult.text = "${result?.times(quantity)}"
        }
    }

    override fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressbar.visibility = View.GONE
    }

    override fun showException(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
