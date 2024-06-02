package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.razvanberchez.proiectlicenta.presentation.intent.LoginScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.AuthState
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

    private val auth = Firebase.auth

    init {
        combine(_email, _password) { email, password ->
            // TODO: validate email properly
            val validEmail = email.length >= 8
            val validPassword = password.length >= 8
            _viewState.value = _viewState.value.copy(
                validUsername = validEmail,
                validPassword = validPassword,
                loginButtonEnabled = validEmail && validPassword,
                email = email,
                password = password,
                // TODO: Extract string resources for error messages
                errorMessage = if (!validEmail) "Adresa de email nu este validă"
                else if (!validPassword) "Parola trebuie să conțină cel puțin 8 caractere, o literă și o cifră"
                else null
            )
        }.launchIn(viewModelScope)
    }

    fun onIntent(intent: LoginScreenIntent) {
        when (intent) {
            is LoginScreenIntent.Login -> login()
            is LoginScreenIntent.SetLoggedIn -> setLoggedIn(intent.authState)
            is LoginScreenIntent.ModifyPassword -> updatePassword(intent.newPassword)
            is LoginScreenIntent.ModifyEmail -> updateUsername(intent.newEmail)
        }
    }

    private fun setLoggedIn(authState: AuthState) {
        _viewState.value = _viewState.value.copy(
            authState = authState
        )
    }

    private fun login() {
        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _viewState.value = _viewState.value.copy(
                        authState = AuthState.LoggedIn
                    )
                } else {
                    _viewState.value = _viewState.value.copy(
                        errorMessage = "Autentificare eșuată"
                    )
                }
            }
    }

    private fun updateUsername(newEmail: String) {
        _email.value = newEmail
    }

    private fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
}