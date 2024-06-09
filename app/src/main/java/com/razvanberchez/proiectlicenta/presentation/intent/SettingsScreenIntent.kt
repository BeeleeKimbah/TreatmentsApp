package com.razvanberchez.proiectlicenta.presentation.intent

enum class AppTheme {
    SYSTEM,
    DARK,
    LIGHT;

    companion object {
        fun fromString(str: String): AppTheme {
            return when(str) {
                "DARK" -> DARK
                "LIGHT" -> LIGHT
                else -> SYSTEM
            }
        }
    }
}

sealed class SettingsScreenIntent {
    data object ToggleNotifications: SettingsScreenIntent()
    data object ToggleAppointmentNotifications: SettingsScreenIntent()
    data object ToggleTreatmentNotifications: SettingsScreenIntent()
    data class ModifyAppTheme(val newTheme: AppTheme): SettingsScreenIntent()
}