package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Session
import com.razvanberchez.proiectlicenta.data.repository.getSession

data class SessionDetailsScreenViewState(
    val session: Session = getSession(),
    val loading: Boolean = false
)
