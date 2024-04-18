package com.razvanberchez.proiectlicenta.data.model

import java.time.LocalDate

data class Treatment(
    val treatmentName: String,
    val startDate: LocalDate,
    val dose: Int,
    val frequency: Int,
    val applications: Int
)