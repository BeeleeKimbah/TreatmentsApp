package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Medic

data class MedicDetailsScreenViewState(
    val medic: Medic? = null,
    val loading: Boolean = true
)
