package com.razvanberchez.proiectlicenta.view.viewstate

data class AddReviewScreenViewState(
    val medicId: Int,
    val reviewBody: String = "",
    val loading: Boolean = false
)
