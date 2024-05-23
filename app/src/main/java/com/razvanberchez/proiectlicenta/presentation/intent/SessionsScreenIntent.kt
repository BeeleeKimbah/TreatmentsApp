package com.razvanberchez.proiectlicenta.presentation.intent

sealed class SessionsScreenIntent {
    data object Refresh : SessionsScreenIntent()
}