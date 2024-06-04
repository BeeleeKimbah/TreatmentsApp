package com.razvanberchez.proiectlicenta.data.model

import com.google.firebase.firestore.DocumentSnapshot

@Suppress("UNCHECKED_CAST")
class Medic(
    val name: String = "",
    val mainSpecialty: String = "",
    val secondarySpecialties: List<String> = listOf(),
    val reviews: List<Review> = listOf(),
    val averageScore: Float? = null,
    val medicId: String = ""
) {
    companion object {
        fun fromDocumentSnapshot(doc: DocumentSnapshot): Medic {
            return Medic(
                medicId = doc.id,
                name = doc.get("name") as String,
                mainSpecialty = doc.get("mainSpecialty") as String,
                secondarySpecialties =
                doc.get("secondarySpecialties") as List<String>? ?: listOf(),
                reviews = (doc.get("reviews") as List<Map<String, Any>>?)?.map {
                    Review(
                        text = it["body"] as String,
                        score = Score.getScore((it["score"] as Long).toInt()) ?: Score.ONE
                    )
                } ?: listOf(),
                averageScore = (doc.get("reviews") as List<Map<String, Any>>?)?.map {
                    it["score"] as Long
                }?.average()?.toFloat()
            )
        }
    }
}
