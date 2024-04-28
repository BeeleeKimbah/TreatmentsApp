package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.repository.getMedics

data class MedicsScreenViewState(
    val medics: List<Medic> = getMedics(),
    val loading: Boolean = false
)
