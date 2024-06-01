package com.razvanberchez.proiectlicenta.presentation.intent

sealed class LoginScreenIntent {
    data class Login(val onLogin: (String) -> Unit) : LoginScreenIntent()
    data class ModifyEmail(val newEmail: String) : LoginScreenIntent()
    data class ModifyPassword(val newPassword: String) : LoginScreenIntent()
}