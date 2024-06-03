package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.repository.Repository
import com.razvanberchez.proiectlicenta.presentation.intent.MedicDetailsScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.MedicDetailsScreenViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MedicDetailsScreenViewModel.Factory::class)
class MedicDetailsScreenViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted val medicId: String
) : ViewModel() {
    private val _viewState = MutableStateFlow(MedicDetailsScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            loadMedicDetails()
        }
    }

    fun onIntent(intent: MedicDetailsScreenIntent) {
        when (intent) {
            is MedicDetailsScreenIntent.Refresh -> loadMedicDetails()
        }
    }

    private fun loadMedicDetails() {
        _viewState.value = _viewState.value.copy(
            loading = true
        )
        viewModelScope.launch {
            val medic = repository.getMedic(medicId)

            _viewState.value = _viewState.value.copy(
                medic = medic,
                loading = false
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(medicId: String): MedicDetailsScreenViewModel
    }
}