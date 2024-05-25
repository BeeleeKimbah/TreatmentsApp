package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.repository.getMedics
import com.razvanberchez.proiectlicenta.presentation.intent.MedicsScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.MedicsScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicsScreenViewModel @Inject constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(MedicsScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            loadMedics()
        }
    }

    fun onIntent(intent: MedicsScreenIntent) {
        when (intent) {
            is MedicsScreenIntent.Refresh -> loadMedics()
        }
    }

    private fun loadMedics() {
        _viewState.value = _viewState.value.copy(
            loading = true
        )
        viewModelScope.launch {
            // simulate database call delay
            delay(1000)
            val medics = getMedics()

            _viewState.value = _viewState.value.copy(
                medics = medics,
                loading = false
            )
        }
    }
}