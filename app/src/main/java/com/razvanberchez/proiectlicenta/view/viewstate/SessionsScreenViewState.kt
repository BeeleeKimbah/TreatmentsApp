package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Session

data class SessionsScreenViewState(
    val sessions: List<Session> = listOf(),
    val loading: Boolean = true
)
