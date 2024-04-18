package com.razvanberchez.proiectlicenta.data.model

import java.time.LocalDateTime

data class Session(
    val startDate: LocalDateTime,
    val lastConsult: LocalDateTime,
    val medicName: String,
    val treatmentScheme: List<Treatment>,
    val diagnostic: String? = null
)