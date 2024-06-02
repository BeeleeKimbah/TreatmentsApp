package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.repository.Repository
import com.razvanberchez.proiectlicenta.presentation.intent.SessionDetailsScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.SessionDetailsScreenViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SessionDetailsScreenViewModel.Factory::class)
class SessionDetailsScreenViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted val sessionId: Int
) : ViewModel() {
    private val _viewState = MutableStateFlow(SessionDetailsScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            loadSessionDetails()
        }
    }

    fun onIntent(intent: SessionDetailsScreenIntent) {
        when (intent) {
            is SessionDetailsScreenIntent.Refresh -> loadSessionDetails()
        }
    }

    private fun loadSessionDetails() {
        _viewState.value = _viewState.value.copy(
            loading = true
        )
        viewModelScope.launch {
            val session = repository.getSession(sessionId)

            _viewState.value = _viewState.value.copy(
                session = session,
                loading = false
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(sessionId: Int): SessionDetailsScreenViewModel
    }
}