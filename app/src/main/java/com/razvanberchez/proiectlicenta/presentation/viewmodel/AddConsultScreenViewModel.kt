package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.data.repository.Repository
import com.razvanberchez.proiectlicenta.presentation.intent.AddConsultScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.AddConsultScreenViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date


@HiltViewModel(assistedFactory = AddConsultScreenViewModel.Factory::class)
class AddConsultScreenViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted medicId: String? = null
) : ViewModel() {
    private val _viewState = MutableStateFlow(AddConsultScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val medics = repository.getMedics()
            _viewState.value = _viewState.value.copy(
                medics = medics,
                loading = false
            )
        }
        if (medicId != null) {
            modifyMedic(medicId)
        }
    }

    fun onIntent(intent: AddConsultScreenIntent) {
        when (intent) {
            is AddConsultScreenIntent.ModifyDate ->
                modifyDate(intent.newDate)

            is AddConsultScreenIntent.ModifyTime ->
                modifyTime(intent.newInterval)

            is AddConsultScreenIntent.ModifyMedic ->
                modifyMedic(intent.newMedicId)
        }
    }

    private fun modifyMedic(newMedicId: String) {
        _viewState.value = _viewState.value.copy(
            intervalPickEnabled = false
        )

        viewModelScope.launch {
            val newMedic = repository.getMedic(newMedicId)
            val slots = repository.getAvailableSlots(newMedicId, _viewState.value.selectedDate)

            _viewState.value = _viewState.value.copy(
                availableIntervals = slots,
                intervalPickEnabled = true,
                selectedMedic = newMedic
            )
        }
    }

    private fun modifyTime(newInterval: TimeSlot) {
        _viewState.value = _viewState.value.copy(
            selectedTime = newInterval
        )
    }

    private fun modifyDate(newDate: Date) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(
                selectedDate = newDate
            )
            viewState.value.selectedMedic?.let {
                val slots = repository.getAvailableSlots(
                    viewState.value.selectedMedic!!.medicId,
                    viewState.value.selectedDate
                )
                _viewState.value = _viewState.value.copy(
                    availableIntervals = slots
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(medicId: String? = null): AddConsultScreenViewModel
    }
}