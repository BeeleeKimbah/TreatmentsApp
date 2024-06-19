package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.repository.Repository
import com.razvanberchez.proiectlicenta.presentation.intent.MedicSessionsScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.MedicSessionsScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicSessionsScreenViewModel@Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _viewState = MutableStateFlow(MedicSessionsScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            loadSessions()
        }
    }

    fun onIntent(intent: MedicSessionsScreenIntent) {
        when (intent) {
            is MedicSessionsScreenIntent.Refresh -> loadSessions()
        }
    }

    private fun loadSessions() {
        _viewState.value = _viewState.value.copy(
            loading = true
        )
        viewModelScope.launch {
            val sessions = repository.getSessions(true)

            _viewState.value = _viewState.value.copy(
                sessions = sessions,
                loading = false
            )
        }
    }
}