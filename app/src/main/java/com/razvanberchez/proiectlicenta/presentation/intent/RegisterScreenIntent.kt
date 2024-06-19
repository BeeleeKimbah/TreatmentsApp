package com.razvanberchez.proiectlicenta.presentation.intent

sealed class RegisterScreenIntent {
    data class ModifyName(val newName: String) : RegisterScreenIntent()
    data class ModifyEmail(val newEmail: String) : RegisterScreenIntent()
    data class ModifyPassword(val newPassword: String) : RegisterScreenIntent()
    data class ModifyConfirmPassword(val newConfirmPassword: String) : RegisterScreenIntent()
    data object Register : RegisterScreenIntent()
}