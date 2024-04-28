package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.repository.getMedic

data class MedicDetailsScreenViewState(
    val medic: Medic = getMedic(),
    val loading: Boolean = false
)
