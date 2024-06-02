package com.razvanberchez.proiectlicenta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razvanberchez.proiectlicenta.data.model.Score
import com.razvanberchez.proiectlicenta.data.repository.Repository
import com.razvanberchez.proiectlicenta.presentation.intent.AddReviewScreenIntent
import com.razvanberchez.proiectlicenta.view.viewstate.AddReviewScreenViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = AddReviewScreenViewModel.Factory::class)
class AddReviewScreenViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted val medicId: Int
) : ViewModel() {
    private val _viewState = MutableStateFlow(AddReviewScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            loadMedicDetails()
        }
    }

    fun onIntent(intent: AddReviewScreenIntent) {
        when (intent) {
            is AddReviewScreenIntent.ModifyReviewBody -> modifyReviewBody(intent.newReviewBody)
            is AddReviewScreenIntent.ModifyRating -> modifyRating(intent.newRating)
            is AddReviewScreenIntent.AddReview -> addReview()
        }
    }

    private fun addReview() {
        // TODO: add to database
    }

    private fun modifyRating(newRating: Score) {
        _viewState.value = _viewState.value.copy(
            score = newRating
        )
    }

    private fun modifyReviewBody(newReviewBody: String) {
        _viewState.value = _viewState.value.copy(
            reviewBody = newReviewBody
        )
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
        fun create(medicId: Int): AddReviewScreenViewModel
    }
}