package com.ace.converter.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ace.converter.R
import com.ace.converter.base.context.ContextDelegateFactory
import com.ace.converter.extentions.afterTextChanged
import com.ace.converter.extentions.getIntOrZero
import com.ace.converter.prefs.AppPreferencesHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView {

    private val KEY_COUNT = "key_count"
    private lateinit var presenter: MainPresenter
    private var currencyOne: String? = null
    private var currencyTwo: String? = null
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = AppPreferencesHelper(ContextDelegateFactory.create(this))
        presenter = MainPresenter(this, repository)

        ArrayAdapter.createFromResource(
            this, R.array.currencies, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_from.adapter = adapter
            spinner_to.adapter = adapter
        }
        spinner_to.setSelection(2)
        spinner_from.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                currencyOne = parent.getItemAtPosition(pos).toString()
                presenter.updateViewInfo("${currencyOne}_${currencyTwo}")
                count = etQuantity.text.toString().getIntOrZero()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinner_to.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                currencyTwo = parent.getItemAtPosition(pos).toString()
                presenter.updateViewInfo("${currencyOne}_${currencyTwo}")
                count = etQuantity.text.toString().getIntOrZero()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        etQuantity.afterTextChanged {
            presenter.updateViewInfo("${currencyOne}_${currencyTwo}")
            count = it
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNT, count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        count = savedInstanceState.getInt(KEY_COUNT)
    }

    override fun showResult(result: Float?) {
        if (result == 0.0f) {
            tvResult.text = getString(R.string.connection)
        } else {
            if (count <= 0) {
                tvResult.text = getString(R.string.input_positive)
            } else tvResult.text = "${result?.times(count)}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
