package com.razvanberchez.proiectlicenta.presentation.intent

sealed class MedicDetailsScreenIntent {
    data object Refresh : MedicDetailsScreenIntent()
}