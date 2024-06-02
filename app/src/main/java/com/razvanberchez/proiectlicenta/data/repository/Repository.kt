package com.razvanberchez.proiectlicenta.data.repository

import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.Session
import kotlinx.coroutines.delay

class Repository {

    suspend fun getMedics(): List<Medic> {
        delay(1000)
        return medicList
    }

    suspend fun getMedic(medicId: Int): Medic {
        delay(1000)
        return medicList[medicId]
    }

    suspend fun getSessions(): List<Session> {
        delay(1000)
        return sessionList
    }

    suspend fun getSession(sessionId: Int): Session {
        delay(1000)
        return sessionList[sessionId]
    }
}