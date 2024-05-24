package com.razvanberchez.proiectlicenta.view.viewstate

import com.razvanberchez.proiectlicenta.data.model.Session

data class SessionDetailsScreenViewState(
    val session: Session? = null,
    val loading: Boolean = true
)
