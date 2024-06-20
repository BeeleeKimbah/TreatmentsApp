package com.razvanberchez.proiectlicenta.presentation.intent

import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import java.util.Date

sealed class AddTreatmentScreenIntent {
    data class ModifyTreatment(val newTreatment: String) : AddTreatmentScreenIntent()
    data class ModifyDose(val newDose: Int?) : AddTreatmentScreenIntent()
    data class ModifyFrequency(val newFrequency: Int?) : AddTreatmentScreenIntent()
    data class ModifyApplications(val newApplications: Int?) : AddTreatmentScreenIntent()
    data class ModifyStartDate(val newDate: Date) : AddTreatmentScreenIntent()
    data class ModifyStartTime(val newTime: TimeSlot) : AddTreatmentScreenIntent()
    data object AddTreatment : AddTreatmentScreenIntent()
}