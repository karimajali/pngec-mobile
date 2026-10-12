package com.pngelectoral.utility.extension

import android.content.Context
import android.widget.Toast

fun String.showAsToast(context: Context) {
    showAsToast(context, Toast.LENGTH_SHORT)
}

fun String.showAsToast(context: Context, duration: Int) {
    Toast.makeText(context, this, duration).show()
}

fun String.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

