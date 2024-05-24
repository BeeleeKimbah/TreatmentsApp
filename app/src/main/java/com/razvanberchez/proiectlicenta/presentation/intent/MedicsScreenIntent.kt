package com.razvanberchez.proiectlicenta.presentation.intent

sealed class MedicsScreenIntent {
    data object Refresh : MedicsScreenIntent()
}