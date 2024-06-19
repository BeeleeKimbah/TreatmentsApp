package com.razvanberchez.proiectlicenta.view.viewstate

data class RegisterScreenViewState(
    val registerButtonEnabled: Boolean = false,
    val name: String = "",
    val email: String = "",
    val validEmail: Boolean = false,
    val password: String = "",
    val validPassword: Boolean = false,
    val confirmPassword: String = "",
    val passwordsMatch: Boolean = false,
    val errorMessage: String? = null,
    val registered: Boolean = false
)
