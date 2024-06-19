package com.razvanberchez.proiectlicenta.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

@Suppress("UNCHECKED_CAST")
class Session(
    val patientId: String = "",
    val patientName: String = "",
    val sessionId: String = "",
    val consultDate: Date = Date(),
    val medicName: String = "",
    val treatmentScheme: List<Treatment> = listOf(),
    val diagnostic: String? = null,
    val medicId: String? = null
) {

    companion object {
        fun fromDocumentSnapshot(doc: DocumentSnapshot): Session {
            return Session(
                sessionId = doc.id,
                medicName = doc.get("medicName") as String,
                consultDate = (doc.get("consultDate") as Timestamp).toDate(),
                diagnostic = doc.get("diagnostic") as String?,
                medicId = doc.get("medicId") as String,
                treatmentScheme =
                (doc.get("treatmentScheme") as List<Map<String, Any>>?)?.map {
                    Treatment(
                        treatmentName = it["treatmentName"] as String,
                        dose = (it["dose"] as Long).toInt(),
                        frequency = (it["frequency"] as Long).toInt(),
                        applications = (it["applications"] as Long).toInt(),
                        startDate = (it["startDate"] as Timestamp).toDate()
                    )
                } ?: listOf(),
                patientId = doc.get("patientId") as String,
                patientName = doc.get("patientName") as String
            )
        }
    }
}

