package com.razvanberchez.proiectlicenta.presentation

import android.text.TextUtils
import android.util.Patterns

object EmailValidator {
    fun isValidEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}