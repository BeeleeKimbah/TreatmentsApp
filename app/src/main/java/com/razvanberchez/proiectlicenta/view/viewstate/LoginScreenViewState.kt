package com.razvanberchez.proiectlicenta.view.viewstate

data class LoginScreenViewState(
    val loginButtonEnabled: Boolean = false,
    val email: String = "",
    val validUsername: Boolean = false,
    val password: String = "",
    val validPassword: Boolean = false,
    val errorMessage: String? = null
)
