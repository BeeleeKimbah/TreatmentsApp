package com.razvanberchez.proiectlicenta.view.viewstate

data class LoginScreenViewState(
    val loginButtonEnabled: Boolean = true,
    val username: String = "",
    val password: String = ""
)
