package com.razvanberchez.proiectlicenta.view.viewstate

data class SettingsScreenViewState(
    val loading: Boolean = false,
    val notificationsOn: Boolean = false,
    val consultNotificationsOn: Boolean = false,
    val treatmentNotificationsOn: Boolean = false,

    )
