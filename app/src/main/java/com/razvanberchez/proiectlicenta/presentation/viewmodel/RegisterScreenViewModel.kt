package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.presentation.intent.RegisterScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.RegisterScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor() : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _confirmPassword = MutableStateFlow("")
    private val _viewState = MutableStateFlow(RegisterScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        combine(
            _email,
            _password,
            _confirmPassword
        ) { email, password, confirmPassword ->
            val validPassword = password.length >= 8
            val validEmail = true
            val passwordsMatch = password == confirmPassword
            _viewState.value = _viewState.value.copy(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                validPassword = validPassword,
                validEmail = validEmail,
                passwordsMatch = passwordsMatch,
                registerButtonEnabled =
                validPassword && passwordsMatch && validEmail
            )
        }.launchIn(viewModelScope)
    }

    fun onIntent(intent: RegisterScreenIntent) {
        when (intent) {
            is RegisterScreenIntent.ModifyPassword ->
                modifyPassword(intent.newPassword)

            is RegisterScreenIntent.ModifyEmail ->
                modifyEmail(intent.newEmail)

            is RegisterScreenIntent.ModifyConfirmPassword ->
                modifyConfirmPassword(intent.newConfirmPassword)

            is RegisterScreenIntent.Register ->
                register()
        }
    }

    private fun register() {
        // TODO: Add user to database
    }

    private fun modifyConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    private fun modifyEmail(newEmail: String) {
        _email.value = newEmail
    }

    private fun modifyPassword(newPassword: String) {
        _password.value = newPassword
    }
}