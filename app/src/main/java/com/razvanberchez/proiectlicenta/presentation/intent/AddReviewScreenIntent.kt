package com.razvanberchez.proiectlicenta.presentation.intent

import com.razvanberchez.proiectlicenta.data.model.Score

sealed class AddReviewScreenIntent {
    data class ModifyRating(val newRating: Score) : AddReviewScreenIntent()
    data class ModifyReviewBody(val newReviewBody: String) : AddReviewScreenIntent()
    data object AddReview : AddReviewScreenIntent()
}