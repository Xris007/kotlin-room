package com.noblecilla.tasks.view.utils

import android.content.Context
import android.graphics.Paint
import android.widget.TextView
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun TextView.complete(complete: Boolean) {
    paintFlags = if (complete) {
        Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        0
    }
}
