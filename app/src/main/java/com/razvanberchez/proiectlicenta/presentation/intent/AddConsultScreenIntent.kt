package com.razvanberchez.proiectlicenta.presentation.intent

import java.util.Date

sealed class AddConsultScreenIntent {
    data class ModifyMedic(val newMedicId: String) : AddConsultScreenIntent()
    data class ModifyDate(val newDate: Date) : AddConsultScreenIntent()
    data class ModifyTime(val newInterval: Pair<Int, Int>) : AddConsultScreenIntent()
}