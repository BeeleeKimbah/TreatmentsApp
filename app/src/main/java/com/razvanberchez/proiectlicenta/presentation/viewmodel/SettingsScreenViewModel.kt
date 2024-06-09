package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.repository.Repository
import com.razvanberchez.proiectlicenta.presentation.intent.AppTheme
import com.razvanberchez.proiectlicenta.presentation.intent.SettingsScreenIntent
import com.razvanberchez.proiectlicenta.ui.theme.ThemeSelector
import com.razvanberchez.proiectlicenta.view.viewstate.SettingsScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    private val _viewState = MutableStateFlow(SettingsScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            loadSettings()
        }
    }

    private suspend fun loadSettings() {
        val settings = repository.getUserSettings()

        if (settings.isNotEmpty()) {
            _viewState.value = _viewState.value.copy(
                notificationsOn = settings["notifications"] as Boolean,
                appointmentNotificationsOn = settings["appointmentNotifications"] as Boolean,
                treatmentNotificationsOn = settings["treatmentNotifications"] as Boolean,
                appTheme = settings["theme"] as AppTheme,
                loading = false,
            )
        }
    }

    fun onIntent(intent: SettingsScreenIntent) {
        when(intent) {
            is SettingsScreenIntent.ToggleNotifications ->
                toggleNotifications()

            is SettingsScreenIntent.ToggleAppointmentNotifications ->
                toggleAppointmentNotifications()

            is SettingsScreenIntent.ToggleTreatmentNotifications ->
                toggleTreatmentNotifications()

            is SettingsScreenIntent.ModifyAppTheme ->
                modifyAppTheme(intent.newTheme)
        }
    }

    private fun modifyAppTheme(newTheme: AppTheme) {
        viewModelScope.launch {
            repository.updateUserSettings(
                hashMapOf(
                    "theme" to newTheme
                )
            )

            _viewState.value = _viewState.value.copy(
                appTheme = newTheme
            )

            ThemeSelector.setTheme(newTheme)
        }
    }

    private fun toggleTreatmentNotifications() {
        viewModelScope.launch {
            repository.updateUserSettings(
                hashMapOf(
                    "treatmentNotifications" to !_viewState.value.treatmentNotificationsOn
                )
            )

            _viewState.value = _viewState.value.copy(
                treatmentNotificationsOn = !_viewState.value.treatmentNotificationsOn
            )
        }
    }

    private fun toggleAppointmentNotifications() {
        viewModelScope.launch {
            repository.updateUserSettings(
                hashMapOf(
                    "appointmentNotifications" to !_viewState.value.appointmentNotificationsOn
                )
            )

            _viewState.value = _viewState.value.copy(
                appointmentNotificationsOn = !_viewState.value.appointmentNotificationsOn
            )
        }
    }

    private fun toggleNotifications() {
        viewModelScope.launch {
            repository.updateUserSettings(
                hashMapOf(
                    "notifications" to !_viewState.value.notificationsOn
                )
            )

            _viewState.value = _viewState.value.copy(
                notificationsOn = !_viewState.value.notificationsOn
            )
        }
    }
}