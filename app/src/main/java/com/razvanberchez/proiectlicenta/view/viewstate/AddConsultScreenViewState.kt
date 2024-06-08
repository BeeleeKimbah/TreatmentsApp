package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.MedicListItem
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.presentation.getDateWithoutTime
import java.util.Date

data class AddConsultScreenViewState(
    val medics: List<MedicListItem> = listOf(),
    val loading: Boolean = true,
    val selectedMedic: Medic? = null,
    val intervalPickEnabled: Boolean = false,
    val selectedDate: Date = Date().getDateWithoutTime(),
    val selectedTime: TimeSlot = TimeSlot(0, 0),
    val availableIntervals: List<TimeSlot> = listOf(),
    val addedSession: Boolean = false,
    val showErrorAddingSession: Boolean = false,
    val addSessionEnabled: Boolean = false
)
