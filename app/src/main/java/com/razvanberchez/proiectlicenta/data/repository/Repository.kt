package com.razvanberchez.proiectlicenta.data.repository

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.MedicListItem
import com.razvanberchez.proiectlicenta.data.model.Score
import com.razvanberchez.proiectlicenta.data.model.Session
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.data.model.allSlots
import com.razvanberchez.proiectlicenta.presentation.DAY_MILLIS
import com.razvanberchez.proiectlicenta.presentation.addTimeSlot
import com.razvanberchez.proiectlicenta.presentation.toUTC
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

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

        return medicList.toList()
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

    suspend fun getSessions(): List<Session> {
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

        return sessions.toList()
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

    suspend fun getAvailableSlots(medicId: String, date: Date): List<TimeSlot> {
        val slots = mutableSetOf<TimeSlot>()
        allSlots.forEach { slots.add(it) }

        db.collection("medics")
            .document(medicId)
            .collection("appointments")
            .whereGreaterThanOrEqualTo("consultDate", date)
            .whereLessThan("consultDate", Date(date.time + DAY_MILLIS))
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    val consultDate = (document.get("consultDate") as Timestamp).toDate()
                    val calendar = Calendar.getInstance()
                    calendar.setTime(consultDate)
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)

                    slots.remove(TimeSlot(hour, minute))
                }
            }.addOnFailureListener {
                println(it.stackTrace)
            }.await()

        return slots.toList()
    }

    suspend fun addSession(
        selectedMedic: Medic,
        selectedDate: Date,
        selectedTime: TimeSlot
    ): Boolean {
        val currentUser = auth.currentUser

        val availableSlots = getAvailableSlots(selectedMedic.medicId, selectedDate)
        if (!availableSlots.contains(selectedTime))
            return false

        val datetime = Timestamp(selectedDate.addTimeSlot(selectedTime).toUTC())

        var success = true
        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .collection("sessions")
                .add(
                    hashMapOf(
                        "consultDate" to datetime,
                        "medicId" to selectedMedic.medicId,
                        "medicName" to selectedMedic.name,
                    )
                ).addOnFailureListener {
                    success = false
                }.await()

            db.collection("medics")
                .document(selectedMedic.medicId)
                .collection("appointments")
                .add(
                    hashMapOf(
                        "consultDate" to datetime
                    )
                ).addOnFailureListener {
                    success = false
                }.await()
        }
        return success
    }
}