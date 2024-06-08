package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.data.model.allSlots
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
                loading = false,
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

            is AddConsultScreenIntent.AddSession ->
                addSession()
        }
    }

    private fun addSession() {
        viewModelScope.launch {
            val success = repository.addSession(
                _viewState.value.selectedMedic!!,
                _viewState.value.selectedDate,
                _viewState.value.selectedTime
            )

            if (!success) {
                val slots = repository.getAvailableSlots(
                    _viewState.value.selectedMedic?.medicId!!,
                    _viewState.value.selectedDate
                )

                _viewState.value = _viewState.value.copy(
                    availableIntervals = slots,
                    selectedTime = TimeSlot(0, 0),
                    showErrorAddingSession = true
                )
            } else {
                _viewState.value = _viewState.value.copy(
                    addedSession = true,
                    showErrorAddingSession = false
                )
            }
        }
    }

    private fun modifyMedic(newMedicId: String) {
        _viewState.value = _viewState.value.copy(
            intervalPickEnabled = false,
            loading = true
        )

        viewModelScope.launch {
            val newMedic = repository.getMedic(newMedicId)
            val slots = repository.getAvailableSlots(
                newMedicId,
                _viewState.value.selectedDate
            )

            _viewState.value = _viewState.value.copy(
                intervalPickEnabled = true,
                selectedMedic = newMedic,
                showErrorAddingSession = false,
                availableIntervals = slots,
                loading = false
            )
        }
    }

    private fun modifyTime(newInterval: TimeSlot) {
        viewModelScope.launch {
            val slots =
                if (_viewState.value.selectedMedic != null)
                    repository.getAvailableSlots(
                        _viewState.value.selectedMedic?.medicId!!,
                        _viewState.value.selectedDate
                    )
                else
                    allSlots.toList()

            _viewState.value = _viewState.value.copy(
                selectedTime = newInterval,
                showErrorAddingSession = false,
                availableIntervals = slots
            )
        }
    }

    private fun modifyDate(newDate: Date) {
        viewModelScope.launch {
            val slots =
                if (_viewState.value.selectedMedic != null)
                    repository.getAvailableSlots(
                        _viewState.value.selectedMedic?.medicId!!,
                        newDate
                    )
                else
                    allSlots.toList()

            _viewState.value = _viewState.value.copy(
                selectedDate = newDate,
                showErrorAddingSession = false,
                availableIntervals = slots,
                selectedTime =
                if (_viewState.value.selectedDate != newDate)
                    TimeSlot(0, 0)
                else
                    _viewState.value.selectedTime
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(medicId: String? = null): AddConsultScreenViewModel
    }
}