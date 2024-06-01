package com.razvanberchez.proiectlicenta.presentation.intent

import com.razvanberchez.proiectlicenta.view.viewstate.AuthState

sealed class LoginScreenIntent {
    data object Login : LoginScreenIntent()
    data class SetLoggedIn(val authState: AuthState) : LoginScreenIntent()
    data class ModifyEmail(val newEmail: String) : LoginScreenIntent()
    data class ModifyPassword(val newPassword: String) : LoginScreenIntent()
}