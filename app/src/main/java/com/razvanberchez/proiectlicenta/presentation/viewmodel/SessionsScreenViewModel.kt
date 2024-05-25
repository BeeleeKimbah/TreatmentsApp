package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.repository.getSessions
import com.razvanberchez.proiectlicenta.presentation.intent.SessionsScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.SessionsScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionsScreenViewModel @Inject constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(SessionsScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            loadSessions()
        }
    }

    fun onIntent(intent: SessionsScreenIntent) {
        when (intent) {
            is SessionsScreenIntent.Refresh -> loadSessions()
        }
    }

    private fun loadSessions() {
        _viewState.value = _viewState.value.copy(
            loading = true
        )
        viewModelScope.launch {
            // simulate database call delay
            delay(1000)
            val sessions = getSessions()

            _viewState.value = _viewState.value.copy(
                sessions = sessions,
                loading = false
            )
        }
    }
}