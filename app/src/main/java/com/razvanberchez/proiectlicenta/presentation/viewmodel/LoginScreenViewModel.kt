package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.presentation.intent.LoginScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.LoginScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(): ViewModel() {
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _viewState = MutableStateFlow(LoginScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        combine(_username, _password) {
            username, password->
            val validUsername = username.length >= 6
            val validPassword = password.length >= 8
            _viewState.value = _viewState.value.copy(
                validUsername = validUsername,
                validPassword = validPassword,
                loginButtonEnabled = validUsername && validPassword,
                username = _username.value,
                password = _password.value
            )
        }.launchIn(viewModelScope)
    }

    fun onIntent(intent: LoginScreenIntent) {
        when(intent) {
            is LoginScreenIntent.Login -> login()
            is LoginScreenIntent.ModifyPassword -> updatePassword(intent.newPassword)
            is LoginScreenIntent.ModifyUsername -> updateUsername(intent.newUsername)
        }
    }

    private fun login() {
        // TODO: validate login credentials with database
    }

    private fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    private fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
}