package com.razvanberchez.proiectlicenta.view.viewstate

data class RegisterScreenViewState(
    val registerButtonEnabled: Boolean = false,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)
