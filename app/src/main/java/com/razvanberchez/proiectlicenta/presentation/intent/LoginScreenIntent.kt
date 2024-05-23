package com.razvanberchez.proiectlicenta.presentation.intent

sealed class LoginScreenIntent {
    data object Login : LoginScreenIntent()
    data class ModifyUsername(val newUsername: String) : LoginScreenIntent()
    data class ModifyPassword(val newPassword: String) : LoginScreenIntent()
}