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
class LoginScreenViewModel @Inject constructor() : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _viewState = MutableStateFlow(LoginScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        combine(_email, _password) { email, password ->
            val validUsername = email.length >= 8
            val validPassword = password.length >= 8
            _viewState.value = _viewState.value.copy(
                validUsername = validUsername,
                validPassword = validPassword,
                loginButtonEnabled = validUsername && validPassword,
                email = email,
                password = password
            )
        }.launchIn(viewModelScope)
    }

    fun onIntent(intent: LoginScreenIntent) {
        when (intent) {
            is LoginScreenIntent.Login -> login()
            is LoginScreenIntent.ModifyPassword -> updatePassword(intent.newPassword)
            is LoginScreenIntent.ModifyEmail -> updateUsername(intent.newEmail)
        }
    }

    private fun login() {
        // TODO: validate login credentials with database
    }

    private fun updateUsername(newEmail: String) {
        _email.value = newEmail
    }

    private fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
}