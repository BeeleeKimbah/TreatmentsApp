package com.razvanberchez.proiectlicenta.view.viewstate

import java.util.Date

data class AddTreatmentScreenViewState(
    val treatmentName: String = "",
    val dose: Int = 0,
    val frequency: Int = 0,
    val applications: Int = 0,
    val startDate: Date = Date(),
    val addButtonEnabled: Boolean = false
)
