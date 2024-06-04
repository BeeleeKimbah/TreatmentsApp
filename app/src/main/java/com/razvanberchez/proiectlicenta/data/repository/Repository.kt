package com.razvanberchez.proiectlicenta.data.repository

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.MedicListItem
import com.razvanberchez.proiectlicenta.data.model.Review
import com.razvanberchez.proiectlicenta.data.model.Score
import com.razvanberchez.proiectlicenta.data.model.Session
import com.razvanberchez.proiectlicenta.data.model.Treatment
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

@Suppress("UNCHECKED_CAST")
class Repository {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    suspend fun getMedics(): List<MedicListItem> {
        val medicList = mutableListOf<MedicListItem>()
        db.collection("medics").get().addOnSuccessListener { result ->
            for (document in result) {
                medicList.add(
                    MedicListItem(
                        medicId = document.id,
                        name = document.get("name") as String,
                        mainSpecialty = document.get("mainSpecialty") as String,
                        averageScore = (document.get("reviews") as List<Map<String, Any>>?)?.map {
                            it["score"] as Long
                        }?.average()?.toFloat()
                    )
                )
            }
        }.addOnFailureListener {
            println(it.stackTrace)
        }.await()

        return medicList
    }

    suspend fun getMedic(medicId: String): Medic {
        var medic = Medic()
        db.collection("medics").document(medicId).get().addOnSuccessListener {
            document ->
            medic = Medic(
                medicId = document.id,
                name = document.get("name") as String,
                mainSpecialty = document.get("mainSpecialty") as String,
                secondarySpecialties =
                    document.get("secondarySpecialties") as List<String>? ?: listOf(),
                reviews = (document.get("reviews") as List<Map<String, Any>>?)?.map {
                    Review(
                        text = it["body"] as String,
                        score = Score.getScore((it["score"] as Long).toInt()) ?: Score.ONE
                    )
                } ?: listOf(),
                averageScore = (document.get("reviews") as List<Map<String, Any>>?)?.map {
                    it["score"] as Long
                }?.average()?.toFloat()
            )
        }.addOnFailureListener {
            println(it.stackTrace)
        }.await()

        return medic
    }

    suspend fun addReview(medicId: String, reviewBody: String, score: Score) {
        db.collection("medics")
            .document(medicId)
            .update("reviews", FieldValue.arrayUnion(hashMapOf(
                "body" to reviewBody,
                "score" to score.value
            )))
            .await()
    }

    suspend fun getSessions(): MutableList<Session> {
        val sessions = mutableListOf<Session>()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users")
                    .document(currentUser.uid)
                    .collection("sessions")
                    .get().addOnSuccessListener {
                result ->
                for (document in result) {
                    sessions.add(Session(
                        medicId = document.get("medicId") as String,
                        medicName = document.get("medicName") as String,
                        diagnostic = document.get("diagnostic") as String?,
                        consultDate = (document.get("consultDate") as Timestamp).toDate(),
                        treatmentScheme =
                            (document.get("treatmentScheme") as List<Map<String, Any>>).map {
                                Treatment(
                                    treatmentName = it["treatmentName"] as String,
                                    dose = (it["dose"] as Long).toInt(),
                                    frequency = (it["frequency"] as Long).toInt(),
                                    applications = (it["applications"] as Long).toInt(),
                                    startDate = (it["startDate"] as Timestamp).toDate()
                                )
                            }
                    ))
                }
            }.addOnFailureListener {
                println(it.stackTrace)
            }.await()
        }
        return sessions
    }

    suspend fun getSession(sessionId: Int): Session {
        delay(1000)
        return sessionList[sessionId]
    }
}