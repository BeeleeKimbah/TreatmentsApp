package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.data.model.Treatment
import com.razvanberchez.proiectlicenta.data.repository.Repository
import com.razvanberchez.proiectlicenta.presentation.addTimeSlot
import com.razvanberchez.proiectlicenta.presentation.intent.AddTreatmentScreenIntent
import com.razvanberchez.proiectlicenta.presentation.toUTC
import com.razvanberchez.proiectlicenta.view.viewstate.AddTreatmentScreenViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel(assistedFactory = AddTreatmentScreenViewModel.Factory::class)
class AddTreatmentScreenViewModel @AssistedInject constructor(
    val repository: Repository,
    @Assisted val sessionId: String
) : ViewModel() {
    private val _treatment = MutableStateFlow("")
    private val _dose: MutableStateFlow<Int?> = MutableStateFlow(0)
    private val _frequency: MutableStateFlow<Int?>  = MutableStateFlow(0)
    private val _applications: MutableStateFlow<Int?>  = MutableStateFlow(0)
    private val _viewState = MutableStateFlow(AddTreatmentScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        combine(_treatment, _dose, _frequency, _applications) {
            treatment, dose, frequency, applications ->
            _viewState.value = _viewState.value.copy(
                treatmentName = treatment,
                dose = dose,
                frequency = frequency,
                applications = applications,
                addButtonEnabled = treatment.isNotEmpty()
                                && dose != null && dose > 0
                                && frequency != null && frequency > 0
                                && applications != null && applications > 0
            )
        }.launchIn(viewModelScope)
    }


    fun onIntent(intent: AddTreatmentScreenIntent) {
        when(intent) {
            is AddTreatmentScreenIntent.AddTreatment ->
                addTreatment()

            is AddTreatmentScreenIntent.ModifyApplications ->
                modifyApplications(intent.newApplications)

            is AddTreatmentScreenIntent.ModifyDose ->
                modifyDose(intent.newDose)

            is AddTreatmentScreenIntent.ModifyFrequency ->
                modifyFrequency(intent.newFrequency)

            is AddTreatmentScreenIntent.ModifyStartDate ->
                modifyStartDate(intent.newDate)

            is AddTreatmentScreenIntent.ModifyTreatment ->
                modifyTreatment(intent.newTreatment)

            is AddTreatmentScreenIntent.ModifyStartTime ->
                modifyStartTime(intent.newTime)
        }
    }

    private fun modifyStartTime(newTime: TimeSlot) {
        _viewState.value = _viewState.value.copy(
            startTime = newTime
        )
    }

    private fun modifyTreatment(newTreatment: String) {
        _treatment.value = newTreatment
    }

    private fun modifyStartDate(newDate: Date) {
        _viewState.value = _viewState.value.copy(
            startDate = newDate
        )
    }

    private fun modifyFrequency(newFrequency: Int?) {
        _frequency.value = newFrequency
    }

    private fun modifyDose(newDose: Int?) {
        _dose.value = newDose
    }

    private fun modifyApplications(newApplications: Int?) {
        _applications.value = newApplications
    }

    private fun addTreatment() {
        viewModelScope.launch {
            repository.addTreatment(
                sessionId,
                Treatment(
                    treatmentName =  viewState.value.treatmentName,
                    startDate = viewState.value.startDate
                        .addTimeSlot(viewState.value.startTime).toUTC(),
                    dose = viewState.value.dose!!,
                    frequency = viewState.value.frequency!!,
                    applications = viewState.value.applications!!
                )
            )

            _viewState.value = _viewState.value.copy(
                treatmentAdded = true
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(sessionId: String? = null): AddTreatmentScreenViewModel
    }
}