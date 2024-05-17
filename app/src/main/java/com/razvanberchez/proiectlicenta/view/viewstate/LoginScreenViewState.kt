package com.razvanberchez.proiectlicenta.view.viewstate

data class LoginScreenViewState(
    val loginButtonEnabled: Boolean = false,
    val username: String = "",
    val validUsername: Boolean = false,
    val password: String = "",
    val validPassword: Boolean = false
)
