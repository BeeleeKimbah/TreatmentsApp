package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Medic

data class MedicsScreenViewState(
    val medics: List<Medic> = listOf(),
    val loading: Boolean = false
)
