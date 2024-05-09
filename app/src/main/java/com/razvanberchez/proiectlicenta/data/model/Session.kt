package com.razvanberchez.proiectlicenta.data.model

import java.time.LocalDateTime

data class Session(
    val consultDate: LocalDateTime,
    val medicName: String,
    val treatmentScheme: List<Treatment>,
    val diagnostic: String? = null
)