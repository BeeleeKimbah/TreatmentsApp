package com.razvanberchez.proiectlicenta.resources

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
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
                8,
                15
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
        "Valoare"
    )
)
