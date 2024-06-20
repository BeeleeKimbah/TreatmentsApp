package com.razvanberchez.proiectlicenta.presentation.intent

import com.razvanberchez.proiectlicenta.data.model.Treatment

sealed class SessionDetailsScreenIntent {
    data object Refresh : SessionDetailsScreenIntent()
    data class RemoveTreatment(val treatment: Treatment) : SessionDetailsScreenIntent()
    data class ModifyDiagnostic(val newDiagnostic: String) : SessionDetailsScreenIntent()
    data object SaveDiagnostic : SessionDetailsScreenIntent()
}