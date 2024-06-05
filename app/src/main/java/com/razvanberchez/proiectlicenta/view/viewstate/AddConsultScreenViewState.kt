package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.MedicListItem
import java.util.Date

data class AddConsultScreenViewState(
    val medics: List<MedicListItem> = listOf(),
    val loading: Boolean = true,
    val selectedMedic: Medic? = null,
    val intervalPickEnabled: Boolean = false,
    val selectedDate: Date = Date(),
    val selectedTime: Pair<Int, Int> = Pair(8, 0)
)
