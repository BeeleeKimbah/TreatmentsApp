package com.razvanberchez.proiectlicenta.data.model

import java.time.LocalDate

data class Session(
    val startDate: LocalDate,
    val lastConsult: LocalDate,
    val medicName: String,
    val treatmentScheme: List<Treatment>,
    val diagnostic: String? = null
)