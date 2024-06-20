package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.presentation.getDateWithoutTime
import com.razvanberchez.proiectlicenta.presentation.getNextDay
import java.util.Date

data class AddTreatmentScreenViewState(
    val treatmentName: String = "",
    val dose: Int? = null,
    val frequency: Int? = null,
    val applications: Int? = null,
    val startDate: Date = Date().getNextDay().getDateWithoutTime(),
    val addButtonEnabled: Boolean = false,
    val startTime: TimeSlot = TimeSlot(8, 0),
    val treatmentAdded: Boolean = false
)
