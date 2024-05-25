package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.Score

data class AddReviewScreenViewState(
    val medic: Medic? = null,
    val reviewBody: String = "",
    val score: Score = Score.ONE,
    val loading: Boolean = true
)
