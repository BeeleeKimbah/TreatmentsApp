package com.razvanberchez.proiectlicenta.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.MedicListItem
import com.razvanberchez.proiectlicenta.data.model.Score
import com.razvanberchez.proiectlicenta.data.model.Session
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.data.model.Treatment
import com.razvanberchez.proiectlicenta.data.model.allSlots
import com.razvanberchez.proiectlicenta.presentation.DAY_MILLIS
import com.razvanberchez.proiectlicenta.presentation.addTimeSlot
import com.razvanberchez.proiectlicenta.presentation.intent.AppTheme
import com.razvanberchez.proiectlicenta.presentation.toUTC
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class Repository {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    suspend fun getMedics(): List<MedicListItem> {
        val medicList = mutableListOf<MedicListItem>()

        db.collection("medics")
            .orderBy("mainSpecialty")
            .get().addOnSuccessListener { result ->
            for (document in result) {
                medicList.add(MedicListItem.fromDocumentSnapshot(document))
            }
        }.addOnFailureListener {
            Log.w(TAG, "Error fetching medic list")
        }.await()

        return medicList.toList()
    }

    suspend fun getMedic(medicId: String): Medic {
        var medic = Medic()

        db.collection("medics")
            .document(medicId)
            .get().addOnSuccessListener { document ->
                medic = Medic.fromDocumentSnapshot(document)
            }.addOnFailureListener {
                Log.w(TAG, "Error fetching medic details")
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
            ).addOnFailureListener {
                Log.w(TAG, "Error adding review")
            }
            .await()
    }

    suspend fun getSessions(isMedic: Boolean): List<Session> {
        val sessions = mutableListOf<Session>()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            if (isMedic) {
                db.collection("medics")
                    .document(currentUser.uid)
                    .collection("sessions")
                    .orderBy("consultDate", Query.Direction.DESCENDING)
                    .get().addOnSuccessListener { result ->
                        for (document in result) {
                            sessions.add(Session.fromDocumentSnapshot(document))
                        }
                    }.addOnFailureListener {
                        Log.w(TAG, "Error fetching session list")
                    }.await()
            } else {
                db.collection("users")
                    .document(currentUser.uid)
                    .collection("sessions")
                    .orderBy("consultDate", Query.Direction.DESCENDING)
                    .get().addOnSuccessListener { result ->
                        for (document in result) {
                            sessions.add(Session.fromDocumentSnapshot(document))
                        }
                    }.addOnFailureListener {
                        Log.w(TAG, "Error fetching session list")
                    }.await()
            }
        }

        return sessions.toList()
    }

    suspend fun getSession(sessionId: String, forMedic: Boolean = false): Session {
        var session = Session()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            db.collection(if (forMedic) "medics" else "users")
                .document(currentUser.uid)
                .collection("sessions")
                .document(sessionId)
                .get().addOnSuccessListener { result ->
                    session = Session.fromDocumentSnapshot(result)
                }.addOnFailureListener {
                    Log.w(TAG, "Error fetching session details")
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
                Log.w(TAG, "Error fetching timeslots")
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
            var patientName = ""

            db.collection("users")
                .document(currentUser.uid)
                .get().addOnSuccessListener { document ->
                    patientName = document.get("name") as String
                }.addOnFailureListener {
                    success = false
                    Log.w(TAG, "Error fetching user details")
                }.await()

            var sessionId = ""
            db.collection("users")
                .document(currentUser.uid)
                .collection("sessions")
                .add(
                    hashMapOf(
                        "consultDate" to datetime,
                        "medicId" to selectedMedic.medicId,
                        "medicName" to selectedMedic.name,
                        "patientId" to currentUser.uid,
                        "patientName" to patientName
                    )
                ).addOnSuccessListener { document ->
                    sessionId = document.id
                }.addOnFailureListener {
                    success = false
                    Log.w(TAG, "Error adding session")
                }.await()

            db.collection("medics")
                .document(selectedMedic.medicId)
                .collection("sessions")
                .document(sessionId)
                .set(
                    hashMapOf(
                        "consultDate" to datetime,
                        "medicId" to selectedMedic.medicId,
                        "medicName" to selectedMedic.name,
                        "patientId" to currentUser.uid,
                        "patientName" to patientName
                    )
                ).addOnFailureListener {
                    success = false
                    Log.w(TAG, "Error adding session")
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
                    Log.w(TAG, "Error adding session")
                }.await()
        }
        return success
    }

    suspend fun addUser(userId: String, userName: String) {
        db.collection("users").document(userId).set(
            hashMapOf(
                "notifications" to true,
                "treatmentNotifications" to true,
                "appointmentNotifications" to true,
                "theme" to AppTheme.SYSTEM,
                "isMedic" to false,
                "name" to userName
            )
        ).await()
    }

    suspend fun getUserTheme(): AppTheme {
        val currentUser = auth.currentUser
        var theme = AppTheme.SYSTEM

        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .get().addOnSuccessListener {
                    theme = (AppTheme.fromString(it.get("theme") as String))
                }.addOnFailureListener {
                    Log.w(TAG, "Error fetching user theme")
                }.await()
        }

        return theme
    }

    suspend fun getUserSettings(): Map<String, Any> {
        val currentUser = auth.currentUser
        var settings: Map<String, Any> = hashMapOf()

        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .get().addOnSuccessListener {
                    settings = hashMapOf(
                        "notifications" to (it.get("notifications") as Boolean),
                        "treatmentNotifications" to (it.get("treatmentNotifications") as Boolean),
                        "appointmentNotifications" to (it.get("appointmentNotifications") as Boolean),
                        "theme" to (AppTheme.fromString(it.get("theme") as String))
                    )
                }.addOnFailureListener {
                    Log.w(TAG, "Error fetching user settings")
                }.await()
        }

        return settings
    }

    suspend fun updateUserSettings(newSettings: Map<String, Any>){
        val currentUser = auth.currentUser

        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .update(newSettings)
                .await()
        }
    }

    suspend fun isMedic(): Boolean {
        val currentUser = auth.currentUser
        var res = false

        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .get().addOnSuccessListener { document ->
                    res = document.get("isMedic") as Boolean
                }.addOnFailureListener {
                    Log.w(TAG, "Error fetching user details")
                }.await()
        }

        return res
    }

    suspend fun addTreatment(sessionId: String, newTreat: Treatment) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            var patientId = ""
            val medicSession = db.collection("medics")
                .document(currentUser.uid)
                .collection("sessions")
                .document(sessionId)

            medicSession.get().addOnSuccessListener { document ->
                patientId = document.get("patientId") as String
            }.addOnFailureListener {
                Log.w(TAG, "Error fetching medic session")
            }.await()

            medicSession.update("treatmentScheme", FieldValue.arrayUnion(
                    hashMapOf(
                        "treatmentName" to newTreat.treatmentName,
                        "startDate" to Timestamp(newTreat.startDate),
                        "frequency" to newTreat.frequency,
                        "dose" to newTreat.dose,
                        "applications" to newTreat.applications
                    )
                )).await()

            db.collection("users")
                .document(patientId)
                .collection("sessions")
                .document(sessionId)
                .update("treatmentScheme", FieldValue.arrayUnion(
                    hashMapOf(
                        "treatmentName" to newTreat.treatmentName,
                        "startDate" to Timestamp(newTreat.startDate),
                        "frequency" to newTreat.frequency,
                        "dose" to newTreat.dose,
                        "applications" to newTreat.applications
                    )
                )).await()
        }
    }

    suspend fun removeTreatment(sessionId: String, patientId: String, treatment: Treatment) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            db.collection("medics")
                .document(currentUser.uid)
                .collection("sessions")
                .document(sessionId)
                .update("treatmentScheme", FieldValue.arrayRemove(
                    hashMapOf(
                        "treatmentName" to treatment.treatmentName,
                        "startDate" to treatment.startDate,
                        "frequency" to treatment.frequency,
                        "dose" to treatment.dose,
                        "applications" to treatment.applications
                    )
                )).await()

            db.collection("users")
                .document(patientId)
                .collection("sessions")
                .document(sessionId)
                .update("treatmentScheme", FieldValue.arrayRemove(
                    hashMapOf(
                        "treatmentName" to treatment.treatmentName,
                        "startDate" to treatment.startDate,
                        "frequency" to treatment.frequency,
                        "dose" to treatment.dose,
                        "applications" to treatment.applications
                    )
                )).await()
        }
    }
}