package com.razvanberchez.proiectlicenta.presentation.intent

sealed class LoginScreenIntent {
    data object Login : LoginScreenIntent()
    data class ModifyEmail(val newEmail: String) : LoginScreenIntent()
    data class ModifyPassword(val newPassword: String) : LoginScreenIntent()
}