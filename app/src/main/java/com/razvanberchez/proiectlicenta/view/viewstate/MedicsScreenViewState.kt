package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.MedicListItem

data class MedicsScreenViewState(
    val medics: List<MedicListItem> = listOf(),
    val loading: Boolean = true
)
