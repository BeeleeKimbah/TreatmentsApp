package com.razvanberchez.proiectlicenta.data.repository

import com.razvanberchez.proiectlicenta.data.model.Session
import com.razvanberchez.proiectlicenta.data.model.Treatment
import java.time.LocalDate
import java.time.LocalDateTime

val sessionList = listOf(
    Session(
        LocalDateTime.of(2024, 4, 17, 0, 0),
        LocalDateTime.of(2024, 4, 17, 0, 0),
        "Marian Paun",
        listOf(
            Treatment(
                "Paramociotol",
                LocalDate.of(2024, 4, 23),
                2,
                12,
                2
            ),
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                4,
                8,
                1
            ),
            Treatment(
                "Azitromicina",
                LocalDate.of(2024, 4, 23),
                6,
                12,
                1
            ),
            Treatment(
                "Xyzal",
                LocalDate.of(2024, 4, 23),
                14,
                6,
                1
            ),Treatment(
                "Paramociotol",
                LocalDate.of(2024, 4, 23),
                2,
                12,
                2
            ),
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                4,
                8,
                1
            ),
            Treatment(
                "Azitromicina",
                LocalDate.of(2024, 4, 23),
                6,
                12,
                1
            ),
            Treatment(
                "Xyzal",
                LocalDate.of(2024, 4, 23),
                14,
                6,
                1
            ),Treatment(
                "Paramociotol",
                LocalDate.of(2024, 4, 23),
                2,
                12,
                2
            ),
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                4,
                8,
                1
            ),
            Treatment(
                "Azitromicina",
                LocalDate.of(2024, 4, 23),
                6,
                12,
                1
            ),
            Treatment(
                "Xyzal",
                LocalDate.of(2024, 4, 23),
                14,
                6,
                1
            ),
            Treatment(
                "Paramociotol",
                LocalDate.of(2024, 4, 23),
                2,
                12,
                2
            ),
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                4,
                8,
                1
            ),
            Treatment(
                "Azitromicina",
                LocalDate.of(2024, 4, 23),
                6,
                12,
                1
            ),
            Treatment(
                "Xyzal",
                LocalDate.of(2024, 4, 23),
                14,
                6,
                1
            )
        ),
        "Raceala"
    ),
    Session(
        LocalDateTime.of(2024, 2, 12, 0, 0),
        LocalDateTime.of(2024, 3, 19, 0, 0),
        "Gigel Samsaru",
        listOf(
            Treatment(
                "Paramociotol",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        )
    ),
    Session(
        LocalDateTime.of(2024, 3, 17, 0, 0),
        LocalDateTime.of(2024, 4, 9, 0, 0),
        "Eusebiu Manea",
        listOf(
            Treatment(
                "Panadol",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        ),
        "Migrena"
    ),
    Session(
        LocalDateTime.of(2024, 4, 17, 0, 0),
        LocalDateTime.of(2024, 4, 17, 0, 0),
        "Marian Paun",
        listOf(
            Treatment(
                "Extraveral",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        )
    ),
    Session(
        LocalDateTime.of(2024, 4, 17, 0, 0),
        LocalDateTime.of(2024, 4, 17, 0, 0),
        "Ileana Sharap",
        listOf(
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        )
    ),
    Session(
        LocalDateTime.of(2024, 4, 17, 0, 0),
        LocalDateTime.of(2024, 4, 17, 0, 0),
        "Maria Geangu",
        listOf(
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        )
    ),
    Session(
        LocalDateTime.of(2024, 4, 17, 0, 0),
        LocalDateTime.of(2024, 4, 17, 0, 0),
        "Vasile Copot",
        listOf(
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        ),
        "Hipertensiune"
    ),
    Session(
        LocalDateTime.of(2024, 4, 17, 0, 0),
        LocalDateTime.of(2024, 4, 17, 0, 0),
        "Irina Popa",
        listOf(
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        )
    ),
    Session(
        LocalDateTime.of(2024, 4, 17, 0, 0),
        LocalDateTime.of(2024, 4, 17, 0, 0),
        "Giani Vasiliu",
        listOf(
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        )
    ),
    Session(
        LocalDateTime.of(2024, 4, 17, 0, 0),
        LocalDateTime.of(2024, 4, 17, 0, 0),
        "Bogdan Ciurea",
        listOf(
            Treatment(
                "Aspacardin",
                LocalDate.of(2024, 4, 23),
                2,
                8,
                15
            )
        ),
        "Hipertensiune"
    )
)

fun getSessions(): List<Session> {
    return sessionList
}

fun getSession(): Session {
    return sessionList[0]
}

