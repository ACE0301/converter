package com.ace.converter.extentions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChanged(afterTextChanged: (Int) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            try {
                afterTextChanged.invoke(Integer.parseInt(editable.toString()))
            } catch (e: NumberFormatException) {
                afterTextChanged.invoke(0)
            }
        }
    })
}