package com.razvanberchez.proiectlicenta.presentation.intent

sealed class SessionDetailsScreenIntent {
    data object Refresh : SessionDetailsScreenIntent()
}