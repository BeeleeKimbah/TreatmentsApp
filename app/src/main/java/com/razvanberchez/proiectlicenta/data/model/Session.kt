package com.razvanberchez.proiectlicenta.data.model

import java.util.Date

data class Session(
    val consultDate: Date,
    val medicName: String,
    val treatmentScheme: List<Treatment>,
    val diagnostic: String? = null,
    val medicId: String? = null
)