package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private val auth = Firebase.auth

    init {
        combine(
            _email,
            _password,
            _confirmPassword
        ) { email, password, confirmPassword ->
            // TODO: proper validations
            val validPassword = password.length >= 8
            val validEmail = email.length >= 8
            val passwordsMatch = password == confirmPassword
            _viewState.value = _viewState.value.copy(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                validPassword = validPassword,
                validEmail = validEmail,
                passwordsMatch = passwordsMatch,
                registerButtonEnabled =
                validPassword && passwordsMatch && validEmail,
                errorMessage =
                if (!validEmail) "Adresa de email nu este validă"
                else if (!validPassword) "Parola trebuie să conțină cel puțin 8 caractere, o literă și o cifră"
                else if (!passwordsMatch) "Parolele nu se potrivesc"
                else null
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
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _viewState.value = _viewState.value.copy(
                        registered = true
                    )
                } else {
                    _viewState.value = _viewState.value.copy(
                        errorMessage = "Înregistrare eșuată"
                    )
                }
            }
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