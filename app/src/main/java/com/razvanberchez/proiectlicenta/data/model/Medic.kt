package com.razvanberchez.proiectlicenta.data.model

data class Medic(
    val name: String = "",
    val mainSpecialty: String = "",
    val secondarySpecialties: List<String> = listOf(),
    val reviews: List<Review> = listOf(),
    val averageScore: Float? = null,
    val medicId: String = ""
)
