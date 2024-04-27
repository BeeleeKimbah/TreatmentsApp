package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Session
import com.razvanberchez.proiectlicenta.data.repository.getSessions

data class SessionsScreenViewState(
    val sessions: List<Session> = getSessions(),
    val loading: Boolean = false
)
