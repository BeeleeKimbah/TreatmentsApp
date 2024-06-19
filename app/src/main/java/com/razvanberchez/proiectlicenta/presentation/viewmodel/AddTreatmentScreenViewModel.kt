package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.repository.Repository
import com.razvanberchez.proiectlicenta.presentation.intent.AddTreatmentScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.AddTreatmentScreenViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import java.util.Date

@HiltViewModel(assistedFactory = AddTreatmentScreenViewModel.Factory::class)
class AddTreatmentScreenViewModel @AssistedInject constructor(
    val repository: Repository,
    @Assisted val sessionId: String
) : ViewModel() {
    private val _treatment = MutableStateFlow("")
    private val _dose = MutableStateFlow(0)
    private val _frequency = MutableStateFlow(0)
    private val _applications = MutableStateFlow(0)
    private val _startDate = MutableStateFlow(Date())
    private val _viewState = MutableStateFlow(AddTreatmentScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        combine(_treatment, _startDate, _dose, _frequency, _applications) {
            treatment, startDate, dose, frequency, applications ->
            _viewState.value = _viewState.value.copy(
                treatmentName = treatment,
                startDate = startDate,
                dose = dose,
                frequency = frequency,
                applications = applications,
                addButtonEnabled = treatment.isNotEmpty()
                                && dose > 0
                                && frequency > 0
                                && applications > 0
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
        }
    }

    private fun modifyTreatment(newTreatment: String) {
        _treatment.value = newTreatment
    }

    private fun modifyStartDate(newDate: Date) {
        _startDate.value = newDate
    }

    private fun modifyFrequency(newFrequency: Int) {
        _frequency.value = newFrequency
    }

    private fun modifyDose(newDose: Int) {
        _dose.value = newDose
    }

    private fun modifyApplications(newApplications: Int) {
        _applications.value = newApplications
    }

    private fun addTreatment() {
        TODO("Not yet implemented")
    }

    @AssistedFactory
    interface Factory {
        fun create(sessionId: String? = null): AddTreatmentScreenViewModel
    }
}