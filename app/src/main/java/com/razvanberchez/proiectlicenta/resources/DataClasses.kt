package com.razvanberchez.proiectlicenta.resources

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

data class Treatment(
    val treatmentName: String,
    val startDate: LocalDate,
    val usages: Int,
    val frequency: Int,
    val applications: Int
)

data class Session(
    val startDate: LocalDate,
    val lastConsult: LocalDate,
    val medicName: String,
    val treatmentScheme: List<Treatment>,
    val diagnostic: String? = null
)