package com.ace.converter.extentions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.subjects.PublishSubject

fun EditText.afterTextChanged(afterTextChanged: (Int) -> Unit) {

    val subject = PublishSubject.create<Any>()
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            subject.onNext(editable.toString())
            try {
                afterTextChanged.invoke(Integer.parseInt(editable.toString()))
            } catch (e: NumberFormatException) {
                afterTextChanged.invoke(0)
            }
        }
    })
}