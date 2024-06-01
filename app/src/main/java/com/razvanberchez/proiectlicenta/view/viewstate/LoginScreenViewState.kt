package com.razvanberchez.proiectlicenta.view.viewstate

sealed class AuthState {
    data object Unknown: AuthState()
    data object LoggedIn: AuthState()
    data object NotLoggedIn: AuthState()
}

data class LoginScreenViewState(
    val loginButtonEnabled: Boolean = false,
    val email: String = "",
    val validUsername: Boolean = false,
    val password: String = "",
    val validPassword: Boolean = false,
    val errorMessage: String? = null,
    val authState: AuthState = AuthState.Unknown
)
