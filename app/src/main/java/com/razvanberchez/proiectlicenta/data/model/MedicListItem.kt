package com.razvanberchez.proiectlicenta.data.model

import com.google.firebase.firestore.DocumentSnapshot

@Suppress("UNCHECKED_CAST")
class MedicListItem(
    val medicId: String,
    val name: String,
    val mainSpecialty: String,
    val averageScore: Float?
) {
    companion object {
        fun fromDocumentSnapshot(doc: DocumentSnapshot): MedicListItem {
            return MedicListItem(
                medicId = doc.id,
                name = doc.get("name") as String,
                mainSpecialty = doc.get("mainSpecialty") as String,
                averageScore = (doc.get("reviews") as List<Map<String, Any>>?)?.map {
                    it["score"] as Long
                }?.average()?.toFloat()
            )
        }
    }
}
