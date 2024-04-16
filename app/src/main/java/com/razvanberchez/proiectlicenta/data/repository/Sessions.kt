package com.razvanberchez.proiectlicenta.data.repository

import com.razvanberchez.proiectlicenta.data.model.Session
import com.razvanberchez.proiectlicenta.data.model.Treatment
import java.time.LocalDate

val sessionList = listOf(
    Session(
        LocalDate.of(2024, 4, 17),
        LocalDate.of(2024, 4, 17),
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
        LocalDate.of(2024, 2, 12),
        LocalDate.of(2024, 3, 19),
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
        LocalDate.of(2024, 3, 17),
        LocalDate.of(2024, 4, 9),
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
        LocalDate.of(2024, 4, 17),
        LocalDate.of(2024, 4, 17),
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
        LocalDate.of(2024, 4, 17),
        LocalDate.of(2024, 4, 17),
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
        LocalDate.of(2024, 4, 17),
        LocalDate.of(2024, 4, 17),
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
        LocalDate.of(2024, 4, 17),
        LocalDate.of(2024, 4, 17),
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
        LocalDate.of(2024, 4, 17),
        LocalDate.of(2024, 4, 17),
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
        LocalDate.of(2024, 4, 17),
        LocalDate.of(2024, 4, 17),
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
        LocalDate.of(2024, 4, 17),
        LocalDate.of(2024, 4, 17),
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

