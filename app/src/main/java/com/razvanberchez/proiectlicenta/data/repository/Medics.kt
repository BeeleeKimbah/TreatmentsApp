package com.razvanberchez.proiectlicenta.data.repository

import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.Review
import com.razvanberchez.proiectlicenta.data.model.Score

val medicList = listOf(
    Medic(
        "Vasile Versaci",
        "Endocrinologie",
        listOf(
            "Gastroenterologie"
        ),
        listOf(
            Review(
                "Ok, se putea si mai bine",
                Score.FOUR
            ),
            Review(
                "Foarte bun, mai venim",
                Score.FIVE
            ),
            Review(
                "Cam arogant",
                Score.TWO
            )
        ),
        11.0 / 3.0
    ),
    Medic(
        "Giani",
        "Medicina interna",
        listOf(
            "Diabet zaharat"
        ),
        listOf(
            Review(
                "Buuun",
                Score.FIVE
            ),
            Review(
                "Foarte bun, mai venim",
                Score.FIVE
            ),
            Review(
                "Ok, dar cam mult de asteptat",
                Score.THREE
            )
        ),
        13.0 / 3.0
    ),
    Medic(
        "Anastasia Craciun",
        "Hematologie",
        listOf(
            "Gastroenterologie",
            "Endocrinologie",
            "Reumatologie"
        ),
        listOf(),
        null
    ),
    Medic(
        "Alina Maria Bojog",
        "Endocrinologie",
        listOf(
            "Gastroenterologie"
        ),
        listOf(
            Review(
                "Super",
                Score.FIVE
            ),
            Review(
                "Foarte proasta experienta",
                Score.TWO
            )
        ),
        7.0 / 2.0
    ),
    Medic(
        "Vasile Versaci",
        "Endocrinologie",
        listOf(
            "Gastroenterologie"
        ),
        listOf(
            Review(
                "Ok, se putea si mai bine",
                Score.FOUR
            ),
            Review(
                "Foarte bun, mai venim",
                Score.FIVE
            ),
            Review(
                "Cam arogant",
                Score.TWO
            )
        ),
        11.0 / 3.0
    ),
    Medic(
        "Giani",
        "Medicina interna",
        listOf(
            "Diabet zaharat"
        ),
        listOf(
            Review(
                "Buuun",
                Score.FIVE
            ),
            Review(
                "Foarte bun, mai venim",
                Score.FIVE
            ),
            Review(
                "Ok, dar cam mult de asteptat",
                Score.THREE
            )
        ),
        13.0 / 3.0
    ),
    Medic(
        "Anastasia Craciun",
        "Hematologie",
        listOf(
            "Gastroenterologie",
            "Endocrinologie",
            "Reumatologie"
        ),
        listOf(),
        null
    ),
    Medic(
        "Alina Maria Bojog",
        "Endocrinologie",
        listOf(
            "Gastroenterologie"
        ),
        listOf(
            Review(
                "Super",
                Score.FIVE
            ),
            Review(
                "Foarte proasta experienta",
                Score.TWO
            )
        ),
        7.0 / 2.0
    ),
    Medic(
        "Vasile Versaci",
        "Endocrinologie",
        listOf(
            "Gastroenterologie"
        ),
        listOf(
            Review(
                "Ok, se putea si mai bine",
                Score.FOUR
            ),
            Review(
                "Foarte bun, mai venim",
                Score.FIVE
            ),
            Review(
                "Cam arogant",
                Score.TWO
            )
        ),
        11.0 / 3.0
    ),
    Medic(
        "Giani",
        "Medicina interna",
        listOf(
            "Diabet zaharat"
        ),
        listOf(
            Review(
                "Buuun",
                Score.FIVE
            ),
            Review(
                "Foarte bun, mai venim",
                Score.FIVE
            ),
            Review(
                "Ok, dar cam mult de asteptat",
                Score.THREE
            )
        ),
        13.0 / 3.0
    ),
    Medic(
        "Anastasia Craciun",
        "Hematologie",
        listOf(
            "Gastroenterologie",
            "Endocrinologie",
            "Reumatologie"
        ),
        listOf(),
        null
    ),
    Medic(
        "Alina Maria Bojog",
        "Endocrinologie",
        listOf(
            "Gastroenterologie"
        ),
        listOf(
            Review(
                "Super",
                Score.FIVE
            ),
            Review(
                "Foarte proasta experienta",
                Score.TWO
            )
        ),
        7.0 / 2.0
    )
)

fun getMedics(): List<Medic> {
    return medicList
}

fun getMedic(): Medic {
    return medicList[0]
}