package com.razvanberchez.proiectlicenta.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.MedicListItem
import com.razvanberchez.proiectlicenta.data.model.Score
import com.razvanberchez.proiectlicenta.data.model.Session
import kotlinx.coroutines.tasks.await

class Repository {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    suspend fun getMedics(): List<MedicListItem> {
        val medicList = mutableListOf<MedicListItem>()

        db.collection("medics").get().addOnSuccessListener { result ->
            for (document in result) {
                medicList.add(MedicListItem.fromDocumentSnapshot(document))
            }
        }.addOnFailureListener {
            println(it.stackTrace)
        }.await()

        return medicList
    }

    suspend fun getMedic(medicId: String): Medic {
        var medic = Medic()

        db.collection("medics").document(medicId).get().addOnSuccessListener { document ->
            medic = Medic.fromDocumentSnapshot(document)
        }.addOnFailureListener {
            println(it.stackTrace)
        }.await()

        return medic
    }

    suspend fun addReview(medicId: String, reviewBody: String, score: Score) {
        db.collection("medics")
            .document(medicId)
            .update(
                "reviews", FieldValue.arrayUnion(
                    hashMapOf(
                        "body" to reviewBody,
                        "score" to score.value
                    )
                )
            )
            .await()
    }

    suspend fun getSessions(): MutableList<Session> {
        val sessions = mutableListOf<Session>()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .collection("sessions")
                .get().addOnSuccessListener { result ->
                    for (document in result) {
                        sessions.add(Session.fromDocumentSnapshot(document))
                    }
                }.addOnFailureListener {
                    println(it.stackTrace)
                }.await()
        }

        return sessions
    }

    suspend fun getSession(sessionId: String): Session {
        var session = Session()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .collection("sessions")
                .document(sessionId)
                .get().addOnSuccessListener { result ->
                    session = Session.fromDocumentSnapshot(result)
                }.addOnFailureListener {
                    println(it.stackTrace)
                }.await()
        }

        return session
    }
}