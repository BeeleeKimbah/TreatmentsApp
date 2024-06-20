package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import java.util.Date

data class AddTreatmentScreenViewState(
    val treatmentName: String = "",
    val dose: Int? = null,
    val frequency: Int? = null,
    val applications: Int? = null,
    val startDate: Date = Date(),
    val addButtonEnabled: Boolean = false,
    val startTime: TimeSlot = TimeSlot(8, 0),
    val treatmentAdded: Boolean = false
)
