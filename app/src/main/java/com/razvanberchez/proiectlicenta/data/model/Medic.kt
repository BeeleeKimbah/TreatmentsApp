package com.razvanberchez.proiectlicenta.data.model

data class Medic(
    val name: String,
    val mainSpecialty: String,
    val secondarySpecialties: List<String>,
    val reviews: List<Review>,
    val averageScore: Double?
)
