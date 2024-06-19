package com.razvanberchez.proiectlicenta.presentation.intent

sealed class MedicSessionsScreenIntent {
    data object Refresh : MedicSessionsScreenIntent()
}