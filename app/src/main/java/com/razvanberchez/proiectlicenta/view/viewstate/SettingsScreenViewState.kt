package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.presentation.intent.AppTheme

data class SettingsScreenViewState(
    val loading: Boolean = true,
    val notificationsOn: Boolean = false,
    val appointmentNotificationsOn: Boolean = false,
    val treatmentNotificationsOn: Boolean = false,
    val appTheme: AppTheme = AppTheme.SYSTEM,
    val loggedOut: Boolean = false
)
