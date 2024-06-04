package com.razvanberchez.proiectlicenta.data.model

import java.util.Date

data class Treatment(
    val treatmentName: String,
    val startDate: Date,
    val dose: Int,
    val frequency: Int,
    val applications: Int
)