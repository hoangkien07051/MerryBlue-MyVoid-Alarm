package com.merryblue.myvoicealarm.common.extenstion

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.setTextValue(value: String) {
    if (value.isNotEmpty()) {
        if (value.toFloat() > 0) {
            this.setText(value)
        }
    }
}

fun EditText.getValueString(): String {
    return this.text.toString()
}

fun EditText.getValueTrim(): String {
    return this.text.toString().trim().replace(" ", "")
}

fun EditText.isEmpty(): Boolean {
    return this.text.toString().trim().isEmpty()
}

fun EditText.getString(): String {
    return text.toString()
}

fun EditText.getInt(): Int {
    return try {
        text.toString().toIntOrNull() ?: 0
    } catch (e: Exception) {
        0
    }
}

fun EditText.getDouble(): Double {
    return try {
        text.toString().toDoubleOrNull() ?: 0.0
    } catch (e: Exception) {
        0.0
    }
}

