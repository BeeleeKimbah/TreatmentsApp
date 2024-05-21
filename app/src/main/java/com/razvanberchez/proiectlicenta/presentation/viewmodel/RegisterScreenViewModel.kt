package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.razvanberchez.proiectlicenta.presentation.intent.RegisterScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.RegisterScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor() : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _confirmPassword = MutableStateFlow("")
    private val _viewState = MutableStateFlow(RegisterScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        combine(_username, _email, _password, _confirmPassword) {
            username, email, password, confirmPassword ->
            val validUsername = username.length >= 6
            val validPassword = password.length >= 8
            val validEmail = true
            val passwordsMatch = password == confirmPassword
            _viewState.value = _viewState.value.copy(
                username = username,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                validUsername = validUsername,
                validPassword = validPassword,
                validEmail = validEmail,
                passwordsMatch = passwordsMatch,
                registerButtonEnabled =
                    validUsername &&validPassword && passwordsMatch && validEmail
            )
        }
    }

    fun onIntent(intent: RegisterScreenIntent) {
        when(intent) {
            else -> Unit
        }
    }
}